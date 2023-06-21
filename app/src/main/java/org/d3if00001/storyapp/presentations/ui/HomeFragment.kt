package org.d3if00001.storyapp.presentations.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.databinding.FragmentHomeBinding
import org.d3if00001.storyapp.presentations.utils.StoryAdapter
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
import org.d3if00001.storyapp.presentations.viewmodels.StoryViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listNotes : ArrayList<getStoryResult>
    private val storyViewModel : StoryViewModel by viewModels()
    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listNotes = ArrayList()
        binding.rvNotes.setHasFixedSize(true)
        binding.pgHome.visibility = View.GONE

        storyViewModel.getNameUser.observe(viewLifecycleOwner) { name ->
            binding.textUsername.text = name
        }
        //data story
        storyViewModel.getAllStories()
        updateProgress()

        binding.maps.setOnClickListener {
            startActivity(Intent(context,MapsActivity::class.java))
        }
        binding.fabPlus.setOnClickListener { views ->
            views.findNavController().navigate(R.id.action_homeFragment_to_addStory)
        }
        binding.logout.setOnClickListener {
            authenticationViewModel.logout()
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment2())
        }
    }

    private fun updateProgress() {
        storyViewModel.getAllStories.observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Loading ->{
                    binding.pgHome.visibility = View.VISIBLE
                }
                is ApiResponse.Success->{
                    binding.pgHome.visibility = View.GONE
                    it.data.listStory.map { fragmentStory->
                        listNotes.add(fragmentStory)
                        if (listNotes.isEmpty()) {
                            binding.textNotAvailable.visibility = View.VISIBLE
                        } else {
                            val adapter = StoryAdapter(requireContext())
                            adapter.setListNotes(listNotes)
                            binding.rvNotes.adapter = adapter
                            val layoutManager = LinearLayoutManager(requireContext())
                            binding.rvNotes.layoutManager = layoutManager
                            val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
                            binding.rvNotes.addItemDecoration(itemDecoration)
                        }
                    }
                }
                is ApiResponse.Error->{
                    Toast.makeText(context,"Gagal Catch data!",Toast.LENGTH_SHORT).show()
                    binding.pgHome.visibility = View.GONE
                }

                else -> {
                    binding.pgHome.visibility = View.GONE
                }
            }
        }
    }
}