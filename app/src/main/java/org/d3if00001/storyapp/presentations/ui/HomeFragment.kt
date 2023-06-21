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

        authenticationViewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
        storyViewModel.getNameUser.observe(viewLifecycleOwner) { name ->
            binding.textUsername.text = name
        }
        //data story
        getAllStories()
        storyViewModel.getStatus().observe(viewLifecycleOwner){
            updateProgress(it)
        }

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
    private fun getAllStories(){
        storyViewModel.getAllStories()
        storyViewModel.getAllStories.observe(viewLifecycleOwner){ listStory->
            listStory?.map { fragmentStory->
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
    }

    private fun updateProgress(status: APIService.ApiStatus?) {
        when(status){
            APIService.ApiStatus.SUCCESS->{
                binding.pgHome.visibility =View.GONE
            }
            APIService.ApiStatus.LOADING->binding.pgHome.visibility = View.VISIBLE
            APIService.ApiStatus.FAILED->{
                binding.pgHome.visibility = View.GONE
                Toast.makeText(requireContext(),
                    "Data Tidak Tersedia!",
                    Toast.LENGTH_SHORT).show()
            }
            else ->binding.pgHome.visibility = View.GONE
        }
    }
}