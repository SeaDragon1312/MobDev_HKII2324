package com.example.finalproject

import EntryScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.screens.AddNumberScreen
import com.example.finalproject.screens.ClassicModeScreen
import com.example.finalproject.screens.ClassicModeScreenViewModel
import com.example.finalproject.screens.ConnectingNumbersModeScreen
import com.example.finalproject.screens.DualRowModeScreen
import com.example.finalproject.screens.InputBoxesModeScreen
import com.example.finalproject.screens.TimedChallengeModeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "entry") {
        composable("entry") {
            EntryScreen(navController)
        }
        composable("addNumber/{gameType}") { backStackEntry ->
            val gameType = GameType.valueOf(backStackEntry.arguments?.getString("gameType") ?: "")
            AddNumberScreen(navController, gameType)
        }
        composable("classic?selectedNumber={selectedNumber}") { backStackEntry ->
            val selectedNumber = backStackEntry.arguments?.getString("selectedNumber")?.toIntOrNull()
            val viewModel: ClassicModeScreenViewModel = viewModel()
            viewModel.selectedNumber.value = selectedNumber
            ClassicModeScreen(navController, viewModel)
        }
        composable("dualRow") {
            DualRowModeScreen()
        }
        composable("inputBoxes") {
            InputBoxesModeScreen()
        }
        composable("connectingNumbers") {
            ConnectingNumbersModeScreen()
        }
        composable("timedChallenge") {
            TimedChallengeModeScreen()
        }
    }
}