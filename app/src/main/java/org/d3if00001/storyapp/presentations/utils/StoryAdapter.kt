package org.d3if00001.storyapp.presentations.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.databinding.ItemNotesBinding
import org.d3if00001.storyapp.presentations.ui.HomeFragment
import org.d3if00001.storyapp.presentations.ui.HomeFragmentDirections

class StoryAdapter(private val listNotes:List<StoryResult>,val context: Context) :RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemNotesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_,description,id,_) =listNotes[position]
        holder.binding.description.text = description
        holder.binding.detailStory.setOnClickListener {view->
            val toDetailStoryBinding = HomeFragmentDirections.actionHomeFragmentToDetailStory2()
            toDetailStoryBinding.id = id
            view.findNavController().navigate(toDetailStoryBinding)
        }
    }
}