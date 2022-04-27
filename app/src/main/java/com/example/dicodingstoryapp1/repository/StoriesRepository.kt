package com.example.dicodingstoryapp1.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.dicodingstoryapp1.api.ApiService
import com.example.dicodingstoryapp1.api.ListStoryItem
import com.example.dicodingstoryapp1.database.StoriesDatabase

class StoriesRepository(private val storiesDatabase: StoriesDatabase, private val apiService: ApiService) {
    fun getStoriesForPaging(header: String) : LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService, header)
            }
        ).liveData
    }
}