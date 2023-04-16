package org.d3if00001.storyapp.presentations.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if00001.storyapp.domain.models.Notes
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.presentations.ui.DetailStory

class HomeListAdapter(private val fragmentManager: FragmentManager,private val listNotes: ArrayList<Notes>) : RecyclerView.Adapter<HomeListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleStory : TextView = itemView.findViewById(R.id.title_story)
        val description : TextView = itemView.findViewById(R.id.description)
        val detailButton : Button = itemView.findViewById(R.id.detailStory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_notes,parent,false))
    }

    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(_,titleStory,description) = listNotes[position]
        holder.titleStory.text = titleStory
        holder.description.text = description
        holder.detailButton.setOnClickListener {
            val modalBottomSheet = DetailStory()
            val bundle = Bundle()
            bundle.putString("title_story",holder.titleStory.text.toString())
            modalBottomSheet.arguments = bundle
            modalBottomSheet.show(fragmentManager, DetailStory.TAG)
        }

    }
}