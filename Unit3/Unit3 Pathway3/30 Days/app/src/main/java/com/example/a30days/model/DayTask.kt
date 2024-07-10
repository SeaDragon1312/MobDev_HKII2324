package com.example.a30days.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DayTask(
    @StringRes val day : Int,
    @StringRes val dayTitle : Int,
    @DrawableRes val imageOfTheDay : Int,
    @StringRes val dayDescription : Int,
)
