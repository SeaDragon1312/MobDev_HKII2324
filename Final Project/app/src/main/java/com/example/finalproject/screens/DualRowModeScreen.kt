package com.example.finalproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.icons.BackButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DualRowModeScreen(navController: NavController, viewModel: DualRowModeScreenViewModel = viewModel()) {
    val timeRemaining = viewModel.timeRemaining.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dual Row Mode") },
                navigationIcon = { BackButton(navController) }
            )
        },
        content = { paddingValues ->
            Image(
                painter = painterResource(id = R.drawable.math),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.gameWon.value) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Congratulations, you have won!!!",
                            color = Color.Green,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("entry")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(135, 206, 250)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text("Play Again")
                            }
                            Button(
                                onClick = {
                                    navController.navigate("entry")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(135, 206, 250)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text("Play Other Mode")
                            }
                        }
                    }
                } else if (viewModel.isRunOutOfTime.value) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You're out of time :(",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("entry")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(135, 206, 250)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text("Play Again")
                            }
                            Button(
                                onClick = {
                                    navController.navigate("entry")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(135, 206, 250)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text("Play Other Mode")
                            }
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "Time left: $timeRemaining s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Let's get 30 points!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Score: ${viewModel.score.value}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Choose 2 numbers whose sum is ${viewModel.selectedNumber.value}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        AutoScrollingUpperList(viewModel)
                        AutoScrollingLowerList(viewModel)
                    }
                }
            }
        }
    )
}

@Composable
fun AutoScrollingUpperList(viewModel: DualRowModeScreenViewModel = viewModel()) {
    val upperRowNumbers by viewModel.upperRowNumbers.collectAsState()
    val upperRowColors by viewModel.upperBorderColor.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var scrollDirection by remember { mutableStateOf(1) } // 1 for forward, -1 for backward
    // Automatic scrolling logic
    LaunchedEffect(Unit) {
        while (true) {
            coroutineScope.launch {
                val itemCount = listState.layoutInfo.totalItemsCount
                val firstVisibleItemIndex = listState.firstVisibleItemIndex

                // Reverse direction at the ends
                if (firstVisibleItemIndex >= itemCount - 7) {
                    scrollDirection = -1 // Change direction to backward
                } else if (firstVisibleItemIndex <= 0) {
                    scrollDirection = 1 // Change direction to forward
                }

                // Scroll by one item in the current direction
                val nextIndex = firstVisibleItemIndex + scrollDirection
                listState.animateScrollToItem(index = nextIndex)
            }
            delay(1000) // Delay for 2 seconds
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(upperRowNumbers) {index, number ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .background(Color.White)
                    .border(2.dp, upperRowColors[index])
                    .clickable { viewModel.selectUpperCell(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun AutoScrollingLowerList(viewModel: DualRowModeScreenViewModel = viewModel()) {
    val lowerRowNumbers by viewModel.lowerRowNumbers.collectAsState()
    val lowerRowColors by viewModel.lowerBorderColor.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var scrollDirection by remember { mutableStateOf(1) } // 1 for forward, -1 for backward
    // Automatic scrolling logic
    LaunchedEffect(Unit) {
        while (true) {
            coroutineScope.launch {
                val itemCount = listState.layoutInfo.totalItemsCount
                val firstVisibleItemIndex = listState.firstVisibleItemIndex

                // Reverse direction at the ends
                if (firstVisibleItemIndex >= itemCount - 7) {
                    scrollDirection = -1 // Change direction to backward
                } else if (firstVisibleItemIndex <= 0) {
                    scrollDirection = 1 // Change direction to forward
                }

                // Scroll by one item in the current direction
                val nextIndex = firstVisibleItemIndex + scrollDirection
                listState.animateScrollToItem(index = nextIndex)
            }
            delay(1000) // Delay for 2 seconds
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(lowerRowNumbers) {index, number ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .background(Color.White)
                    .border(2.dp, lowerRowColors[index])
                    .clickable { viewModel.selectLowerCell(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDualRowModeScreen() {
    val navController = rememberNavController()
    DualRowModeScreen(navController)
}