package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

class DualRowModeScreenViewModel : ViewModel() {
    val selectedNumber = mutableStateOf(0)
    val upperRowNumbers = mutableStateOf(List(15) { Random.nextInt(1, 10) })
    val lowerRowNumbers = mutableStateOf(List(15) { Random.nextInt(1, 10) })
    val message = mutableStateOf("")
    val upperRowColors = mutableStateOf(List(15) { Color.Black })
    val lowerRowColors = mutableStateOf(List(15) { Color.Black })
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)
    private var firstClick: Pair<Int, Int>? = null

    init {
        startScrolling()
    }

    private fun startScrolling() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(1000)
                shiftLeft()
            }
        }
    }

    fun selectNumberForPlayer1(index: Int) {
        firstClick = Pair(1, index)
        updateBorderColor(1, index, Color.Yellow)
    }

    fun selectNumberForPlayer2(index: Int) {
        if (firstClick != null) {
            val firstNumber = upperRowNumbers.value[firstClick!!.second]
            val secondNumber = lowerRowNumbers.value[index]
            if (firstNumber + secondNumber == selectedNumber.value) {
                updateBorderColor(1, firstClick!!.second, Color.Green)
                updateBorderColor(2, index, Color.Green)
                score.value += 10
                if (score.value >= 50) {
                    gameWon.value = true
                }
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    updateBorderColor(1, firstClick!!.second, Color.Black)
                    updateBorderColor(2, index, Color.Black)
                }
            } else {
                updateBorderColor(1, firstClick!!.second, Color.Red)
                updateBorderColor(2, index, Color.Red)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    updateBorderColor(1, firstClick!!.second, Color.Black)
                    updateBorderColor(2, index, Color.Black)
                }
            }
            firstClick = null
        }
    }

    private fun updateBorderColor(row: Int, index: Int, color: Color) {
        if (row == 1) {
            val newColors = upperRowColors.value.toMutableList()
            newColors[index] = color
            upperRowColors.value = newColors
        } else {
            val newColors = lowerRowColors.value.toMutableList()
            newColors[index] = color
            lowerRowColors.value = newColors
        }
    }

    private fun shiftLeft() {
        upperRowNumbers.value = upperRowNumbers.value.drop(1) + upperRowNumbers.value.first()
        lowerRowNumbers.value = lowerRowNumbers.value.drop(1) + lowerRowNumbers.value.first()
        upperRowColors.value = upperRowColors.value.drop(1) + upperRowColors.value.first()
        lowerRowColors.value = lowerRowColors.value.drop(1) + lowerRowColors.value.first()
    }
}