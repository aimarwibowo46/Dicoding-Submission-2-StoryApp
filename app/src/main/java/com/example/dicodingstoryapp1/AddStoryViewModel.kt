package com.example.dicodingstoryapp1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class AddStoryViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser() : LiveData<UserAuth> {
        return pref.getUser().asLiveData()
    }
}