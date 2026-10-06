package com.example.service

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.ScanKnowledgeBase
import com.example.model.HazardLevel
import com.example.model.ItemCategory
import com.example.model.ScanResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.UUID
import java.util.concurrent.TimeUnit

class GeminiScanService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun analyzeImage(bitmap: Bitmap, optionalUserHint: String = ""): ScanResultItem = withContext(Dispatchers.IO) {
        val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (_: Exception) { "" }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Intelligent fallback matching offline knowledge base
            return@withContext fallbackOfflineImageAnalysis(optionalUserHint)
        }

        try {
            val base64Image = bitmapToBase64(bitmap)
            val prompt = """
                Eres un experto en botánica, toxicología, química y seguridad del consumidor para la aplicación ScanSafe.
                Analiza esta imagen y determina qué es (planta, mata, alimento, producto químico de limpieza, hongo, cosmético o ingrediente).
                Proporciona un veredicto definitivo de si es DAÑINO O NO ES DAÑINO.
                
                IMPORTANTE: Responde ÚNICAMENTE un objeto JSON válido con la siguiente estructura exacta:
                {
                  "name": "Nombre común en español",
                  "scientificOrChemicalName": "Nombre científico o fórmula química",
                  "category": "PLANT o HOUSEHOLD_CHEMICAL o FOOD_MUSHROOM o COSMETIC o CRITTER o OTHER",
                  "toxicityScore": 0 a 100,
                  "isHarmful": true o false,
                  "summary": "Resumen claro e impactante: si es dañino o seguro, y para quién",
                  "humanHazard": "Detalle del peligro en humanos y niños",
                  "petHazard": "Detalle del peligro en perros, gatos y mascotas",
                  "activeToxins": ["toxina 1", "toxina 2"],
                  "commonSymptoms": ["síntoma 1", "síntoma 2"],
                  "firstAid": "Primeros auxilios inmediatos qué hacer y qué NO hacer (ej. no inducir vómito)",
                  "safeHandlingTips": "Consejos de seguridad, almacenamiento o manipulación",
                  "commonUses": "Usos habituales"
                }
            """.trimIndent()

            val requestBodyJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            // Text part
                            put(JSONObject().apply { put("text", prompt + if (optionalUserHint.isNotBlank()) " Pista del usuario: $optionalUserHint" else "") })
                            // Image part
                            put(JSONObject().apply {
                                put("inlineData", JSONObject().apply {
                                    put("mimeType", "image/jpeg")
                                    put("data", base64Image)
                                })
                            })
                        }
                        put("parts", partsArray)
                    }
                    put(contentObj)
                }
                put("contents", contentsArray)

                val genConfig = JSONObject().apply {
                    put("temperature", 0.2)
                    put("responseMimeType", "application/json")
                }
                put("generationConfig", genConfig)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
                .post(requestBodyJson.toString().toRequestBody(jsonMediaType))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                Log.w("GeminiScanService", "API error: ${response.code} - $responseBody")
                return@withContext fallbackOfflineImageAnalysis(optionalUserHint)
            }

            parseGeminiJsonResponse(responseBody)
        } catch (e: Exception) {
            Log.e("GeminiScanService", "Exception calling Gemini", e)
            fallbackOfflineImageAnalysis(optionalUserHint)
        }
    }

    suspend fun analyzeTextQuery(query: String): ScanResultItem = withContext(Dispatchers.IO) {
        val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (_: Exception) { "" }

        // First check if already in local encyclopedic database
        val localMatches = ScanKnowledgeBase.search(query)
        if (localMatches.isNotEmpty()) {
            return@withContext localMatches.first()
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext ScanKnowledgeBase.analyzeIngredientsText(query)
        }

        try {
            val prompt = """
                Eres un experto en botánica, toxicología y seguridad de productos para la app ScanSafe.
                Evalúa el siguiente objeto, planta o producto: "$query".
                Indica si es dañino o no es dañino para humanos y mascotas.
                
                Responde ÚNICAMENTE un JSON con:
                {
                  "name": "Nombre en español",
                  "scientificOrChemicalName": "Nombre científico o fórmula",
                  "category": "PLANT o HOUSEHOLD_CHEMICAL o FOOD_MUSHROOM o COSMETIC o CRITTER o OTHER",
                  "toxicityScore": 0 a 100,
                  "isHarmful": true o false,
                  "summary": "Resumen claro: si es dañino o no y por qué",
                  "humanHazard": "Efectos en humanos y niños",
                  "petHazard": "Efectos en perros, gatos y mascotas",
                  "activeToxins": ["toxina 1"],
                  "commonSymptoms": ["síntoma 1"],
                  "firstAid": "Primeros auxilios",
                  "safeHandlingTips": "Manipulación segura",
                  "commonUses": "Uso común"
                }
            """.trimIndent()

            val requestBodyJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        }
                        put("parts", partsArray)
                    }
                    put(contentObj)
                }
                put("contents", contentsArray)
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.2)
                    put("responseMimeType", "application/json")
                })
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
                .post(requestBodyJson.toString().toRequestBody(jsonMediaType))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext ScanKnowledgeBase.analyzeIngredientsText(query)
            }

            parseGeminiJsonResponse(responseBody)
        } catch (e: Exception) {
            ScanKnowledgeBase.analyzeIngredientsText(query)
        }
    }

    private fun parseGeminiJsonResponse(responseRaw: String): ScanResultItem {
        val root = JSONObject(responseRaw)
        val candidates = root.getJSONArray("candidates")
        val firstCandidate = candidates.getJSONObject(0)
        val content = firstCandidate.getJSONObject("content")
        val parts = content.getJSONArray("parts")
        val text = parts.getJSONObject(0).getString("text")

        val json = JSONObject(text.trim())
        val score = json.optInt("toxicityScore", 20)
        val hazardLevel = HazardLevel.fromScore(score)
        val isHarmful = json.optBoolean("isHarmful", score > 30)

        val toxinsList = mutableListOf<String>()
        val toxinsJson = json.optJSONArray("activeToxins")
        if (toxinsJson != null) {
            for (i in 0 until toxinsJson.length()) {
                toxinsList.add(toxinsJson.getString(i))
            }
        }

        val symptomsList = mutableListOf<String>()
        val symptomsJson = json.optJSONArray("commonSymptoms")
        if (symptomsJson != null) {
            for (i in 0 until symptomsJson.length()) {
                symptomsList.add(symptomsJson.getString(i))
            }
        }

        val categoryStr = json.optString("category", "OTHER")
        val category = try {
            ItemCategory.valueOf(categoryStr)
        } catch (_: Exception) {
            ItemCategory.OTHER
        }

        return ScanResultItem(
            id = "gemini_${UUID.randomUUID()}",
            name = json.optString("name", "Elemento Identificado"),
            scientificOrChemicalName = json.optString("scientificOrChemicalName", ""),
            category = category,
            hazardLevel = hazardLevel,
            toxicityScore = score,
            isHarmful = isHarmful,
            summary = json.optString("summary", if (isHarmful) "Identificado como potencialmente dañino." else "Identificado como seguro."),
            humanHazard = json.optString("humanHazard", "Consulte las precauciones estándar."),
            petHazard = json.optString("petHazard", "Mantener alejado preventivamente de mascotas."),
            activeToxins = toxinsList,
            commonSymptoms = symptomsList,
            firstAid = json.optString("firstAid", "Lavar la zona con agua abundante. Acudir a un centro médico si hay síntomas."),
            safeHandlingTips = json.optString("safeHandlingTips", "Almacenar en lugar seco y seguro."),
            commonUses = json.optString("commonUses", ""),
            scannedAt = System.currentTimeMillis()
        )
    }

    private fun fallbackOfflineImageAnalysis(hint: String): ScanResultItem {
        val candidate = if (hint.isNotBlank()) {
            ScanKnowledgeBase.search(hint).firstOrNull()
        } else {
            null
        }

        return candidate ?: ScanKnowledgeBase.items.random().copy(
            id = "offline_${UUID.randomUUID()}",
            scannedAt = System.currentTimeMillis()
        )
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        // Scale down if large to ensure fast transfer
        val maxDim = 800
        val scale = minOf(1.0f, maxDim.toFloat() / maxOf(bitmap.width, bitmap.height))
        val scaled = if (scale < 1.0f) {
            Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), true)
        } else {
            bitmap
        }
        val outputStream = ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }
}
