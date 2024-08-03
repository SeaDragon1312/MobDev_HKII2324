package com.example.finalproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.icons.BackButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DualRowModeScreen(navController: NavController, viewModel: DualRowModeScreenViewModel = viewModel()) {
//    val upperRowNumbers by viewModel.upperRowNumbers.collectAsState()
//    val lowerRowNumbers by viewModel.lowerRowNumbers.collectAsState()
//    val upperRowColors by viewModel.upperBorderColor.collectAsState()
//    val lowerRowColors by viewModel.lowerBorderColor.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dual Row Mode") },
                navigationIcon = { BackButton(navController) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Choose 2 numbers whose sum is ${viewModel.selectedNumber.value}")
                AutoScrollingUpperList(viewModel)
                AutoScrollingLowerList(viewModel)
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    items(upperRowNumbers) { number ->
//                        val index = upperRowNumbers.indexOf(number)
//                        Box(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .size(50.dp)
//                                .background(Color.White)
//                                .border(2.dp, upperRowColors[index])
//                                .clickable { viewModel.selectUpperCell(index) },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = number.toString(),
//                                fontSize = 16.sp,
//                            )
//                        }
//                    }
//                }
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    items(lowerRowNumbers) { number ->
//                        val index = lowerRowNumbers.indexOf(number)
//                        Box(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .size(50.dp)
//                                .background(Color.White)
//                                .border(2.dp, lowerRowColors[index])
//                                .clickable { viewModel.selectLowerCell(index) },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = number.toString(),
//                                fontSize = 16.sp,
//                            )
//                        }
//                    }
//                }
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
        items(upperRowNumbers) { number ->
            val index = upperRowNumbers.indexOf(number)
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
        items(lowerRowNumbers) { number ->
            val index = lowerRowNumbers.indexOf(number)
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .background(Color.White)
                    .border(2.dp, lowerRowColors[index])
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

@Preview
@Composable
fun PreviewDualRowModeScreen() {
    val navController = rememberNavController()
    DualRowModeScreen(navController)
}