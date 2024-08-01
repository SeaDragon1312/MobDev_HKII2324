package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class PokemonModeScreenViewModel : ViewModel() {
    val selectedNumber = mutableStateOf<Int?>(10)
    val numbers = mutableStateOf<List<List<Int>>>(emptyList())
    val borderColors = mutableStateOf<List<List<Color>>>(emptyList())
    val validationMessage = mutableStateOf("")
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)

    init {
        generateNumbers()
    }

//    fun setEnteredNumber(number: Int) {
//        selectedNumber.value = number
//        generateNumbers()
//    }

    private fun generateNumbers() {
        val targetNumber = selectedNumber.value ?: 10
        val randomNumbers = mutableListOf<Int>()
        while (randomNumbers.size < 25) {
            randomNumbers.add(Random.nextInt(1, targetNumber))
        }
        numbers.value = randomNumbers.chunked(5)
        borderColors.value = List(5) { List(5) { Color.Black } }
    }

    fun validateSum() {
        // Implement validation logic if needed
    }

    fun canConnectDirectly(board: Array<IntArray>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
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

    fun canConnectWithOneBend(board: Array<IntArray>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
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

    fun canConnectWithTwoBends(board: Array<IntArray>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
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

    fun canConnect(board: Array<IntArray>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
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