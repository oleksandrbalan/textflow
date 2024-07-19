package eu.wewox.textflow

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import eu.wewox.textflow.screens.material2.AlignmentAnimationScreen
import eu.wewox.textflow.screens.material2.AnnotatedStringScreen
import eu.wewox.textflow.screens.material2.BookChapterScreen
import eu.wewox.textflow.screens.material2.SimpleTextFlowScreen
import eu.wewox.textflow.screens.material3.AlignmentAnimationScreen as M3AlignmentAnimationScreen
import eu.wewox.textflow.screens.material3.AnnotatedStringScreen as M3AnnotatedStringScreen
import eu.wewox.textflow.screens.material3.BookChapterScreen as M3BookChapterScreen
import eu.wewox.textflow.screens.material3.SimpleTextFlowScreen as M3SimpleTextFlowScreen
import eu.wewox.textflow.ui.theme.TextFlowThemeMaterial3

/**
 * Demo application, has simple "Crossfade" based navigation to various examples.
 */
@Composable
fun App(
    example: Pair<Example, Boolean>?,
    onChangeExample: (Pair<Example, Boolean>?) -> Unit,
) {
    TextFlowThemeMaterial3 {
        val reset = { onChangeExample(null) }

        Crossfade(example, label = "Example navigation") { selected ->
            when (selected) {
                null -> RootScreen(onExampleClick = onChangeExample)
                Example.SimpleTextFlow to false -> SimpleTextFlowScreen(reset)
                Example.BookChapter to false -> BookChapterScreen(reset)
                Example.AlignmentAnimation to false -> AlignmentAnimationScreen(reset)
                Example.AnnotatedString to false -> AnnotatedStringScreen(reset)
                Example.SimpleTextFlow to true -> M3SimpleTextFlowScreen(reset)
                Example.BookChapter to true -> M3BookChapterScreen(reset)
                Example.AlignmentAnimation to true -> M3AlignmentAnimationScreen(reset)
                Example.AnnotatedString to true -> M3AnnotatedStringScreen(reset)
            }
        }
    }
}
