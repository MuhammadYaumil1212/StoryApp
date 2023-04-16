package org.d3if00001.storyapp.presentations.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.databinding.FragmentDetailStoryBinding

class DetailStory : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentDetailStoryBinding
    companion object {
        const val TAG = "ModalBottomSheet"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailStoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleStory = arguments?.getString("title_story")
        binding.tvTitleStory.text = titleStory
    }
}