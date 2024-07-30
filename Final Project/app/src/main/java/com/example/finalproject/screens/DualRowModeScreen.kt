package com.example.finalproject.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun DualRowModeScreen(navController: NavController, viewModel: DualRowModeScreenViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dual Row Mode") },
                navigationIcon = { BackButton(navController) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Choose numbers whose sum is ${viewModel.selectedNumber.value}")
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(viewModel.upperRowNumbers) { number ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .clickable { viewModel.selectNumberForPlayer1(number) }
                                .border(BorderStroke(2.dp, Color.Black)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = number.toString(),
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(viewModel.lowerRowNumbers) { number ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .clickable { viewModel.selectNumberForPlayer2(number) }
                                .border(BorderStroke(2.dp, Color.Black)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = number.toString(),
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                Text(
                    text = viewModel.message.value,
                    color = if (viewModel.message.value.contains("Correct")) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewDualRowModeScreen() {
    FinalProjectTheme {
        DualRowModeScreen(navController = rememberNavController())
    }
}