package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class DualRowModeScreenViewModel : ViewModel() {
    val upperRowNumbers = mutableStateListOf<Int>()
    val lowerRowNumbers = mutableStateListOf<Int>()
    val selectedNumber = mutableStateOf<Int?>(null)
    val player1SelectedNumber = mutableStateOf<Int?>(null)
    val player2SelectedNumber = mutableStateOf<Int?>(null)
    val message = mutableStateOf("")

    init {
        generateNumbers()
    }

    private fun generateNumbers() {
        upperRowNumbers.clear()
        lowerRowNumbers.clear()
        val uniqueNumbers = (1..50).shuffled().take(10)
        upperRowNumbers.addAll(uniqueNumbers)
        lowerRowNumbers.addAll(uniqueNumbers.shuffled())
    }

    fun selectNumberForPlayer1(number: Int) {
        player1SelectedNumber.value = number
        checkSum()
    }

    fun selectNumberForPlayer2(number: Int) {
        player2SelectedNumber.value = number
        checkSum()
    }

    private fun checkSum() {
        val sum = (player1SelectedNumber.value ?: 0) + (player2SelectedNumber.value ?: 0)
        if (sum == selectedNumber.value) {
            message.value = "Correct! The sum is $sum."
        } else {
            message.value = "Try again."
        }
    }
}