package com.example.finalproject.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.Icon.BackButton
import com.example.finalproject.ui.theme.FinalProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassicModeScreen(navController: NavController, viewModel: ClassicModeScreenViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Classic Mode") },
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
                    Text(text = "Choose 2 numbers have addition: ${viewModel.selectedNumber.value}")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        items(viewModel.numbers) { number ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(50.dp)
                                    .clickable { viewModel.selectNumber(number) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = number.toString(),
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                    Text(text = viewModel.message.value)
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