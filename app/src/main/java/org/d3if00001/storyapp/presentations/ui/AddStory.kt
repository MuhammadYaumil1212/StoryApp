package org.d3if00001.storyapp.presentations.ui

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.databinding.FragmentAddStoryBinding
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddStory : Fragment() {
    private val viewModelStory by viewModels<AddStoryViewModel>()
    private lateinit var binding:FragmentAddStoryBinding

    private val launcherIntentGallery : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedImg = it.data?.data as Uri
                selectedImg.let {uri->
                    val myFile = uriToFile(uri,requireContext())
                    myFile.let {file->
                        viewModelStory.setFile(file)
                        binding.imageCamera.setImageURI(uri)
                    }
                }
            }
        }
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val FILENAME_FORMAT = "dd-MMM-yyyy"
        val timeStamp: String = SimpleDateFormat(
            FILENAME_FORMAT,
            Locale.US
        ).format(System.currentTimeMillis())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onRequestPermissionsResult(requestCode, permissions, grantResults)",
        "androidx.fragment.app.Fragment"
    )
    )
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(!allPermissionsGranted()){
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageCamera.setOnClickListener {
            if(!allPermissionsGranted()){
                ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS)
            }else{
                startGallery()
            }
        }
        binding.submitButton.setOnClickListener {
            viewModelStory.uploadImage(description = binding.descriptionInput.text.toString())
            updateProgress()
        }
    }
    private fun updateProgress() {
        viewModelStory.uploadImageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Success -> {
                    binding.pgInputStory.visibility = View.GONE
                    view?.findNavController()
                        ?.navigate(AddStoryDirections.actionAddStoryToHomeFragment())
                }

                is ApiResponse.Error -> {
                    binding.pgInputStory.visibility = View.GONE
                    Toast.makeText(context, "Error : ${it.errorMessage}", Toast.LENGTH_SHORT).show()
                }

                is ApiResponse.Loading -> {
                    binding.pgInputStory.visibility = View.VISIBLE
                }

                is ApiResponse.Empty->{
                    binding.pgInputStory.visibility = View.GONE
                    view?.findNavController()
                        ?.navigate(AddStoryDirections.actionAddStoryToHomeFragment())
                }

                else -> {
                    Toast.makeText(context, "Error Response dont available", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent,"Choose a picture")
        launcherIntentGallery.launch(chooser)
    }
}