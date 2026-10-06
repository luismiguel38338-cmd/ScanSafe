package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BouncyButton

@Composable
fun EmergencyScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        // Warning Banner
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFEF4444).copy(alpha = 0.15f),
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color(0xFFEF4444), RoundedCornerShape(18.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Centro de Emergencias 24h",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFFEF4444)
                    )
                    Text(
                        text = "En caso de ingestión grave, pérdida de conciencia o asfixia, llama de inmediato.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Líneas Directas de Toxicología",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Phone 1: Emergencias 112 / 911
        EmergencyPhoneCard(
            title = "Emergencias Generales (Ambulancia)",
            number = "112",
            subtitle = "Unión Europea, España y red global de emergencias",
            onCall = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Phone 2: Toxicología España
        EmergencyPhoneCard(
            title = "Inst. Nacional de Toxicología (España)",
            number = "+34915620420",
            subtitle = "Atención médica toxicológica 24 horas",
            onCall = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+34915620420"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Phone 3: Poison Help USA / LATAM
        EmergencyPhoneCard(
            title = "Poison Help (EE.UU. / LATAM)",
            number = "18002221222",
            subtitle = "Línea gratuita de control de envenenamiento",
            onCall = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18002221222"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // First Aid Protocols Checklist
        Text(
            text = "Protocolos Clave de Actuación",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProtocolItem(
            step = "1",
            title = "Ingestión de Químicos Cáusticos o Lejía",
            desc = "¡NUNCA PROVOQUES EL VÓMITO! El retorno de ácidos o álcalis volverá a quemar el esófago y puede perforarlo. No des leche ni vinagre. Acude a urgencias inmediatamente."
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProtocolItem(
            step = "2",
            title = "Contacto Ocular con Sustancias Químicas",
            desc = "Lava los ojos con agua limpia templada a chorro suave de forma ininterrumpida durante al menos 15 a 20 minutos. Retira lentes de contacto."
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProtocolItem(
            step = "3",
            title = "Ingestión de Plantas Venenosas (Adelfa, Ricino)",
            desc = "Guarda una muestra o foto clara de la planta. No administres remedios caseros. Si la persona está inconsciente, colócala de lado (posición lateral de seguridad)."
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProtocolItem(
            step = "4",
            title = "Intoxicación en Mascotas (Perros / Gatos)",
            desc = "Evita dar sal o agua oxigenada sin orden veterinaria. Lleva al animal a la clínica veterinaria de urgencia junto con los restos de lo que mordió."
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun EmergencyPhoneCard(
    title: String,
    number: String,
    subtitle: String,
    onCall: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = number,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFEF4444)
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            BouncyButton(
                onClick = onCall,
                backgroundColor = Color(0xFFEF4444),
                contentColor = Color.White,
                shape = RoundedCornerShape(14.dp),
                testTag = "button_call_${number}"
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Llamar",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ProtocolItem(
    step: String,
    title: String,
    desc: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = step,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
