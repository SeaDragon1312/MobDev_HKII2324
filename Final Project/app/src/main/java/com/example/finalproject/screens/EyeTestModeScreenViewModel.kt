package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EyeTestScreenViewModel : ViewModel() {
    var topClicks = mutableStateOf(0)
    var bottomClicks = mutableStateOf(0)
    var topClicksFinal = mutableStateOf(0)
    var resultText = mutableStateOf("")
    var resultColor = mutableStateOf(Color.Black)
    var isTopImageDone = mutableStateOf(false)
    var isSubmitted = mutableStateOf(false)
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)
    val timeRemaining = mutableStateOf(300) // 300 seconds countdown
    val isRunOutOfTime = mutableStateOf(false)
    val shownMessage = mutableStateOf(false)

    init {
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

    fun onTopImageClick() {
        if (!isTopImageDone.value) {
            topClicks.value++
        }
    }

    fun onBottomImageClick() {
        if (isTopImageDone.value) {
            bottomClicks.value++
        }
    }

    fun onDoneClick() {
        topClicksFinal.value = topClicks.value
        isTopImageDone.value = true
    }

    fun onSubmitClick(selectedNumber: Int) {
        shownMessage.value = true
        val totalClicks = topClicksFinal.value + bottomClicks.value
        if (totalClicks == selectedNumber) {
            resultText.value = "Correct"
            resultColor.value = Color.Green
            score.value += 10
            if (score.value >= 30) {
                gameWon.value = true
            }
        } else {
            resultText.value = "Incorrect"
            resultColor.value = Color.Red
        }
        viewModelScope.launch {
            delay(1000)
            shownMessage.value = false
            resetClicks()
        }
    }

    private fun resetClicks() {
        topClicks.value = 0
        bottomClicks.value = 0
        topClicksFinal.value = 0
        isTopImageDone.value = false
        isSubmitted.value = true
    }
}