package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class PokemonModeScreenViewModel : ViewModel() {
    val selectedNumber = mutableStateOf<Int?>(10)
    val numbers = mutableStateOf<List<List<Int>>>(emptyList())
    val borderColors = mutableStateOf<List<List<Color>>>(emptyList())
    val validationMessage = mutableStateOf("")
    val gameWon = mutableStateOf(false)
    val isRunOutOfTime = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    private var firstSelected: Pair<Int, Int>? = null
    private var secondSelected: Pair<Int, Int>? = null
    val connectingLines = mutableStateOf<List<Pair<Pair<Int, Int>, Pair<Int, Int>>>>(emptyList())
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
        val targetNumber = selectedNumber.value ?: 10
        val randomNumbers = mutableListOf<Int>()
        while (randomNumbers.size < 25) {
            randomNumbers.add(Random.nextInt(1, targetNumber))
        }

        // Cover 5 x 5 matrix with 7 x 7 matrix that has 0 on the border

        val innerMatrix = randomNumbers.chunked(5)
        val outerMatrix = List(7) { MutableList(7) { 0 } }

        for (i in 1..5) {
            for (j in 1..5) {
                outerMatrix[i][j] = innerMatrix[i - 1][j - 1]
            }
        }

        numbers.value = outerMatrix
        borderColors.value = List(7) { List(7) { Color.Black } }
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

        // Add connecting lines elements
        if (firstNumber + secondNumber == 10 && canConnect(numbers.value, firstPos, secondPos)) {
            when {
                canConnectDirectly(numbers.value, firstPos, secondPos) -> {
                    connectingLines.value = listOf(Pair(firstPos, secondPos))
                }
                canConnectWithOneBend(numbers.value, firstPos, secondPos) -> {
                    val point = listOf(Pair(firstPos.first, secondPos.second), Pair(secondPos.first, firstPos.second))
                        .first {numbers.value[it.first][it.second] == 0 && canConnectDirectly(numbers.value, firstPos, it) && canConnectDirectly(numbers.value, it, secondPos) }
                    connectingLines.value = listOf(Pair(firstPos, point), Pair(point, secondPos))
                }
                canConnectWithTwoBends(numbers.value, firstPos, secondPos) -> {
                    val points = mutableListOf<Pair<Int, Int>>()
                    for (row in numbers.value.indices) {
                        for (col in numbers.value[0].indices) {
                            if (numbers.value[row][col] == 0) {
                                // Find the first point that can connect with the second point directly and the first point with one bend
                                if (canConnectWithOneBend(numbers.value, firstPos, Pair(row, col)) &&
                                    canConnectDirectly(numbers.value, Pair(row, col), secondPos)) {
                                    points.add(Pair(row, col))
                                    break
                                }
                            }
                        }
                    }
                    val point2 = points[0] // The point connect to the end point
                    val point1 = listOf(Pair(firstPos.first, point2.second), Pair(point2.first, firstPos.second))
                        .first {numbers.value[it.first][it.second] == 0 && canConnectDirectly(numbers.value, firstPos, it) && canConnectDirectly(numbers.value, it, point2) }
                    connectingLines.value = listOf(Pair(firstPos, point1), Pair(point1, point2), Pair(point2, secondPos))
                }
            }
            updateBorderColor(Color.Green)
            viewModelScope.launch {
                delay(1000)
                removeNumbers(firstPos, secondPos)
                connectingLines.value = emptyList()
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
                            if ((i != row || j != col) && numbers.value[i][j] != 0 && number + numbers.value[i][j] == 10) {
                                if (canConnect(numbers.value, Pair(row, col), Pair(i, j))) {
                                    return
                                }
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

    private fun canConnectDirectly(board: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
        if (start.first == end.first) { // same row
            val row = start.first
            for (col in (minOf(start.second, end.second) + 1) until maxOf(start.second, end.second)) {
                if (board[row][col] != 0) {
                    return false
                }
            }
            return true
        } else if (start.second == end.second) { // same column
            val col = start.second
            for (row in (minOf(start.first, end.first) + 1) until maxOf(start.first, end.first)) {
                if (board[row][col] != 0) {
                    return false
                }
            }
            return true
        }
        return false
    }

    private fun canConnectWithOneBend(board: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
        val points = listOf(Pair(start.first, end.second), Pair(end.first, start.second))
        for (point in points) {
            if (board[point.first][point.second] == 0 &&
                canConnectDirectly(board, start, point) &&
                canConnectDirectly(board, point, end)) {
                return true
            }
        }
        return false
    }

    private fun canConnectWithTwoBends(board: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col] == 0) {
                    if (canConnectWithOneBend(board, start, Pair(row, col)) &&
                        canConnectDirectly(board, Pair(row, col), end)) {
                        return true
                    }
                    if (canConnectWithOneBend(board, Pair(row, col), end) &&
                        canConnectDirectly(board, start, Pair(row, col))) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun canConnect(board: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
        if (canConnectDirectly(board, start, end)) {
            return true
        }
        if (canConnectWithOneBend(board, start, end)) {
            return true
        }
        if (canConnectWithTwoBends(board, start, end)) {
            return true
        }
        return false
    }
}