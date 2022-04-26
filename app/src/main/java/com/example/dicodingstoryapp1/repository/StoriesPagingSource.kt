package com.example.dicodingstoryapp1.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dicodingstoryapp1.api.ApiService
import com.example.dicodingstoryapp1.api.ListStoryItem

class StoriesPagingSource(private val apiService: ApiService, private val header: String) : PagingSource<Int, ListStoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStoriesForPaging(header, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if(responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch(exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}