package com.example.finalproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.icons.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EyeTestModeScreen(navController: NavController, selectedNumber: Int) {
    val viewModel: EyeTestScreenViewModel = viewModel()
    val timeRemaining = viewModel.timeRemaining.value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Eye Test Mode") },
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Time left: $timeRemaining s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Get 30 points to win",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Score: ${viewModel.score.value}",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.stawberry),
                            contentDescription = "Strawberry",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable(enabled = !viewModel.isTopImageDone.value) { viewModel.onTopImageClick() }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.onDoneClick() }) {
                            Text("Done")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Selected Number: $selectedNumber",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.stawberry),
                            contentDescription = "Strawberry",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable(enabled = viewModel.isTopImageDone.value) { viewModel.onBottomImageClick() }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.onSubmitClick(selectedNumber) }) {
                            Text("Submit")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (viewModel.shownMessage.value) {
                            Text(
                                text = viewModel.resultText.value,
                                color = viewModel.resultColor.value,
                                fontSize = 20.sp
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
fun EyeTestModeScreenPreview() {
    val navController = rememberNavController()
    EyeTestModeScreen(navController, selectedNumber = 10)
}