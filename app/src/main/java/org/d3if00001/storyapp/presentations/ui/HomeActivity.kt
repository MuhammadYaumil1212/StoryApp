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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.presentations.utils.HomeListAdapter
import org.d3if00001.storyapp.domain.models.Notes
import org.d3if00001.storyapp.databinding.ActivityHomeBinding
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
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
    private val authenticationViewModel:AuthenticationViewModel by viewModels()
    companion object{
        const val extra_id = "extra_id"
    }
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

        val isLoggedIn = authenticationViewModel.getLoggedIn()
        if(isLoggedIn == false){
            startActivity(Intent(this@HomeActivity,LoginActivity::class.java))
            Toast.makeText(this,"Session Expired",Toast.LENGTH_SHORT).show()
            finishAffinity()
        }

        binding.pgHome.visibility = View.GONE
        val id = intent.getIntExtra(extra_id,0)
        if(id == 0){
            authenticationViewModel.logout()
            Toast.makeText(this,"Session Expired",Toast.LENGTH_SHORT).show()
        }else{
            val user = homeViewModel.getUser(id)
            binding.textUsername.text = user.name
        }

        binding.fabPlus.setOnClickListener {
            startActivity(Intent(this,AddStory::class.java))
        }

        binding.logout.setOnClickListener {
            authenticationViewModel.logout()
            Toast.makeText(this,"berhasil keluar!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
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