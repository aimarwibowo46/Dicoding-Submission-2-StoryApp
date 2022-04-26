package com.example.dicodingstoryapp1.di

import android.content.Context
import com.example.dicodingstoryapp1.api.ApiConfig
import com.example.dicodingstoryapp1.database.StoriesDatabase
import com.example.dicodingstoryapp1.repository.StoriesRepository

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()

        return StoriesRepository(database, apiService)
    }
}