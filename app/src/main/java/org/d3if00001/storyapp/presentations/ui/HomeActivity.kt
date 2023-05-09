package org.d3if00001.storyapp.presentations.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.presentations.utils.HomeListAdapter
import org.d3if00001.storyapp.domain.models.Notes
import org.d3if00001.storyapp.databinding.ActivityHomeBinding
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvNotes : RecyclerView
    private lateinit var listNotes : ArrayList<Notes>
    private var backPressedTime = 0L
    private val backPressedInterval = 2000L
    private val homeViewModel : HomeViewModel by viewModels()
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + backPressedInterval > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Tekan kembali untuk keluar", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
            finishAffinity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvNotes = binding.rvNotes
        listNotes = ArrayList()
        rvNotes.setHasFixedSize(true)

        homeViewModel.isLoggedIn.observe(this){isLoggedIn ->
            if (!isLoggedIn){
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
        binding.textUsername.text = "loading...."
        homeViewModel.getAuthToken()
        homeViewModel.getToken.observe(this){
            Toast.makeText(this,"Token is : $it",Toast.LENGTH_SHORT).show()
        }

        binding.logout.setOnClickListener {
            homeViewModel.logout()
            startActivity(Intent(this,LoginActivity::class.java))
            Toast.makeText(this,"berhasil keluar!",Toast.LENGTH_SHORT).show()
        }

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