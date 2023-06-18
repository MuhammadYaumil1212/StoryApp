package org.d3if00001.storyapp.presentations.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.databinding.ItemNotesBinding
import org.d3if00001.storyapp.presentations.ui.HomeFragment
import org.d3if00001.storyapp.presentations.ui.HomeFragmentDirections

class StoryAdapter(val context: Context) :RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private val listNotes:ArrayList<getStoryResult> = ArrayList()
    inner class ViewHolder(private val binding:ItemNotesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(getStoryResult: getStoryResult){
            with(binding){
                description.text = getStoryResult.description
                Glide.with(context).load(getStoryResult.photo).into(photo)
                detailStory.setOnClickListener {view->
                    val toDetailStoryBinding = HomeFragmentDirections.actionHomeFragmentToDetailStory2()
                    toDetailStoryBinding.id = getStoryResult.id
                    view.findNavController().navigate(toDetailStoryBinding)
                }
            }
        }
    }
    fun setListNotes(listNotes: ArrayList<getStoryResult>){
        val diffCallback = StoryDiffUtil(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =holder.bind(listNotes[position])
}