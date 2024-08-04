package com.example.finalproject.screens

import GameType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.icons.BackButton
import com.example.finalproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNumberScreen(navController: NavController, gameType: GameType, viewModel: AddNumberScreenViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Number To Play Game") },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = viewModel.enteredNumber.value,
                    onValueChange = {
                        viewModel.updateEnteredNumber(it)
                        viewModel.validateNumber()
                    },
                    label = { Text("Enter a number less or equal to 15") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Text(
                        text = viewModel.errorMessage.value,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val enteredNumber = viewModel.enteredNumber.value.toIntOrNull()
                        if (enteredNumber != null && enteredNumber <= 15) {
                            when (gameType) {
                                GameType.CLASSIC -> navController.navigate("classic?selectedNumber=$enteredNumber")
                                GameType.DUAL_ROW -> navController.navigate("dualRow?selectedNumber=$enteredNumber")
                                GameType.INPUT_BOXES -> navController.navigate("inputBoxes?selectedNumber=$enteredNumber")
                                GameType.POKEMON -> navController.navigate("connectingNumbers?selectedNumber=$enteredNumber")
                                GameType.EYE_TEST -> navController.navigate("timedChallenge?selectedNumber=$enteredNumber")
                                GameType.HIDDEN_CARD -> navController.navigate("hiddenCard?selectedNumber=$enteredNumber")
                            }
                        } else {
                            viewModel.validateNumber()
                        }
                    }
                ) {
                    Text("Submit")
                }
            }
        }
    )
}

@Preview
@Composable
fun AddNumberScreenPreview() {
    AddNumberScreen(navController = rememberNavController(), gameType = GameType.CLASSIC)
}