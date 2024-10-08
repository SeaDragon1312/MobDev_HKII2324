package com.example.finalproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.finalproject.icons.BackButton
import com.example.finalproject.R
import com.example.finalproject.ui.theme.FinalProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassicModeScreen(navController: NavController, viewModel: ClassicModeScreenViewModel = viewModel()) {
    val selectedNumber = viewModel.selectedNumber.value
    val timeRemaining = viewModel.timeRemaining.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Classic Mode") },
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
                }
                else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Time left: $timeRemaining s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Choose 2 numbers whose sum is $selectedNumber")
                        Column {
                            viewModel.numbers.value.forEachIndexed { rowIndex, row ->
                                Row {
                                    row.forEachIndexed { colIndex, number ->
                                        if (number != 0) {
                                            val borderColor = viewModel.borderColors.value[rowIndex][colIndex]
                                            Box(
                                                modifier = Modifier
                                                    .padding(8.dp)
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
                                                    .padding(8.dp)
                                                    .size(50.dp)
                                                    .border(2.dp, Color.Transparent)
                                                    .background(Color.Transparent),
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
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewClassicModeScreen() {
    FinalProjectTheme {
        ClassicModeScreen(navController = rememberNavController())
    }
}

@Preview
@Composable
fun ClassicWinScreen() {
    val navController = rememberNavController()
    val viewModel: ClassicModeScreenViewModel = viewModel()
    viewModel.gameWon.value = true
    ClassicModeScreen(navController, viewModel)
}

@Preview
@Composable
fun ClassicLoseScreen() {
    val navController = rememberNavController()
    val viewModel: ClassicModeScreenViewModel = viewModel()
    viewModel.isRunOutOfTime.value = true
    ClassicModeScreen(navController, viewModel)
}