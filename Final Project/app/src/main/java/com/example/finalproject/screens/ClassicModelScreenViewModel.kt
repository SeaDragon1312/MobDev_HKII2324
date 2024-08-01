package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val gameWon = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    val isRunOutOfTime = mutableStateOf(false)
    val borderColors = mutableStateMapOf<Int, Color>().apply {
        for (i in 1..50) {
            this[i] = Color.Black
        }
    }

    init {
        generateNumbers()
        startCountdown()
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (timeRemaining.value > 0) {
                delay(1000)
                timeRemaining.value -= 1
            }
            if (timeRemaining.value == 0) {
                isRunOutOfTime.value = true
            }
        }
    }

    private fun generateNumbers() {
        numbers.clear()
        val uniqueNumbers = mutableSetOf<Int>()
        val targetNumber = selectedNumber.value ?: 50
        while (uniqueNumbers.size < 2) { // choose first number that not equal to target number and half of target number (if target number is even so half of it is equal to target number/2)
            val random = (1..targetNumber).random()
            if(random != targetNumber && random != targetNumber/2) {
                uniqueNumbers.add(random)
            }
        }
        uniqueNumbers.add(targetNumber - uniqueNumbers.first())
        while (uniqueNumbers.size < 30) { // 30 unique numbers
            uniqueNumbers.add((1..50).random())
        }
        //Shuffle the unique numbers and add them to the list to ensure 2 first elements far from each other
        numbers.addAll(uniqueNumbers.shuffled())
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
                    gameWon.value = true
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
            if (target - num in numSet && target != num*2) {
                return true
            }
        }
        return false
    }
}