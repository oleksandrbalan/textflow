package eu.wewox.textflow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.wewox.textflow.ui.components.TopBar
import eu.wewox.textflow.ui.theme.SpacingMedium

@Composable
internal fun RootScreen(onExampleClick: (Pair<Example, Boolean>?) -> Unit) {
    Scaffold(
        topBar = { TopBar("Text Flow Demo") },
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            examples(
                header = "Material 2",
                onExampleClick = { onExampleClick(it to false) },
            )
            examples(
                header = "Material 3",
                onExampleClick = { onExampleClick(it to true) },
            )
        }
    }
}

private fun LazyListScope.examples(
    header: String,
    onExampleClick: (Example) -> Unit,
) {
    item {
        Text(
            text = header,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(horizontal = SpacingMedium)
                .padding(top = SpacingMedium),
        )
    }
    items(Example.entries) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExampleClick(it) }
                .padding(SpacingMedium),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = it.label,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = it.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}
