package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DualRowModeScreenViewModel : ViewModel() {
    private val _upperRowNumbers = MutableStateFlow<List<Int>>(emptyList())
    val upperRowNumbers: StateFlow<List<Int>> get() = _upperRowNumbers

    private val _lowerRowNumbers = MutableStateFlow<List<Int>>(emptyList())
    val lowerRowNumbers: StateFlow<List<Int>> get() = _lowerRowNumbers

    val selectedNumber = mutableStateOf(10)
    private val _upperBorderColor = MutableStateFlow<List<Color>>(emptyList())
    private val _lowerBorderColor = MutableStateFlow<List<Color>>(emptyList())

    val upperBorderColor: StateFlow<List<Color>> get() = _upperBorderColor
    val lowerBorderColor: StateFlow<List<Color>> get() = _lowerBorderColor
    private var selectedUpperIndex: Int? = null
    private var selectedLowerIndex: Int? = null

    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    val isRunOutOfTime = mutableStateOf(false)

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

    fun generateNumbers() {
        val targetNumber = selectedNumber.value
        _upperRowNumbers.value = List(20) { Random.nextInt(1, targetNumber) }
        _lowerRowNumbers.value = List(20) { Random.nextInt(1, targetNumber) }
        _upperBorderColor.value = List(20) { Color.Black }
        _lowerBorderColor.value = List(20) { Color.Black }
    }


    fun selectUpperCell(index: Int) {
        if (selectedUpperIndex == index) {
            selectedUpperIndex = null
            _upperBorderColor.value = upperBorderColor.value.toMutableList().apply { this[index] = Color.Black }
        } else {
            selectedUpperIndex = index
            _upperBorderColor.value = List(20) { Color.Black }
            _upperBorderColor.value = upperBorderColor.value.toMutableList().apply { this[index] = Color.Yellow }
        }
        validateSelection()
    }

    fun selectLowerCell(index: Int) {
        if (selectedLowerIndex == index) {
            selectedLowerIndex = null
            _lowerBorderColor.value = lowerBorderColor.value.toMutableList().apply { this[index] = Color.Black }
        } else {
            selectedLowerIndex = index
            _lowerBorderColor.value = List(20) { Color.Black }
            _lowerBorderColor.value = lowerBorderColor.value.toMutableList().apply { this[index] = Color.Yellow }
        }
        validateSelection()
    }

    private fun validateSelection() {
        val upperIndex = selectedUpperIndex
        val lowerIndex = selectedLowerIndex
        if (upperIndex != null && lowerIndex != null) {
            val upperValue = upperRowNumbers.value[upperIndex]
            val lowerValue = lowerRowNumbers.value[lowerIndex]
            val isValid = upperValue + lowerValue == selectedNumber.value
            if (isValid) {
                score.value += 10
                if (score.value >= 30) {
                    gameWon.value = true
                }
            }
            val color = if (isValid) Color.Green else Color.Red
            _upperBorderColor.value = _upperBorderColor.value.toMutableList().apply { this[upperIndex] = color }
            _lowerBorderColor.value = _lowerBorderColor.value.toMutableList().apply { this[lowerIndex] = color }
            viewModelScope.launch {
                delay(1000)
                resetSelection()
            }
        }
    }

    private fun resetSelection() {
        selectedUpperIndex = null
        selectedLowerIndex = null
        _upperBorderColor.value = List(20) { Color.Black }
        _lowerBorderColor.value = List(20) { Color.Black }
    }
}