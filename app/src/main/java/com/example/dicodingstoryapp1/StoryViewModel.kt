package com.example.dicodingstoryapp1

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dicodingstoryapp1.api.ListStoryItem
import com.example.dicodingstoryapp1.di.Injection
import com.example.dicodingstoryapp1.repository.StoriesRepository

class StoryViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {

    fun stories(header: String): LiveData<PagingData<ListStoryItem>> = storiesRepository.getStoriesForPaging(header).cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoryViewModel(Injection.provideRepository(context)) as T
            }
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}