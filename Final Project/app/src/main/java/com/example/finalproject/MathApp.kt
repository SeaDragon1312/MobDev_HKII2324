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
import com.example.finalproject.screens.DualRowModeScreen
import com.example.finalproject.screens.DualRowModeScreenViewModel
import com.example.finalproject.screens.EyeTestModeScreen
import com.example.finalproject.screens.InputBoxesModeScreen
import com.example.finalproject.screens.InputBoxesModeScreenViewModel
import com.example.finalproject.screens.PokemonModeScreen
import com.example.finalproject.screens.PokemonModeScreenViewModel
import com.example.finalproject.screens.HiddenCardScreen
import com.example.finalproject.screens.HiddenCardScreenViewModel

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
            viewModel.selectedNumber.value = selectedNumber ?: 0
            ClassicModeScreen(navController, viewModel)
        }
        composable("dualRow?selectedNumber={selectedNumber}") { backStackEntry ->
            val selectedNumber = backStackEntry.arguments?.getString("selectedNumber")?.toIntOrNull()
            val viewModel: DualRowModeScreenViewModel = viewModel()
            viewModel.selectedNumber.value = selectedNumber ?: 0
            DualRowModeScreen(navController, viewModel)
        }
        composable("inputBoxes?selectedNumber={selectedNumber}") { backStackEntry ->
            val selectedNumber = backStackEntry.arguments?.getString("selectedNumber")?.toIntOrNull()
            val viewModel: InputBoxesModeScreenViewModel = viewModel()
            viewModel.selectedNumber.value = selectedNumber
            InputBoxesModeScreen(navController, viewModel)
        }
        composable("connectingNumbers") {
            val viewModel: PokemonModeScreenViewModel = viewModel()
            PokemonModeScreen(navController, viewModel)
        }
        composable("timedChallenge?selectedNumber={selectedNumber}") { backStackEntry ->
            val selectedNumber = backStackEntry.arguments?.getString("selectedNumber")?.toIntOrNull()
            EyeTestModeScreen(navController, selectedNumber ?: 0)
        }
        composable("hiddenCard?selectedNumber") {
            val viewModel : HiddenCardScreenViewModel = viewModel()
            HiddenCardScreen(navController, viewModel)
        }
    }
}