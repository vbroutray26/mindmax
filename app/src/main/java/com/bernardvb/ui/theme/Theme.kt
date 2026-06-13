package com.bernardvb.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BernardColors.AccentBlue,
    onPrimary = BernardColors.White,
    primaryContainer = BernardColors.AccentBlueDim,
    secondary = BernardColors.Gold,
    onSecondary = BernardColors.Ink,
    secondaryContainer = BernardColors.GoldDim,
    background = BernardColors.Ink,
    onBackground = BernardColors.Bone,
    surface = BernardColors.Ink,
    onSurface = BernardColors.Bone,
    surfaceVariant = Color(0xFF1A1A1E),
    onSurfaceVariant = BernardColors.Mid,
    error = BernardColors.BlindSpot,
    outline = Color(0xFF2E2E32)
)

private val LightColorScheme = lightColorScheme(
    primary = BernardColors.AccentBlue,
    onPrimary = BernardColors.White,
    primaryContainer = BernardColors.AccentBlueDim,
    secondary = BernardColors.Gold,
    onSecondary = BernardColors.White,
    secondaryContainer = BernardColors.GoldDim,
    background = BernardColors.Bone,
    onBackground = BernardColors.Ink,
    surface = BernardColors.White,
    onSurface = BernardColors.Ink,
    surfaceVariant = BernardColors.Faint,
    onSurfaceVariant = BernardColors.Mid,
    error = BernardColors.BlindSpot,
    outline = BernardColors.Faint
)

@Composable
fun BernardVBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        shapes = BernardShapes,
        content = content
    )
}

// Convenience accessors
private fun Color(value: Long) = androidx.compose.ui.graphics.Color(value.toULong())
