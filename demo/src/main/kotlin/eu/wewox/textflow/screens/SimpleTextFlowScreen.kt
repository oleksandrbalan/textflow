package eu.wewox.textflow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.textflow.R
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.ui.theme.SpacingLarge
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.SpacingXSmall

/**
 * Showcases the simple usage of the text flow with an image.
 */
@Composable
fun SimpleTextFlowScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
    ) {
        Surface(
            shape = RoundedCornerShape(CardCornerRadius),
            elevation = CardElevation,
            modifier = Modifier
                .padding(SpacingLarge)
                .wrapContentHeight(unbounded = true)
        ) {
            TextFlow(
                text = JetpackCompose,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingMedium)
            ) {
                Image(
                    painter = painterResource(R.drawable.image_compose),
                    contentDescription = null,
                    modifier = Modifier.padding(SpacingXSmall)
                )
            }
        }
    }
}

private val CardCornerRadius: Dp = 16.dp
private val CardElevation: Dp = 8.dp

@Suppress("MaxLineLength")
private val JetpackCompose = """Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs."""
