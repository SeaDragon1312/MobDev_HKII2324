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
    val upperRowColors = mutableStateOf(List(10) { Color.Black })
    val lowerRowColors = mutableStateOf(List(10) { Color.Black })
    private var selectedUpperIndex: Int? = null
    private var selectedLowerIndex: Int? = null

    init {
        generateNumbers()
        startScrolling()
    }

    fun generateNumbers() {
        val targetNumber = selectedNumber.value
        _upperRowNumbers.value = List(10) { Random.nextInt(1, targetNumber + 1) }
        _lowerRowNumbers.value = List(10) { Random.nextInt(1, targetNumber + 1) }
    }

    private fun startScrolling() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                shiftLeft()
            }
        }
    }

    private fun shiftLeft() {
        _upperRowNumbers.value = _upperRowNumbers.value.drop(1) + _upperRowNumbers.value.first()
        _lowerRowNumbers.value = _lowerRowNumbers.value.drop(1) + _lowerRowNumbers.value.first()
    }

    fun selectUpperCell(index: Int) {
        if (selectedUpperIndex == index) {
            selectedUpperIndex = null
            upperRowColors.value = upperRowColors.value.toMutableList().apply { this[index] = Color.Black }
        } else {
            selectedUpperIndex = index
            upperRowColors.value = List(10) { i -> if (i == index) Color.Yellow else Color.Black }
        }
        validateSelection()
    }

    fun selectLowerCell(index: Int) {
        if (selectedLowerIndex == index) {
            selectedLowerIndex = null
            lowerRowColors.value = lowerRowColors.value.toMutableList().apply { this[index] = Color.Black }
        } else {
            selectedLowerIndex = index
            lowerRowColors.value = List(10) { i -> if (i == index) Color.Yellow else Color.Black }
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
            val color = if (isValid) Color.Green else Color.Red
            upperRowColors.value = upperRowColors.value.toMutableList().apply { this[upperIndex] = color }
            lowerRowColors.value = lowerRowColors.value.toMutableList().apply { this[lowerIndex] = color }
            viewModelScope.launch {
                delay(1000)
                resetSelection()
            }
        }
    }

    private fun resetSelection() {
        selectedUpperIndex = null
        selectedLowerIndex = null
        upperRowColors.value = List(10) { Color.Black }
        lowerRowColors.value = List(10) { Color.Black }
    }
}