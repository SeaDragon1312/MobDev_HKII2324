package com.example.finalproject.screens

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class AddNumberScreenViewModel : ViewModel() {
    private val _enteredNumber = mutableStateOf("")
    val enteredNumber: State<String> = _enteredNumber

    fun updateEnteredNumber(number: String) {
        _enteredNumber.value = number
    }
}