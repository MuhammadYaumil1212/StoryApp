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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.databinding.FragmentHomeBinding
import org.d3if00001.storyapp.presentations.utils.LoadingStateAdapter
import org.d3if00001.storyapp.presentations.utils.StoryAdapter
import org.d3if00001.storyapp.presentations.viewmodels.AuthenticationViewModel
import org.d3if00001.storyapp.presentations.viewmodels.StoryViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
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

    override fun onResume() {
        super.onResume()
        StoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotes.setHasFixedSize(true)
        binding.pgHome.visibility = View.GONE
        storyViewModel.getNameUser.observe(viewLifecycleOwner) { name ->
            binding.textUsername.text = name
        }
        //data story
        setAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvNotes.addItemDecoration(itemDecoration)

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

    private fun setAdapter(){
       lifecycleScope.launch {
           val adapter = StoryAdapter()
           binding.rvNotes.adapter = adapter.withLoadStateFooter(
               footer = LoadingStateAdapter{
                   adapter.retry()
               }
           )
           storyViewModel.story.collectLatest {pagingData->
               adapter.submitData(pagingData)
           }
       }
    }

}