package com.example.dicodingstoryapp1

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StoryAdapter(private val listStories: ArrayList<Story>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto: ImageView = view.findViewById(R.id.img_photo)
        val tvName: TextView = view.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.stories_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listStories[position].name
        Glide.with(viewHolder.itemView.context)
            .load(listStories[position].photo)
            .circleCrop()
            .into(viewHolder.imgPhoto)

        viewHolder.itemView.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                viewHolder.itemView.context as Activity,
                Pair(viewHolder.imgPhoto, "img_photo_detail_transition"),
                Pair(viewHolder.tvName, "tv_name_detail_transition"),
            )

            val intent = Intent(viewHolder.itemView.context, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.DETAIL_STORY, listStories[position])
            viewHolder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return listStories.size
    }
}