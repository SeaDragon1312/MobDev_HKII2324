package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ClassicModeScreenViewModel : ViewModel() {
    val numbers = mutableStateListOf<Int>()
    val selectedNumber = mutableStateOf<Int?>(null)
    val selectedNumbers = mutableStateListOf<Int>()
    val message = mutableStateOf("")

    init {
        generateNumbers()
    }

    private fun generateNumbers() {
        numbers.clear()
        for (i in 1..30) { // 30 numbers
            numbers.add((1..50).random())
        }
    }

    fun selectNumber(number: Int) {
        if (selectedNumbers.size < 2) {
            selectedNumbers.add(number)
        }
        if (selectedNumbers.size == 2) {
            checkSum()
        }
    }

    private fun checkSum() {
        val sum = selectedNumbers.sum()
        if (sum == selectedNumber.value) {
            numbers.removeAll(selectedNumbers)
            message.value = "Correct!"
        } else {
            message.value = "The selected number is incorrect"
        }
        selectedNumbers.clear()
    }
}