package com.example.dicodingstoryapp1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAuth(
    val token: String,
    val isLogin: Boolean
) : Parcelable
