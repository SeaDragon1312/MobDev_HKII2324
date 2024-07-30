package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClassicModeScreenViewModel : ViewModel() {
    val numbers = mutableStateListOf<Int>()
    val selectedNumber = mutableStateOf<Int?>(null)
    val selectedNumbers = mutableStateListOf<Int>()
    val message = mutableStateOf("")
    val messageColor = mutableStateOf(Color.Black)
    val borderColors = mutableStateMapOf<Int, Color>().apply {
        for (i in 1..50) {
            this[i] = Color.Black
        }
    }

    init {
        generateNumbers()
    }

    private fun generateNumbers() {
        numbers.clear()
        val uniqueNumbers = mutableSetOf<Int>()
        while (uniqueNumbers.size < 30) { // 30 unique numbers
            uniqueNumbers.add((1..50).random())
        }
        numbers.addAll(uniqueNumbers)
    }

    fun selectNumber(number: Int) {
        if (selectedNumbers.contains(number)) {
            selectedNumbers.remove(number)
            borderColors[number] = Color.Black
        } else if (selectedNumbers.size < 2) {
            selectedNumbers.add(number)
            borderColors[number] = Color.Yellow
        }
        if (selectedNumbers.size == 2) {
            checkSum()
        }
    }

    private fun checkSum() {
        val sum = selectedNumbers.sum()
        if (sum == selectedNumber.value) {
            selectedNumbers.forEach { number ->
                borderColors[number] = Color.Green
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                selectedNumbers.forEach { number ->
                    numbers[numbers.indexOf(number)] = -1 // Mark the number as removed
                    borderColors[number] = Color.Black
                }
                selectedNumbers.clear()
                if (!hasValidPairs()) {
                    message.value = "win"
                    messageColor.value = Color.Blue
                }
            }
        } else {
            selectedNumbers.forEach { number ->
                borderColors[number] = Color.Red
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                selectedNumbers.forEach { number ->
                    borderColors[number] = Color.Black
                }
                selectedNumbers.clear()
            }
        }
    }

    private fun hasValidPairs(): Boolean {
        val target = selectedNumber.value ?: return false
        val numSet = numbers.filter { it != -1 }.toSet()
        for (num in numSet) {
            if (target - num in numSet) {
                return true
            }
        }
        return false
    }
}