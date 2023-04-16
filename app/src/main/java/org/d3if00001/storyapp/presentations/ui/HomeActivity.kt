package org.d3if00001.storyapp.presentations.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if00001.storyapp.presentations.utils.HomeListAdapter
import org.d3if00001.storyapp.domain.models.Notes
import org.d3if00001.storyapp.databinding.ActivityHomeBinding
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvNotes : RecyclerView
    private lateinit var listNotes : ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvNotes = binding.rvNotes
        listNotes = ArrayList()
        rvNotes.setHasFixedSize(true)

        //setRecyclerView
        setRecyclerView()
    }

    private fun setRecyclerView() {
        if(listNotes.isEmpty()){
            binding.textNotAvailable.visibility = View.VISIBLE
        }
        rvNotes.layoutManager = LinearLayoutManager(this)
        val listNotesAdapter = HomeListAdapter(supportFragmentManager,listNotes)
        rvNotes.adapter = listNotesAdapter
    }

    override fun onClick(v: View?) {

    }
}