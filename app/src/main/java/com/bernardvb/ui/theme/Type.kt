package com.bernardvb.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bernardvb.R

val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val DMSerifDisplay = FontFamily(
    Font(
        googleFont = GoogleFont("DM Serif Display"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal
    )
)

val Inter = FontFamily(
    Font(googleFont = GoogleFont("Inter"), fontProvider = googleFontProvider, weight = FontWeight.Light),
    Font(googleFont = GoogleFont("Inter"), fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Inter"), fontProvider = googleFontProvider, weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Inter"), fontProvider = googleFontProvider, weight = FontWeight.SemiBold),
)

val JetBrainsMono = FontFamily(
    Font(googleFont = GoogleFont("JetBrains Mono"), fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("JetBrains Mono"), fontProvider = googleFontProvider, weight = FontWeight.Medium),
)

object BernardType {
    // Display — DM Serif Display (model names, screen titles)
    val DisplayLarge = TextStyle(
        fontFamily = DMSerifDisplay,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.02).em
    )
    val DisplayMedium = TextStyle(
        fontFamily = DMSerifDisplay,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.01).em
    )
    val DisplaySmall = TextStyle(
        fontFamily = DMSerifDisplay,
        fontSize = 22.sp,
        lineHeight = 26.sp
    )

    // Body — Inter
    val BodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 26.sp
    )
    val BodyMedium = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    )
    val BodySmall = TextStyle(
        fontFamily = Inter,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    )

    // Label — JetBrains Mono (eyebrows, metadata, origin, XP)
    val LabelLarge = TextStyle(
        fontFamily = JetBrainsMono,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.em
    )
    val LabelSmall = TextStyle(
        fontFamily = JetBrainsMono,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.14.em
    )
}
