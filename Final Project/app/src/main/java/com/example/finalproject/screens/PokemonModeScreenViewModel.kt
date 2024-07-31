package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class PokemonModeScreenViewModel : ViewModel() {
    val selectedNumber = mutableStateOf<Int?>(null)
    val numbers = mutableStateOf<List<List<Int>>>(emptyList())
    val borderColors = mutableStateOf<List<List<Color>>>(emptyList())
    val validationMessage = mutableStateOf("")
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)

    fun setEnteredNumber(number: Int) {
        selectedNumber.value = number
        generateNumbers()
    }

    private fun generateNumbers() {
        val targetNumber = selectedNumber.value ?: 10
        val randomNumbers = mutableListOf<Int>()
        while (randomNumbers.size < 25) {
            randomNumbers.add(Random.nextInt(1, targetNumber))
        }
        val numberList = randomNumbers.toList().shuffled()
        numbers.value = numberList.chunked(5)
        borderColors.value = List(5) { List(5) { Color.Black } }
    }

    fun validateSum() {
        // Implement validation logic if needed
    }
}