@file:Suppress("UsingMaterialAndMaterial3Libraries")

package eu.wewox.textflow.screens.material2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import eu.wewox.textflow.R
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.screens.material2.components.Screen
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.SpacingXSmall

/**
 * Showcases the simple usage of the text flow with an image.
 */
@Composable
fun SimpleTextFlowScreen(onBackClick: () -> Unit) {
    Screen(onBackClick) {
        TextFlow(
            text = JetpackCompose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacingMedium),
        ) {
            Image(
                painter = painterResource(R.drawable.image_compose),
                contentDescription = null,
                modifier = Modifier.padding(SpacingXSmall),
            )
        }
    }
}

@Suppress("MaxLineLength", "ktlint:standard:max-line-length")
private val JetpackCompose = """Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs."""
