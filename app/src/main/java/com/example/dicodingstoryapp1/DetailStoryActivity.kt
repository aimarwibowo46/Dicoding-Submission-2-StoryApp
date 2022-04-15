package com.example.dicodingstoryapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dicodingstoryapp1.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var activityDetailStoryBinding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailStoryBinding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(activityDetailStoryBinding.root)

        val story = intent.getParcelableExtra<Story>(DETAIL_STORY) as Story
        Glide.with(this)
            .load(story.photo)
            .into(activityDetailStoryBinding.imgPhotoDetail)
        activityDetailStoryBinding.tvNameDetail.text = story.name
        activityDetailStoryBinding.tvDescriptionDetail.text = story.description
    }

    companion object {
        const val DETAIL_STORY = "detail_story"
    }
}