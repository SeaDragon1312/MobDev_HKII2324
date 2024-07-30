package com.example.finalproject.screens

import GameType
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.Icon.BackButton
import com.example.finalproject.ui.theme.FinalProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNumberScreen(navController: NavController, gameType: GameType, viewModel: AddNumberScreenViewModel = viewModel()) {
    var number by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Enter Number For The Game") },
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Select a number between 10 and 50 to play !!!",
                        modifier = Modifier.padding(16.dp)
                    )
                    TextField(
                        value = number,
                        onValueChange = {
                            number = it
                            errorMessage = ""
                        },
                        label = { Text("Enter a number") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        isError = errorMessage.isNotEmpty()
                    )
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Button(
                        onClick = {
                            val num = number.toIntOrNull()
                            if (num == null || num !in 10..50) {
                                errorMessage = "Please select a number between 10 and 50"
                            } else {
                                viewModel.updateEnteredNumber(number)
                                navController.navigate("classic?selectedNumber=$number")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddNumberScreenPreview() {
    FinalProjectTheme {
        val navController = rememberNavController()
        AddNumberScreen(navController, GameType.CLASSIC)
    }
}