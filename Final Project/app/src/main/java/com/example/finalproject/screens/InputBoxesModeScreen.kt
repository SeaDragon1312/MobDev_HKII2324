package com.example.finalproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.icons.BackButton
import com.example.finalproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBoxesModeScreen(navController: NavController, viewModel: InputBoxesModeScreenViewModel = viewModel()) {
    val selectedNumber = viewModel.selectedNumber.value ?: 0

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Input Boxes Mode") },
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
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Let's get 50 points!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Score: ${viewModel.score.value}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Enter numbers that sum to $selectedNumber")
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                value = viewModel.box1Value.value,
                                onValueChange = {
                                    viewModel.box1Value.value = it
                                    viewModel.box1BorderColor.value = Color.Black
                                    viewModel.box2BorderColor.value = Color.Black
                                },
                                modifier = Modifier
                                    .border(2.dp, viewModel.box1BorderColor.value)
                                    .width(50.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = " + ", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            TextField(
                                value = viewModel.box2Value.value,
                                onValueChange = {
                                    viewModel.box2Value.value = it
                                },
                                modifier = Modifier
                                    .border(2.dp, viewModel.box2BorderColor.value)
                                    .width(50.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = " = ", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "$selectedNumber", fontSize = 20.sp)
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
fun InputBoxesModeScreenPreview() {
    // Dummy NavController for preview
    val navController = rememberNavController()
    InputBoxesModeScreen(navController)
}