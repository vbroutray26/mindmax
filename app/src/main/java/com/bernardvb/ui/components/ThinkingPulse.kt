package com.bernardvb.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType
import kotlin.math.*

@Composable
fun ThinkingPulse(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val t by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "t"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BernardColors.Ink),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(240.dp)
        ) {
            val cx = size.width / 2
            val cy = size.height / 2
            val rx = size.width * 0.42f
            val ry = size.height * 0.42f

            // Lissajous curve: x = A*sin(a*t + d), y = B*sin(b*t)
            val path = Path()
            val steps = 300
            var first = true
            for (i in 0..steps) {
                val angle = (i.toFloat() / steps) * 2 * PI.toFloat()
                val x = cx + rx * sin(3f * angle + t)
                val y = cy + ry * sin(2f * angle)
                if (first) {
                    path.moveTo(x, y)
                    first = false
                } else {
                    path.lineTo(x, y)
                }
            }
            path.close()

            drawPath(
                path = path,
                color = BernardColors.AccentBlue.copy(alpha = alpha),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        Text(
            text = "Thinking through your situation…",
            style = BernardType.LabelLarge,
            color = BernardColors.Bone.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )
    }
}
