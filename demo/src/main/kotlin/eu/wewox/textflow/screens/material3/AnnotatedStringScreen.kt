package eu.wewox.textflow.screens.material3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import eu.wewox.textflow.R
import eu.wewox.textflow.material3.TextFlow
import eu.wewox.textflow.screens.material3.components.Screen
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.SpacingXSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Showcases annotated string usage in the text flow.
 */
@Composable
fun AnnotatedStringScreen(onBackClick: () -> Unit) {
    Screen(onBackClick) {
        TextFlow(
            text = createAnnotatedString(),
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

@Composable
private fun createAnnotatedString(): AnnotatedString {
    val text = listOf(AnnotatedStringTitle, AnnotatedStringText).joinToString(" ")
    val whitespaces = text
        .indices
        .filter { text[it] == ' ' }
        .plus(text.length)
        .drop(1)

    var startIndex by remember { mutableIntStateOf(0) }
    var endIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (isActive) {
            startIndex = 0
            endIndex = 0
            whitespaces.forEach { ix ->
                val oldEnd = endIndex
                while (endIndex < ix) {
                    endIndex++
                    delay(SpanExtendDelay)
                }
                while (startIndex <= oldEnd) {
                    startIndex++
                    delay(SpanExtendDelay)
                }
            }
            while (startIndex < text.length) {
                startIndex++
                delay(SpanExtendDelay)
            }
        }
    }

    return buildAnnotatedString {
        withStyle(TitleSpanStyle) {
            append(AnnotatedStringTitle)
        }
        append("\n")
        append(AnnotatedStringText)

        addStyle(UnderlinedSpanStyle, startIndex, endIndex)
    }
}

private val TitleSpanStyle: SpanStyle
    @Composable
    @ReadOnlyComposable
    get() {
        val textStyle = LocalTextStyle.current
        return SpanStyle(fontSize = textStyle.fontSize * 1.7f, fontWeight = FontWeight.Bold)
    }

private val UnderlinedSpanStyle: SpanStyle
    get() = SpanStyle(textDecoration = TextDecoration.Underline)

private const val SpanExtendDelay = 30L

private const val AnnotatedStringTitle = "Annotated String"

@Suppress("MaxLineLength")
private val AnnotatedStringText = """
Text flow can also receive an AnnotatedString with custom span styles, which will span correctly across both text blocks.
""".trimIndent()
