package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HiddenCardScreenViewModel : ViewModel() {
    val selectedNumber = mutableStateOf(10)
    val numbers = mutableStateOf<List<List<Int>>>(emptyList())
    val validationMessage = mutableStateOf("")
    val gameWon = mutableStateOf(false)
    val isRunOutOfTime = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    private var firstSelected: Pair<Int, Int>? = null
    private var secondSelected: Pair<Int, Int>? = null
    val shownNumbers = mutableStateOf<List<List<Boolean>>>(emptyList())
    val lives = mutableStateOf(10)
    val gameOver = mutableStateOf(false)

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

    private fun createMatrix(numbers: List<Int>, rows: Int, cols: Int): List<List<Int>> {
        require(numbers.size >= rows * cols) {
            "List must have at least ${rows * cols} elements."
        }

        return numbers.take(rows * cols).chunked(cols)
    }

    private fun generateNumbers() {
        val uniqueNumbers = listOf(1, 1, 2,2, 3,3, 4,4, 5,5,5,5, 6,6, 7,7, 8,8, 9,9)
//        val targetNumber = selectedNumber.value
//        val random = (1..targetNumber).random()
//        uniqueNumbers.add(random)
//        uniqueNumbers.add(targetNumber - uniqueNumbers.first())
//        while (uniqueNumbers.size < 30) { // 30 unique numbers
//            uniqueNumbers.add((1..targetNumber).random())
//        }
        //Shuffle the unique numbers and add them to the list to ensure 2 first elements far from each other
        val shuffledUniqueNumbers = uniqueNumbers.shuffled()
        val matrix = createMatrix(shuffledUniqueNumbers,4, 5)
        numbers.value = matrix
        shownNumbers.value = List(4) { List(5) { false } }
    }

    fun onNumberClick(row: Int, col: Int) {
        val currentShown = shownNumbers.value[row][col]
        if (currentShown) {
            shownNumbers.value = shownNumbers.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = false
                }
            }
            if (firstSelected == Pair(row, col)) {
                firstSelected = null
            } else {
                secondSelected = null
            }
        } else {
            shownNumbers.value = shownNumbers.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = true
                }
            }
            if (firstSelected == null) {
                firstSelected = Pair(row, col)
            } else {
                secondSelected = Pair(row, col)
                checkSum()
            }
        }
    }

    private fun checkSum() {
        val firstPos = firstSelected ?: return
        val secondPos = secondSelected ?: return
        val firstNumber = numbers.value[firstPos.first][firstPos.second]
        val secondNumber = numbers.value[secondPos.first][secondPos.second]

        if (firstNumber + secondNumber == selectedNumber.value) {
            viewModelScope.launch {
                delay(1000)
                removeNumbers(firstPos, secondPos)
                checkGameWon()
            }
        } else {
            viewModelScope.launch {
                delay(1000)
                hideNumbers(firstPos, secondPos)
                decrementLives()
            }
        }
    }

    private fun decrementLives() {
        lives.value -= 1
        if (lives.value <= 0) {
            gameOver.value = true
        }
    }

    private fun removeNumbers(firstPos: Pair<Int, Int>, secondPos: Pair<Int, Int>) {
        val updatedNumbers = numbers.value.toMutableList().map { it.toMutableList() }
        updatedNumbers[firstPos.first][firstPos.second] = 0
        updatedNumbers[secondPos.first][secondPos.second] = 0
        numbers.value = updatedNumbers

        resetSelection()
    }

    private fun hideNumbers(firstPos: Pair<Int, Int>, secondPos: Pair<Int, Int>) {
        shownNumbers.value = shownNumbers.value.toMutableList().apply {
            this[firstPos.first] = this[firstPos.first].toMutableList().apply {
                this[firstPos.second] = false
            }
            this[secondPos.first] = this[secondPos.first].toMutableList().apply {
                this[secondPos.second] = false
            }
        }
        resetSelection()
    }

    private fun checkGameWon() {
        for (row in numbers.value.indices) {
            for (col in numbers.value[row].indices) {
                val number = numbers.value[row][col]
                if (number != 0) {
                    for (i in numbers.value.indices) {
                        for (j in numbers.value[i].indices) {
                            if ((i != row || j != col) && numbers.value[i][j] != 0 && number + numbers.value[i][j] == selectedNumber.value) {
                                return
                            }
                        }
                    }
                }
            }
        }
        gameWon.value = true
    }

    private fun resetSelection() {
        firstSelected = null
        secondSelected = null
    }
}