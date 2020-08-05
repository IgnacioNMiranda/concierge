package com.example.myapplication.ui

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val DarkColorPalette = darkColorPalette(
    primary = primaryDarkColor,
    secondary = secondaryDarkColor,
    onPrimary = primaryTextColor,
    onSecondary = secondaryTextColor
)

private val LightColorPalette = lightColorPalette(
    primary = primaryColor,
    primaryVariant = primaryLightColor,
    secondary = secondaryColor,
    secondaryVariant = secondaryLightColor,
    onPrimary = primaryTextColor,
    onSecondary = secondaryTextColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}