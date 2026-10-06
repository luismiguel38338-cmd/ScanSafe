package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.HazardLevel
import com.example.model.ScanResultItem
import com.example.ui.components.BouncyButton
import com.example.ui.components.HazardBadge
import com.example.ui.components.ToxicityScoreGauge
import com.example.ui.viewmodel.ScanViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    viewModel: ScanViewModel,
    onBack: () -> Unit,
    onNavigateToEmergency: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val currentItem by viewModel.currentDetailItem.collectAsState()
    val item: ScanResultItem = currentItem ?: return

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Informe de Seguridad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("button_detail_back")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleFavorite(item) },
                        modifier = Modifier.testTag("button_favorite")
                    ) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (item.isFavorite) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    "Reporte ScanSafe: ${item.name}"
                                )
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    """
                                    ⚠️ Reporte de Seguridad ScanSafe ⚠️
                                    Elemento: ${item.name} (${item.scientificOrChemicalName})
                                    Nivel: ${item.hazardLevel.titleSpanish}
                                    ¿Es dañino?: ${if (item.isHarmful) "SÍ, ES DAÑINO" else "NO, ES SEGURO"}
                                    Índice de Toxicidad: ${item.toxicityScore}/100
                                    Resumen: ${item.summary}
                                    Humanos: ${item.humanHazard}
                                    Mascotas: ${item.petHazard}
                                    Primeros Auxilios: ${item.firstAid}
                                    """.trimIndent()
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Compartir Informe de Seguridad"))
                        },
                        modifier = Modifier.testTag("button_share")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir Reporte"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // VERDICT BANNER
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = item.hazardLevel.color.copy(alpha = 0.14f),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, item.hazardLevel.color, RoundedCornerShape(20.dp))
                    .testTag("banner_verdict")
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (item.isHarmful) "¡ATENCIÓN: PRODUCTO DAÑINO!" else "✓ PRODUCTO SEGURO / INOFENSIVO",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = item.hazardLevel.color
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.hazardLevel.titleSpanish,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = item.hazardLevel.color.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TITLE AND CATEGORY
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (item.scientificOrChemicalName.isNotBlank()) {
                        Text(
                            text = item.scientificOrChemicalName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                HazardBadge(hazardLevel = item.hazardLevel)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SCORE GAUGE & SUMMARY CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ToxicityScoreGauge(
                        score = item.toxicityScore,
                        hazardLevel = item.hazardLevel,
                        size = 120.dp
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Diagnóstico Global",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.summary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // HUMAN HAZARD CARD
            SectionCard(
                title = "Efectos en Humanos y Niños",
                icon = Icons.Default.Warning,
                iconColor = if (item.isHarmful) Color(0xFFF97316) else Color(0xFF10B981)
            ) {
                Text(
                    text = item.humanHazard,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // PET HAZARD CARD
            SectionCard(
                title = "Peligro en Mascotas (Perros y Gatos)",
                icon = Icons.Default.Pets,
                iconColor = if (item.petHazard.contains("Mortal", ignoreCase = true) || item.petHazard.contains("Tóxic", ignoreCase = true)) Color(0xFFEF4444) else Color(0xFF10B981)
            ) {
                Text(
                    text = item.petHazard,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ACTIVE TOXINS / CHEMICAL COMPOUNDS
            if (item.activeToxins.isNotEmpty()) {
                SectionCard(
                    title = "Compuestos Activos y Toxinas",
                    icon = Icons.Default.Warning,
                    iconColor = MaterialTheme.colorScheme.primary
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item.activeToxins.forEach { toxin ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = toxin,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // SYMPTOMS
            if (item.commonSymptoms.isNotEmpty()) {
                SectionCard(
                    title = "Síntomas Clave por Contacto o Ingesta",
                    icon = Icons.Default.LocalHospital,
                    iconColor = Color(0xFFF59E0B)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        item.commonSymptoms.forEach { symptom ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF59E0B))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = symptom,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // IMMEDIATE FIRST AID CARD (Crucial for safety!)
            SectionCard(
                title = "Protocolo de Primeros Auxilios",
                icon = Icons.Default.LocalHospital,
                iconColor = Color(0xFFEF4444)
            ) {
                Text(
                    text = item.firstAid,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SAFE HANDLING
            SectionCard(
                title = "Consejos de Seguridad y Manejo",
                icon = Icons.Default.Warning,
                iconColor = MaterialTheme.colorScheme.secondary
            ) {
                Text(
                    text = item.safeHandlingTips,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (item.commonUses.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Uso habitual: ${item.commonUses}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // EMERGENCY CALLOUT BUTTON IF DANGEROUS
            if (item.hazardLevel == HazardLevel.CRITICAL || item.hazardLevel == HazardLevel.HARMFUL) {
                BouncyButton(
                    onClick = onNavigateToEmergency,
                    backgroundColor = Color(0xFFEF4444),
                    contentColor = Color.White,
                    modifier = Modifier.fillMaxWidth().testTag("button_emergency_callout")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ver Teléfonos de Emergencia Toxicológica",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}
