package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.HazardLevel

/**
 * Animated button with bouncy tactile micro-interaction on press.
 */
@Composable
fun BouncyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    testTag: String = "bouncy_button",
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "button_bounce"
    )

    Surface(
        modifier = modifier
            .testTag(testTag)
            .scale(scale)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                while (true) {
                    awaitPointerEventScope {
                        awaitFirstDown(requireUnconsumed = false)
                        isPressed = true
                        waitForUpOrCancellation()
                        isPressed = false
                    }
                }
            }
            .clip(shape)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Handled by scale bounce
                onClick = onClick
            ),
        shape = shape,
        color = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.5f),
        contentColor = contentColor,
        shadowElevation = if (isPressed) 2.dp else 6.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/**
 * Visual badge displaying hazard level with animated subtle pulse.
 */
@Composable
fun HazardBadge(
    hazardLevel: HazardLevel,
    modifier: Modifier = Modifier,
    showDetailed: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_badge")
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hazard_pulse"
    )

    val icon = when (hazardLevel) {
        HazardLevel.SAFE -> Icons.Default.CheckCircle
        HazardLevel.CAUTION -> Icons.Default.Warning
        HazardLevel.HARMFUL -> Icons.Default.ReportProblem
        HazardLevel.CRITICAL -> Icons.Default.Dangerous
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(hazardLevel.color.copy(alpha = 0.16f))
            .border(1.dp, hazardLevel.color.copy(alpha = alphaAnim), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = hazardLevel.titleSpanish,
            tint = hazardLevel.color,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (showDetailed) hazardLevel.titleSpanish else hazardLevel.shortStatus,
            color = hazardLevel.color,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Animated Circular Toxicity Gauge from 0% (Safe) to 100% (Critical Danger)
 */
@Composable
fun ToxicityScoreGauge(
    score: Int,
    hazardLevel: HazardLevel,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(score) {
        animatedProgress.animateTo(
            targetValue = (score.coerceIn(0, 100)) / 100f,
            animationSpec = tween(durationMillis = 1100, easing = LinearEasing)
        )
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            val strokeWidth = 14.dp.toPx()
            // Track background
            drawArc(
                color = Color.Gray.copy(alpha = 0.2f),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                brush = Brush.sweepGradient(
                    0.0f to Color(0xFF10B981),
                    0.4f to Color(0xFFF59E0B),
                    0.7f to Color(0xFFF97316),
                    1.0f to Color(0xFFEF4444)
                ),
                startAngle = 135f,
                sweepAngle = 270f * animatedProgress.value,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$score",
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                color = hazardLevel.color
            )
            Text(
                text = "Índice Toxicidad",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Animated Scanner Laser Line and Reticle for the camera viewfinder.
 */
@Composable
fun AnimatedScanReticle(
    modifier: Modifier = Modifier,
    isScanning: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "laser_scan")
    val laserYRatio by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_y"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Reticle box padding
        val boxWidth = width * 0.78f
        val boxHeight = height * 0.65f
        val left = (width - boxWidth) / 2f
        val top = (height - boxHeight) / 2f
        val right = left + boxWidth
        val bottom = top + boxHeight
        val cornerLength = 36.dp.toPx()
        val cornerStroke = 4.dp.toPx()
        val reticleColor = Color(0xFF10B981)

        // Draw 4 corners
        // Top Left
        drawLine(reticleColor, Offset(left, top), Offset(left + cornerLength, top), cornerStroke, StrokeCap.Round)
        drawLine(reticleColor, Offset(left, top), Offset(left, top + cornerLength), cornerStroke, StrokeCap.Round)

        // Top Right
        drawLine(reticleColor, Offset(right, top), Offset(right - cornerLength, top), cornerStroke, StrokeCap.Round)
        drawLine(reticleColor, Offset(right, top), Offset(right, top + cornerLength), cornerStroke, StrokeCap.Round)

        // Bottom Left
        drawLine(reticleColor, Offset(left, bottom), Offset(left + cornerLength, bottom), cornerStroke, StrokeCap.Round)
        drawLine(reticleColor, Offset(left, bottom), Offset(left, bottom - cornerLength), cornerStroke, StrokeCap.Round)

        // Bottom Right
        drawLine(reticleColor, Offset(right, bottom), Offset(right - cornerLength, bottom), cornerStroke, StrokeCap.Round)
        drawLine(reticleColor, Offset(right, bottom), Offset(right, bottom - cornerLength), cornerStroke, StrokeCap.Round)

        // Laser scan line
        if (isScanning) {
            val laserY = top + (boxHeight * laserYRatio)
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF34D399),
                        Color(0xFF10B981),
                        Color(0xFF34D399),
                        Color.Transparent
                    )
                ),
                start = Offset(left + 8.dp.toPx(), laserY),
                end = Offset(right - 8.dp.toPx(), laserY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}
