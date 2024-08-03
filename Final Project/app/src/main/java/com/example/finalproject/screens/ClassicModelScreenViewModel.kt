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
    val selectedNumber = mutableStateOf(10)
    val numbers = mutableStateOf<List<List<Int>>>(emptyList())
    val borderColors = mutableStateOf<List<List<Color>>>(emptyList())
    val validationMessage = mutableStateOf("")
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)
    val isRunOutOfTime = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    private var firstSelected: Pair<Int, Int>? = null
    private var secondSelected: Pair<Int, Int>? = null

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
        val uniqueNumbers = mutableListOf<Int>()
        val targetNumber = selectedNumber.value
        val random = (1..targetNumber).random()
        uniqueNumbers.add(random)
        uniqueNumbers.add(targetNumber - uniqueNumbers.first())
        while (uniqueNumbers.size < 30) { // 30 unique numbers
            uniqueNumbers.add((1..targetNumber).random())
        }
        //Shuffle the unique numbers and add them to the list to ensure 2 first elements far from each other
        val shuffledUniqueNumbers = uniqueNumbers.shuffled()
        val matrix = createMatrix(shuffledUniqueNumbers,6, 5)
        numbers.value = matrix
        borderColors.value = List(6) { List(5) { Color.Black } }
    }

    fun onNumberClick(row: Int, col: Int) {
        val currentColor = borderColors.value[row][col]
        if (currentColor == Color.Yellow) {
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = Color.Black
                }
            }
            if (firstSelected == Pair(row, col)) {
                firstSelected = null
            } else {
                secondSelected = null
            }
        } else {
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = Color.Yellow
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
            updateBorderColor(Color.Green)
            viewModelScope.launch {
                delay(1000)
                removeNumbers(firstPos, secondPos)
                checkGameWon()
            }
        } else {
            updateBorderColor(Color.Red)
            checkGameWon()
            viewModelScope.launch {
                delay(1000)
                resetSelection()
            }
        }
    }

    private fun removeNumbers(firstPos: Pair<Int, Int>, secondPos: Pair<Int, Int>) {
        val updatedNumbers = numbers.value.toMutableList().map { it.toMutableList() }
        updatedNumbers[firstPos.first][firstPos.second] = 0
        updatedNumbers[secondPos.first][secondPos.second] = 0
        numbers.value = updatedNumbers

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

    private fun updateBorderColor(color: Color) {
        firstSelected?.let { (row, col) ->
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = color
                }
            }
        }
        secondSelected?.let { (row, col) ->
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = color
                }
            }
        }
    }

    private fun resetSelection() {
        firstSelected?.let { (row, col) ->
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = Color.Black
                }
            }
        }
        secondSelected?.let { (row, col) ->
            borderColors.value = borderColors.value.toMutableList().apply {
                this[row] = this[row].toMutableList().apply {
                    this[col] = Color.Black
                }
            }
        }
        firstSelected = null
        secondSelected = null
    }
}