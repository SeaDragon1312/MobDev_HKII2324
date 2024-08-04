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
fun HiddenCardScreen(navController: NavController, viewModel: HiddenCardScreenViewModel = viewModel()) {
    val selectedNumber = viewModel.selectedNumber.value
    val timeRemaining = viewModel.timeRemaining.value
    val shownNumbers = viewModel.shownNumbers.value
    val lives = viewModel.lives.value
    val gameOver = viewModel.gameOver.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hidden Card") },
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
                } else if (viewModel.isRunOutOfTime.value || gameOver) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You're lose :(",
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
                        Text(text = "Health", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(lives) {
                                Image(
                                    painter = painterResource(id = R.drawable.icons8_heart_48),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Time left: $timeRemaining s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Choose 2 numbers whose sum is $selectedNumber")
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            viewModel.numbers.value.forEachIndexed { rowIndex, row ->
                                Row {
                                    row.forEachIndexed { colIndex, number ->
                                        if (number != 0) {
                                            val isShown = shownNumbers[rowIndex][colIndex]
                                            if (isShown) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(8.dp)
                                                        .size(50.dp)
                                                        .border(2.dp, Color.Black)
                                                        .background(Color.White),
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
                                                        .border(2.dp, Color.Black)
                                                        .background(Color.White)
                                                        .clickable {
                                                            viewModel.onNumberClick(
                                                                rowIndex,
                                                                colIndex
                                                            )
                                                        },
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
fun PreviewHiddenCardScreen() {
    FinalProjectTheme {
        HiddenCardScreen(navController = rememberNavController())
    }
}

@Preview
@Composable
fun HiddenCardWinScreen() {
    val navController = rememberNavController()
    val viewModel: HiddenCardScreenViewModel = viewModel()
    viewModel.gameWon.value = true
    HiddenCardScreen(navController, viewModel)
}

@Preview
@Composable
fun HiddenCardLose() {
    val navController = rememberNavController()
    val viewModel: HiddenCardScreenViewModel = viewModel()
    viewModel.isRunOutOfTime.value = true
    HiddenCardScreen(navController, viewModel)
}