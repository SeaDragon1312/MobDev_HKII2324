package com.example.finalproject.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.finalproject.Icon.BackButton
import com.example.finalproject.R
import com.example.finalproject.ui.theme.FinalProjectTheme
import kotlinx.coroutines.delay

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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Choose 2 numbers whose sum is ${viewModel.selectedNumber.value}")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(16.dp)
                    ) {
                        items(viewModel.numbers) { number ->
                            if (number != -1) { // Only display numbers that are not marked as removed
                                val borderColor = viewModel.borderColors[number] ?: Color.Black
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(50.dp)
                                        .clickable { viewModel.selectNumber(number) }
                                        .border(BorderStroke(2.dp, borderColor))
                                        .background(Color.White),
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
                    if (viewModel.message.value == "win") {
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
                                modifier = Modifier.weight(1f).padding(8.dp)
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
                                modifier = Modifier.weight(1f).padding(8.dp)
                            ) {
                                Text("Play Other Mode")
                            }
                        }
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