package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color

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
        resetClicks()
    }

    private fun resetClicks() {
        topClicks.value = 0
        bottomClicks.value = 0
        topClicksFinal.value = 0
        isTopImageDone.value = false
        isSubmitted.value = true
    }
}