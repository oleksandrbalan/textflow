package eu.wewox.textflow.screens.material3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import eu.wewox.textflow.R
import eu.wewox.textflow.material3.TextFlow
import eu.wewox.textflow.screens.material3.components.Screen
import eu.wewox.textflow.ui.theme.SpacingMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Showcases the use-case how text flow can be used to create a Drop Cap (large capital letter used as a decorative
 * element at the beginning of a paragraph or section).
 */
@Composable
fun BookChapterScreen(onBackClick: () -> Unit) {
    Screen(onBackClick) {
        val text = AliceInTheWonderland
        val index by rememberTextIndex(text)

        TextFlow(
            text = text.substring(1, index),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacingMedium),
        ) {
            Text(
                text = text.substring(0, 1),
                fontFamily = FontFamily(Font(R.font.griffin_two_plain)),
                fontSize = DropCapFontSize,
            )
        }
    }
}

@Composable
@Suppress("NOTHING_TO_INLINE") // To not create a scope
private inline fun rememberTextIndex(text: String): State<Int> {
    val index = remember { mutableIntStateOf(1) }
    LaunchedEffect(Unit) {
        val range = 1 until text.length
        while (isActive) {
            range.forEach {
                delay(TextTypeDelay)
                index.intValue = it
            }
            delay(TextStillDelay)
            range.reversed().forEach {
                delay(TextTypeDelay)
                index.intValue = it
            }
        }
    }
    return index
}

private val DropCapFontSize: TextUnit = 74.sp

private const val TextTypeDelay = 10L
private const val TextStillDelay = 2_000L

@Suppress("MaxLineLength")
private val AliceInTheWonderland = """
Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, “and what is the use of a book,” thought Alice “without pictures or conversations?”
""".trimIndent()
