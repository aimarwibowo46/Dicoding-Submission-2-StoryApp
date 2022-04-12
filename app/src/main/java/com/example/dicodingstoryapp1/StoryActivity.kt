package com.example.dicodingstoryapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstoryapp1.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {

    private lateinit var activityStoryBinding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStoryBinding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(activityStoryBinding.root)
    }
}