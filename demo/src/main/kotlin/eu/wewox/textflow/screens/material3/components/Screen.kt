package eu.wewox.textflow.screens.material3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.textflow.ui.components.TopBar
import eu.wewox.textflow.ui.theme.SpacingLarge
import eu.wewox.textflow.ui.theme.TextFlowThemeMaterial3

/**
 * Simple screen wrapper for Material 3 screen.
 *
 * @param onBackClick The lambda to invoke when back button is clicked.
 * @param content The content of the screen.
 */
@Composable
fun Screen(
    onBackClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    TextFlowThemeMaterial3 {
        Scaffold(
            topBar = {
                TopBar(
                    title = "",
                    onBackClick = onBackClick,
                )
            },
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Surface(
                    shape = RoundedCornerShape(CardCornerRadius),
                    shadowElevation = CardElevation,
                    modifier = Modifier
                        .padding(SpacingLarge)
                        .wrapContentHeight(unbounded = true),
                ) {
                    content()
                }
            }
        }
    }
}

private val CardCornerRadius: Dp = 16.dp
private val CardElevation: Dp = 8.dp
