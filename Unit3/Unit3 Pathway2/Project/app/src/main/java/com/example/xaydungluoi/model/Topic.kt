package com.example.xaydungluoi.model

import android.adservices.adid.AdId
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val courseName: Int,
    val numberOfCourse: Int,
    @DrawableRes val courseImage: Int
)
