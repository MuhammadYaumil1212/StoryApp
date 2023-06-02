package org.d3if00001.storyapp.presentations.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.databinding.FragmentAddStoryBinding
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel

@AndroidEntryPoint
class AddStory : Fragment() {
    private val viewModel by viewModels<AddStoryViewModel>()
    private lateinit var binding:FragmentAddStoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener {view->
            view.findNavController().navigate(R.id.action_addStory_to_homeFragment)
        }
    }
}