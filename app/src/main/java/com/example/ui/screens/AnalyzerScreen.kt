package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BouncyButton
import com.example.ui.viewmodel.ScanViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnalyzerScreen(
    viewModel: ScanViewModel,
    onNavigateToDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    var formulaText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val presets = listOf(
        "Lejía concentrada con amoníaco",
        "Sosa cáustica desatascador NaOH 100%",
        "Tratamiento capilar con formol y alcohol",
        "Crema con propylparaben y fenoxietanol",
        "Mata de adelfa y hojas trituradas"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        Text(
            text = "Analizador de Fórmulas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Pega o escribe los ingredientes de cualquier etiqueta, producto químico, planta o cosmético para detectar sustancias tóxicas.",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Text area for input
        OutlinedTextField(
            value = formulaText,
            onValueChange = { formulaText = it },
            placeholder = {
                Text(
                    "Ejemplo: Aqua, Sodium Hypochlorite, Sodium Hydroxide, Parfum, Limonene, Methylparaben...",
                    fontSize = 14.sp
                )
            },
            minLines = 5,
            maxLines = 8,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_formula_text")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preset Chips
        Text(
            text = "Fórmulas o Productos de Ejemplo:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presets.forEach { preset ->
                AssistChip(
                    onClick = { formulaText = preset },
                    label = { Text(preset, fontSize = 12.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Animated Button
        BouncyButton(
            onClick = {
                if (formulaText.isNotBlank()) {
                    viewModel.analyzeIngredientsText(formulaText)
                    onNavigateToDetail()
                }
            },
            enabled = formulaText.isNotBlank(),
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("button_analyze_formula")
        ) {
            Icon(
                imageVector = Icons.Default.Science,
                contentDescription = null
            )
            Text(
                text = " ANALIZAR COMPONENTES",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
