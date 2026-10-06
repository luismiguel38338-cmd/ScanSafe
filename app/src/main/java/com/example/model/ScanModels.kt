package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.HazardCaution
import com.example.ui.theme.HazardCritical
import com.example.ui.theme.HazardHarmful
import com.example.ui.theme.HazardSafe

enum class HazardLevel(
    val titleSpanish: String,
    val shortStatus: String,
    val color: Color,
    val minScore: Int,
    val maxScore: Int,
    val iconName: String
) {
    SAFE(
        titleSpanish = "SEGURO / NO TÓXICO",
        shortStatus = "Seguro",
        color = HazardSafe,
        minScore = 0,
        maxScore = 20,
        iconName = "CheckCircle"
    ),
    CAUTION(
        titleSpanish = "PRECAUCIÓN / MODERADO",
        shortStatus = "Precaución",
        color = HazardCaution,
        minScore = 21,
        maxScore = 50,
        iconName = "Warning"
    ),
    HARMFUL(
        titleSpanish = "DAÑINO / TÓXICO",
        shortStatus = "Dañino",
        color = HazardHarmful,
        minScore = 51,
        maxScore = 80,
        iconName = "ReportProblem"
    ),
    CRITICAL(
        titleSpanish = "PELIGRO CRÍTICO / VENENOSO",
        shortStatus = "Crítico",
        color = HazardCritical,
        minScore = 81,
        maxScore = 100,
        iconName = "Dangerous"
    );

    companion object {
        fun fromScore(score: Int): HazardLevel {
            return when {
                score <= 20 -> SAFE
                score <= 50 -> CAUTION
                score <= 80 -> HARMFUL
                else -> CRITICAL
            }
        }
    }
}

enum class ItemCategory(val displayName: String, val iconEmoji: String) {
    PLANT("Plantas y Matas", "🌿"),
    HOUSEHOLD_CHEMICAL("Químicos del Hogar", "🧪"),
    FOOD_MUSHROOM("Alimentos y Hongos", "🍄"),
    COSMETIC("Cosméticos e Higiene", "🧴"),
    CRITTER("Fauna e Insectos", "🦂"),
    OTHER("Otros Productos", "📦")
}

data class ScanResultItem(
    val id: String,
    val name: String,
    val scientificOrChemicalName: String = "",
    val category: ItemCategory,
    val hazardLevel: HazardLevel,
    val toxicityScore: Int,
    val isHarmful: Boolean,
    val summary: String,
    val humanHazard: String,
    val petHazard: String,
    val activeToxins: List<String>,
    val commonSymptoms: List<String>,
    val firstAid: String,
    val safeHandlingTips: String,
    val commonUses: String = "",
    val imageResOrUri: String? = null,
    val scannedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
