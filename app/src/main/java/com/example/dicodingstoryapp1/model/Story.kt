package com.example.dicodingstoryapp1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var name: String? = null,
    var photo: String? = null,
    var description: String? = null,
    var lat: Double? = null,
    var lon: Double? = null
) : Parcelable
