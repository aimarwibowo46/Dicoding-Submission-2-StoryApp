package com.example.dicodingstoryapp1.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dicodingstoryapp1.database.User
import com.example.dicodingstoryapp1.database.UserDao
import com.example.dicodingstoryapp1.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.UserDao()
    }

    fun isEmailListed(email: String): LiveData<List<User>> {
        Log.d("User Repository", "isEmailListed: ${mUserDao.isEmailListed(email)}")
        return mUserDao.isEmailListed(email)
    }

    fun insert(user: User) {
        executorService.execute {mUserDao.insert(user)}
    }
}