package org.d3if00001.storyapp.presentations.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.databinding.FragmentHomeBinding
import org.d3if00001.storyapp.domain.models.Notes
import org.d3if00001.storyapp.presentations.utils.HomeListAdapter
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
import org.d3if00001.storyapp.presentations.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvNotes : RecyclerView
    private lateinit var listNotes : ArrayList<Notes>
    private val homeViewModel : HomeViewModel by viewModels()
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes = binding.rvNotes
        listNotes = ArrayList()
        rvNotes.setHasFixedSize(true)
        binding.pgHome.visibility = View.GONE
        if (authenticationViewModel.getLoggedIn() == true){
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        binding.fabPlus.setOnClickListener {
            // Aksi saat fabPlus diklik
        }

        binding.logout.setOnClickListener {
            authenticationViewModel.logout()
            it.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        //setRecyclerView
        setRecyclerView()
    }
    private fun setRecyclerView() {
        if(listNotes.isEmpty()){
            binding.textNotAvailable.visibility = View.VISIBLE
        }
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        val listNotesAdapter = HomeListAdapter(childFragmentManager,listNotes)
        rvNotes.adapter = listNotesAdapter
    }
}