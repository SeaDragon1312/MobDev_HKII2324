package com.example.finalproject.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InputBoxesModeScreenViewModel : ViewModel() {
    val box1Value = mutableStateOf("")
    val box2Value = mutableStateOf("")
    val box1BorderColor = mutableStateOf(Color.Black)
    val box2BorderColor = mutableStateOf(Color.Black)
    val selectedNumber = mutableStateOf<Int?>(null)
    val validationMessage = mutableStateOf("")
    val score = mutableStateOf(0)
    val gameWon = mutableStateOf(false)

//    fun setEnteredNumber(number: Int) {
//        selectedNumber.value = number
//    }

    fun validateSum() {
        val box1Number = box1Value.value.toIntOrNull()
        val box2Number = box2Value.value.toIntOrNull()
        if (box1Number != null && box2Number != null) {
            if (box1Number + box2Number == selectedNumber.value) {
                box1BorderColor.value = Color.Green
                box2BorderColor.value = Color.Green
                validationMessage.value = "Correct"
                score.value += 10
                if (score.value >= 50) {
                    gameWon.value = true
                }
            } else {
                box1BorderColor.value = Color.Red
                box2BorderColor.value = Color.Red
                validationMessage.value = "Incorrect"
            }
            viewModelScope.launch {
                delay(2000)
                box1Value.value = ""
                box2Value.value = ""
                box1BorderColor.value = Color.Black
                box2BorderColor.value = Color.Black
                validationMessage.value = ""
            }
        }
    }
}