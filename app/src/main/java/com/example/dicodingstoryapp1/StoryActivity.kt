package com.example.dicodingstoryapp1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingstoryapp1.databinding.ActivityStoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryActivity : AppCompatActivity() {

    private lateinit var storyViewModel: StoryViewModel
    private lateinit var activityStoryBinding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStoryBinding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(activityStoryBinding.root)

        setupViewModel()

        val layoutManager = LinearLayoutManager(this)
        activityStoryBinding.rvStories.layoutManager = layoutManager

        val token = intent.getStringExtra(TOKEN)
        Log.d(TAG, "onCreate: $token")

        getStories()
    }

    private fun setupViewModel() {
        storyViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[StoryViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.menu2 -> {
                storyViewModel.logout()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return true
    }

    private fun getStories() {
        val client = ApiConfig.getApiService().getStories()
        client.enqueue(object: Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null) {
                    Log.d(TAG, "onResponse: $responseBody")
                    setStoriesData(responseBody.listStory)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setStoriesData(items: List<ListStoryItem>) {
        val listStories = ArrayList<Story>()
        for(item in items) {
            val story = Story(
                item.name,
                item.photoUrl,
                item.description
            )
            listStories.add(story)
        }

        val adapter = StoryAdapter(listStories)
        activityStoryBinding.rvStories.adapter = adapter
    }

    companion object {
        private const val TAG = "Story Activity"
        const val TOKEN = "token"
    }
}