package com.mustfaibra.instagraph.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PurpleRed,
    primaryVariant = Rose,
    secondary = Purple,
    background = LightGray,
    surface = Color.White,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onBackground = Black,
    onSurface = Black,
)

private val LightColorPalette = lightColors(
    primary = PurpleRed,
    primaryVariant = Rose,
    secondary = Purple,
    background = LightGray,
    surface = Color.White,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun InstagraphTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}