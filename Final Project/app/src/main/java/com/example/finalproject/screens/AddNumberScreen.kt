package com.example.finalproject.screens

import GameType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.Icon.BackButton
import com.example.finalproject.R
import com.example.finalproject.ui.theme.FinalProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNumberScreen(navController: NavController, gameType: GameType, viewModel: AddNumberScreenViewModel = viewModel()) {
    var number by remember { mutableStateOf("") }

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
                Image(
                    painter = painterResource(id = R.drawable.math),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = number,
                        onValueChange = { number = it },
                        label = { Text("Enter a number") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                    Button(
                        onClick = {
                            viewModel.updateEnteredNumber(number)
                            navController.navigate("classic?selectedNumber=$number")
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