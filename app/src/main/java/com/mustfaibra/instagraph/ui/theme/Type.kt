package com.mustfaibra.instagraph.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mustfaibra.instagraph.R

val font = FontFamily(
    Font(resId = R.font.readex_extra_light, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(resId = R.font.readex_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(resId = R.font.readex_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(resId = R.font.readex_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.readex_semi_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(resId = R.font.readex_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    defaultFontFamily = font,
)