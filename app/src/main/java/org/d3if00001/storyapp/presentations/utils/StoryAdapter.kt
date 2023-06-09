package org.d3if00001.storyapp.presentations.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.databinding.ItemNotesBinding

class StoryAdapter(private val listNotes:List<StoryResult>) :RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemNotesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_,description,_,name,_) =listNotes[position]
        holder.binding.titleStory.text = name
        holder.binding.description.text = description
    }
}