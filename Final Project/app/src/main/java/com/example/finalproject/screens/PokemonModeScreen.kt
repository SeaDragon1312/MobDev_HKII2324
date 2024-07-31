package com.example.finalproject.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.Icon.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonModeScreen(navController: NavController, viewModel: PokemonModeScreenViewModel = viewModel()) {
    val selectedNumber = viewModel.selectedNumber.value ?: 0

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
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Let's play pikachu, connect 2 number whose sum is $selectedNumber")
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            viewModel.numbers.value.forEachIndexed { rowIndex, row ->
                                Row {
                                    row.forEachIndexed { colIndex, number ->
                                        val borderColor = viewModel.borderColors.value[rowIndex][colIndex]
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .border(2.dp, borderColor)
                                                .clickable { /* Handle click */ },
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
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.validateSum() }) {
                            Text("Check")
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

@Preview(showBackground = true)
@Composable
fun PokemonModeScreenPreview() {
    val navController = rememberNavController()
    val viewModel: PokemonModeScreenViewModel = viewModel()
    viewModel.setEnteredNumber(30) // Initialize with a sample number
    PokemonModeScreen(navController, viewModel)
}