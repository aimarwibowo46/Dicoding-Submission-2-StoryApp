package com.example.dicodingstoryapp1

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstoryapp1.database.User
import com.example.dicodingstoryapp1.repository.UserRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun isEmailListed(email: String): LiveData<Int> {
        return mUserRepository.isEmailListed(email)
    }
}