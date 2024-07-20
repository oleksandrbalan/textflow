@file:Suppress("UsingMaterialAndMaterial3Libraries")

package eu.wewox.textflow.screens.material2

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.textflow.R
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.TextFlowObstacleAlignment
import eu.wewox.textflow.screens.material2.components.Screen
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.SpacingXSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Showcases alignment change with a scale in-out animation.
 */
@Composable
fun AlignmentAnimationScreen(onBackClick: () -> Unit) {
    Screen(onBackClick) {
        var alignment by remember { mutableStateOf(TextFlowObstacleAlignment.TopStart) }
        val size = remember { Animatable(0.dp, Dp.VectorConverter) }

        LaunchedEffect(Unit) {
            while (isActive) {
                size.scaleInAndOut()
                alignment = TextFlowObstacleAlignment.TopEnd

                size.scaleInAndOut()
                alignment = TextFlowObstacleAlignment.TopStart
            }
        }

        TextFlow(
            text = JetpackCompose,
            obstacleAlignment = alignment,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacingMedium),
        ) {
            Image(
                painter = painterResource(R.drawable.image_compose),
                contentDescription = null,
                modifier = Modifier
                    .size(size.value)
                    .padding(SpacingXSmall),
            )
        }
    }
}

private suspend fun Animatable<Dp, AnimationVector1D>.scaleInAndOut() {
    val spec = spring<Dp>(stiffness = Spring.StiffnessLow)
    animateTo(ObstacleMaxSize, spec)
    delay(ObstacleAlignmentChangeDelay)
    animateTo(0.dp, spec)
}

private val ObstacleMaxSize = 92.dp
private const val ObstacleAlignmentChangeDelay = 2_000L

@Suppress("MaxLineLength", "ktlint:standard:max-line-length")
private val JetpackCompose = """Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs."""
