package org.d3if00001.storyapp.presentations.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if00001.storyapp.data.database.Item.StoryResponseItem
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.databinding.ItemNotesBinding
import org.d3if00001.storyapp.presentations.ui.HomeFragmentDirections

class StoryAdapter:PagingDataAdapter<StoryResponseItem,StoryAdapter.ViewHolder>(
    DIFF_CALLBACK){
    inner class ViewHolder(private val binding:ItemNotesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(getStoryResult: StoryResponseItem){
            with(binding){
                description.text = getStoryResult.description
                Glide.with(itemView.context).load(getStoryResult.photo).into(photo)
                detailStory.setOnClickListener {view->
                    val toDetailStoryBinding = HomeFragmentDirections.actionHomeFragmentToDetailStory2()
                    toDetailStoryBinding.id = getStoryResult.id
                    view.findNavController().navigate(toDetailStoryBinding)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseItem>() {
            override fun areItemsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}