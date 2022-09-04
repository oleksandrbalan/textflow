package eu.wewox.textflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.wewox.textflow.ui.theme.TextFlowTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFlowTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary),
                ) {
                    val height = remember { Animatable(0f) }
                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(2000)
                            height.animateTo(550f, tween(10_000))
                            delay(2000)
                            height.animateTo(0f, tween(10_000))
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp,
                        modifier = Modifier
                            .padding(32.dp)
                            .wrapContentHeight(unbounded = true)
                    ) {
                        var alignment by remember { mutableStateOf(TextFlowObstacleAlignment.TopStart) }

                        TextFlow(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                                    append("Alice In The Wonderland")
                                }
                                append("\n")
                                append(Lorem)
                            },
                            onTextLayout = { a, b ->
                                Log.d("___", "${a?.lineCount} ${b?.lineCount}")
                            },
                            obstacleAlignment = alignment,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            //                            Image(
                            //                                painter = painterResource(R.drawable.image_alice),
                            //                                contentDescription = null,
                            //                                modifier = Modifier
                            //                                    .width(100.dp)
                            //                                    .padding(4.dp)
                            //                                    .clickable(
                            //                                        interactionSource = remember { MutableInteractionSource() },
                            //                                        indication = null
                            //                                    ) {
                            //                                        alignment = if (alignment != TextFlowObstacleAlignment.TopEnd) {
                            //                                            TextFlowObstacleAlignment.TopEnd
                            //                                        } else {
                            //                                            TextFlowObstacleAlignment.TopStart
                            //                                        }
                            //                                    }
                            //                            )
                            if (height.value > 0) {
                                Box(
                                    Modifier
                                        .padding(8.dp)
                                        .size(110.dp, height.value.dp)
                                        .background(Color.Magenta)
                                        .clickable {
                                            alignment = if (alignment != TextFlowObstacleAlignment.TopEnd) {
                                                TextFlowObstacleAlignment.TopEnd
                                            } else {
                                                TextFlowObstacleAlignment.TopStart
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private val Lorem = """
Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, “and what is the use of a book,” thought Alice “without pictures or conversations?”

So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.
""".trimIndent()
