@file:OptIn(ExperimentalTextApi::class)

package eu.wewox.textflow

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import kotlin.math.max

@Composable
fun TextFlow(
    text: AnnotatedString,
    obstacleAlignment: TextFlowObstacleAlignment,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult?, TextLayoutResult?) -> Unit = { _, _ -> },
    style: TextStyle = LocalTextStyle.current,
    obstacleContent: @Composable () -> Unit,
) {
    // Basically copy-pasta from Text composable
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }

    // Basically copy-pasta from Text composable
    // However we need to disable font padding to align both text paragraphs perfectly
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing,
            platformStyle = PlatformTextStyle(false),
        )
    )

    SubcomposeLayout(modifier) { constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // Measure obstacle(s) first to check how much space they occupy
        val obstaclePlaceables = subcompose(TextFlowContent.Obstacle, obstacleContent).map {
            it.measure(looseConstraints)
        }

        // Take the largest width and height from obstacles
        val maxObstacleWidth = obstaclePlaceables.maxOfOrNull { it.width } ?: 0
        val maxObstacleHeight = obstaclePlaceables.maxOfOrNull { it.height } ?: 0

        // And create a "bounding box" rectangle
        val obstacle = createObstaclePosition(
            constraints = constraints,
            width = maxObstacleWidth,
            height = maxObstacleHeight,
            alignment = obstacleAlignment
        )

        // Then measure the text canvas with the given obstacle
        val textPlaceable = subcompose(TextFlowContent.Text) {
            TextFlowCanvas(
                text = text,
                obstacle = obstacle,
                obstacleAlignment = obstacleAlignment,
                constraints = constraints,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                onTextLayout = onTextLayout,
                style = mergedStyle,
            )
        }.first().measure(looseConstraints)

        layout(
            width = textPlaceable.width,
            height = max(maxObstacleHeight, textPlaceable.height)
        ) {
            obstaclePlaceables.forEach {
                it.place(obstacle.topLeft.x.toInt(), obstacle.topLeft.y.toInt())
            }

            textPlaceable.place(0, 0)
        }
    }
}

enum class TextFlowObstacleAlignment {
    TopStart, TopEnd
}

@Composable
private fun MeasureScope.TextFlowCanvas(
    text: AnnotatedString,
    obstacle: Rect,
    obstacleAlignment: TextFlowObstacleAlignment,
    constraints: Constraints,
    overflow: TextOverflow,
    softWrap: Boolean,
    maxLines: Int,
    onTextLayout: (TextLayoutResult?, TextLayoutResult?) -> Unit = { _, _ -> },
    style: TextStyle,
) {
    val layoutWidth = constraints.maxWidth

    // Prepare text measurer instance to measure text based on constraints
    val textMeasurer = rememberTextMeasurer()

    var firstBlock: TextLayoutResult?
    val firstBlockHeight: Float
    val firstBlockVisibleLineCount: Int
    val firstBlockLastCharIndex: Int
    if (obstacle.height > 0) {
        firstBlock = textMeasurer.measure(
            text = text,
            style = style,
            size = IntSize(layoutWidth - obstacle.width.toInt(), Int.MAX_VALUE),
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
        )

        val lastVisibleLineIndex = firstBlock.lastVisibleLineIndex(obstacle.height)
        firstBlockVisibleLineCount = lastVisibleLineIndex + 1
        firstBlockHeight = firstBlock.getLineBottom(lastVisibleLineIndex)

        firstBlockLastCharIndex = firstBlock.getOffsetForPosition(
            Offset(
                firstBlock.getLineRight(lastVisibleLineIndex),
                firstBlock.getLineTop(lastVisibleLineIndex)
            )
        )

        firstBlock = textMeasurer.measure(
            text = text,
            style = style,
            size = IntSize(layoutWidth - obstacle.width.toInt(), firstBlockHeight.toInt()),
            overflow = overflow,
            softWrap = softWrap,
            maxLines = lastVisibleLineIndex + 1,
        )
    } else {
        firstBlock = null
        firstBlockHeight = 0f
        firstBlockVisibleLineCount = 0
        firstBlockLastCharIndex = -1
    }

    val secondBlock: TextLayoutResult?
    val secondBlockHeight: Float
    if (firstBlockVisibleLineCount < maxLines && firstBlockLastCharIndex < text.length) {
        secondBlock = textMeasurer.measure(
            text = text.subSequence(firstBlockLastCharIndex + 1, text.length),
            style = style,
            size = IntSize(layoutWidth, Int.MAX_VALUE),
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines - firstBlockVisibleLineCount,
        )

        secondBlockHeight = secondBlock.getLineBottom(secondBlock.lineCount - 1)
    } else {
        secondBlock = null
        secondBlockHeight = 0f
    }

    onTextLayout(firstBlock, secondBlock)

    Canvas(
        modifier = Modifier.size(
            width = layoutWidth.toDp(),
            height = (firstBlockHeight + secondBlockHeight).toDp()
        )
    ) {
        withTransform(
            transformBlock = {
                translate(
                    left = if (obstacleAlignment == TextFlowObstacleAlignment.TopStart) {
                        obstacle.width
                    } else {
                        0f
                    },
                )
            }
        ) {
            firstBlock?.multiParagraph?.paint(drawContext.canvas)
        }

        translate(top = firstBlockHeight) {
            secondBlock?.multiParagraph?.paint(drawContext.canvas)
        }
    }
}

private fun createObstaclePosition(
    constraints: Constraints,
    width: Int,
    height: Int,
    alignment: TextFlowObstacleAlignment
): Rect {
    val offset = when (alignment) {
        TextFlowObstacleAlignment.TopStart -> Offset.Zero
        TextFlowObstacleAlignment.TopEnd -> Offset((constraints.maxWidth - width).toFloat(), 0f)
    }
    return Rect(offset, Size(width.toFloat(), height.toFloat()))
}

private fun TextLayoutResult.lastVisibleLineIndex(height: Float): Int {
    repeat(lineCount) {
        if (getLineBottom(it) > height) {
            return it
        }
    }
    return lineCount - 1
}

private enum class TextFlowContent { Obstacle, Text }
