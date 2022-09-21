package eu.wewox.textflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.wewox.textflow.R
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.ui.theme.SpacingLarge
import eu.wewox.textflow.ui.theme.SpacingMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun BookChapterScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
    ) {
        var index by remember { mutableStateOf(1) }
        LaunchedEffect(Unit) {
            val range = 1 until AliceInTheWonderland.length
            while (isActive) {
                range.forEach {
                    delay(10)
                    index = it
                }
                delay(2000)
                range.reversed().forEach {
                    delay(10)
                    index = it
                }
            }
        }

        Surface(
            shape = RoundedCornerShape(CardCornerRadius),
            elevation = CardElevation,
            modifier = Modifier
                .padding(SpacingLarge)
                .wrapContentHeight(unbounded = true)
        ) {
            TextFlow(
                text = AliceInTheWonderland.substring(1, index),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpacingMedium)
            ) {
                Text(
                    text = AliceInTheWonderland.substring(0, 1),
                    fontFamily = FontFamily(Font(R.font.griffin_two_plain)),
                    fontSize = 74.sp,
                )
            }
        }
    }
}

private val CardCornerRadius: Dp = 16.dp
private val CardElevation: Dp = 8.dp

@Suppress("MaxLineLength")
private val AliceInTheWonderland = """
Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, “and what is the use of a book,” thought Alice “without pictures or conversations?”
""".trimIndent()
