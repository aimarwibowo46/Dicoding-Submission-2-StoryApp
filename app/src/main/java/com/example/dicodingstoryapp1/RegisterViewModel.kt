package com.example.dicodingstoryapp1

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstoryapp1.database.User
import com.example.dicodingstoryapp1.repository.UserRepository

class RegisterViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun insert(user: User) {
        mUserRepository.insert(user)
    }

    fun isEmailListed(email: String): LiveData<List<User>> {
        return mUserRepository.isEmailListed(email)
    }

}