package com.example.finalproject.screens

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class AddNumberScreenViewModel : ViewModel() {
    private val _enteredNumber = mutableStateOf("")
    val enteredNumber: State<String> = _enteredNumber

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun updateEnteredNumber(number: String) {
        _enteredNumber.value = number
    }

    fun validateNumber() {
        val number = _enteredNumber.value.toIntOrNull()
        if (number == null || number < 10 || number > 50) {
            _errorMessage.value = "Please enter a number between 10 and 50"
        } else {
            _errorMessage.value = ""
        }
    }
}