package com.example.finalproject.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.icons.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonModeScreen(navController: NavController, viewModel: PokemonModeScreenViewModel = viewModel()) {
    val selectedNumber = viewModel.selectedNumber.value ?: 0
    val timeRemaining = viewModel.timeRemaining.value
    val connectingLines by viewModel.connectingLines
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pokemon Mode") },
                navigationIcon = { BackButton(navController) }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.math),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
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
                                    navController.navigate("connectingNumbers")
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
                                    navController.navigate("connectingNumbers")
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
                }
                else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Time left: $timeRemaining s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Let's play pikachu, connect 2 number whose sum is $selectedNumber")
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            viewModel.numbers.value.forEachIndexed { rowIndex, row ->
                                Row {
                                    row.forEachIndexed { colIndex, number ->
                                        if (number != 0) {
                                            val borderColor = viewModel.borderColors.value[rowIndex][colIndex]
                                            Box(
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .border(2.dp, borderColor)
                                                    .background(Color.White)
                                                    .clickable { viewModel.onNumberClick(rowIndex, colIndex) },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = number.toString(),
                                                    fontSize = 16.sp,
                                                )
                                            }
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .background(Color.Transparent)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = viewModel.validationMessage.value,
                            color = if (viewModel.validationMessage.value == "Correct") Color.Green else Color.Red,
                            fontSize = 16.sp
                        )
                    }
                    connectingLines?.forEach() { (start, end) ->
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val startX = start.second * 50.dp.toPx() + 72.dp.toPx()
                            val startY = start.first * 50.dp.toPx() + 290.dp.toPx()
                            val endX = end.second * 50.dp.toPx() + 72.dp.toPx()
                            val endY = end.first * 50.dp.toPx() + 290.dp.toPx()
                            drawLine(
                                color = Color.Green,
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 4.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonModeScreenPreview() {
    val navController = rememberNavController()
    val viewModel: PokemonModeScreenViewModel = viewModel()
    PokemonModeScreen(navController, viewModel)
}

//@Preview(showBackground = true)
//@Composable
//fun PokemonWinScreenPreview() {
//    val navController = rememberNavController()
//    val viewModel: PokemonModeScreenViewModel = viewModel()
//    viewModel.gameWon.value = true
//    PokemonModeScreen(navController, viewModel)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PokemonLoseScreenPreview() {
//    val navController = rememberNavController()
//    val viewModel: PokemonModeScreenViewModel = viewModel()
//    viewModel.isRunOutOfTime.value = true
//    PokemonModeScreen(navController, viewModel)
//}