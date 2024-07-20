@file:Suppress("UsingMaterialAndMaterial3Libraries")

package eu.wewox.textflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColors(
    primary = LightBlue,
    secondary = LightBlue,

    onPrimary = Color.Black,
    onSecondary = Color.Black,

    surface = Color.Black,
    background = Color.Black,

    onSurface = Color.White,
    onBackground = Color.White,
)

private val LightColorScheme = lightColors(
    primary = LightBlue,
    secondary = LightBlue,

    onPrimary = Color.Black,
    onSecondary = Color.Black,

    surface = Color.White,
    background = Color.White,

    onSurface = Color.Black,
    onBackground = Color.Black,
)

/**
 * The theme to use for Material 2 demo application.
 */
@Composable
fun TextFlowThemeMaterial2(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        content = content,
    )
}
