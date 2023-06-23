package org.d3if00001.storyapp.presentations.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.databinding.FragmentDetailStoryBinding
import org.d3if00001.storyapp.presentations.viewmodels.StoryViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Formatter
import java.util.Locale

@AndroidEntryPoint
class DetailStory : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailStoryBinding
    private val storyViewModel: StoryViewModel by viewModels()

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailStoryBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = DetailStoryArgs.fromBundle(arguments as Bundle).id

        storyViewModel.getDetailStory(id)
        updateProgress()
    }
    private fun updateProgress() {
        storyViewModel.getDetailStory.observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Success->{
                    Glide.with(requireContext())
                        .load(it.data.story.photo)
                        .fitCenter()
                        .into(binding.photo)
                    binding.name.text = it.data.story.name
                    binding.descDetail.text = it.data.story.description
                    binding.createdAt.text = it.data.story.createdAt
                    binding.pgDetail.visibility = View.GONE
                }
                is ApiResponse.Loading->{
                    binding.pgDetail.visibility = View.VISIBLE
                }
                is ApiResponse.Error->{
                    binding.pgDetail.visibility = View.GONE
                }
                else->{
                    binding.pgDetail.visibility = View.GONE
                    Toast.makeText(context,"Cannot find Response",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}