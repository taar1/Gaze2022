package net.gazeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import net.gazeapp.ui.navigation.BottomNavigation
import net.gazeapp.ui.navigation.NavigationGraph
import net.gazeapp.ui.theme.ScrollableToolBarTheme
import net.gazeapp.viewmodel.ScrollViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScrollableToolBarTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScrollableToolBar()
                }
            }
            MainScreenView()
        }
    }

    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            NavigationGraph(navController = navController)
        }
    }

    @Composable
    fun ScrollableToolBar() {
        val scrollState = rememberLazyListState()
        val viewModel: ScrollViewModel = viewModel()

        val scrollUpState = viewModel.scrollUp.observeAsState()

        viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 56.dp),
                state = scrollState
            ) {
                items(20) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(10.dp, 5.dp, 10.dp, 5.dp)
                            .background(Color.White),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(5.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.josh),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.padding(5.dp))

                                Column {
                                    Text(
                                        text = "Make it easy: ${index + 1}",
                                        color = Color.Blue,
                                        fontSize = 16.sp,
                                        //fontWeight = FontWeight.padding(2.dp)
                                    )

                                    Spacer(modifier = Modifier.padding(2.dp))

                                    Text(
                                        text = "Some other text: ${index + 1}",
                                        color = Color.Red,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }

                    }
                }
            }

            ScrollableAppBar(
                title = "Collapse Toolbar",
                modifier = Modifier.align(Alignment.CenterStart),
                scrollUpState = scrollUpState
            )


        }
    }

    @Composable
    fun ScrollableAppBar(
        title: String,
        modifier: Modifier = Modifier,
        navigationIcon: (() -> Unit)? = null,
        background: Color = MaterialTheme.colors.primary,
        scrollUpState: State<Boolean?>
    ) {
        val position by animateFloatAsState(
            if (scrollUpState.value == true) {
                -150f
            } else {
                0f
            }
        )

        Surface(
            modifier = Modifier.graphicsLayer {
                translationY = position
            },
            elevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(background)
            ) {
                Row(modifier = modifier) {
                    if (navigationIcon != null) {
                        navigationIcon()
                    }

                    Text(
                        text = title,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

    private fun testCrash() {
        throw RuntimeException("This is a crash")
    }

}