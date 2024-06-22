package eu.wewox.textflow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.textflow.R
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.ui.theme.SpacingLarge
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.SpacingXSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Showcases annotated string usage in the text flow.
 */
@Composable
fun AnnotatedStringScreen() {
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
                text = createAnnotatedString(),
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

private val CardCornerRadius: Dp = 16.dp
private val CardElevation: Dp = 8.dp

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
