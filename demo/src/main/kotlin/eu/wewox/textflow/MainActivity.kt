package eu.wewox.textflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import eu.wewox.textflow.screens.AlignmentAnimationScreen
import eu.wewox.textflow.screens.AnnotatedStringScreen
import eu.wewox.textflow.screens.BookChapterScreen
import eu.wewox.textflow.screens.SimpleTextFlowScreen
import eu.wewox.textflow.ui.components.TopBar
import eu.wewox.textflow.ui.theme.SpacingMedium
import eu.wewox.textflow.ui.theme.TextFlowTheme

/**
 * Main activity for demo application.
 * Contains simple "Crossfade" based navigation to various examples.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TextFlowTheme {
                var example by rememberSaveable { mutableStateOf<Example?>(null) }

                BackHandler(enabled = example != null) {
                    example = null
                }

                Crossfade(targetState = example) { selected ->
                    when (selected) {
                        null -> RootScreen(onExampleClick = { example = it })
                        Example.SimpleTextFlow -> SimpleTextFlowScreen()
                        Example.BookChapter -> BookChapterScreen()
                        Example.AlignmentAnimation -> AlignmentAnimationScreen()
                        Example.AnnotatedString -> AnnotatedStringScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun RootScreen(onExampleClick: (Example) -> Unit) {
    Scaffold(
        topBar = { TopBar("Text Flow Demo") }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(Example.values()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExampleClick(it) }
                        .padding(SpacingMedium)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
