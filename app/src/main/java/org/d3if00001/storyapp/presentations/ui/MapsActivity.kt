package org.d3if00001.storyapp.presentations.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import org.d3if00001.storyapp.R
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.databinding.ActivityMapsBinding
import org.d3if00001.storyapp.presentations.viewmodels.StoryViewModel

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val storyViewModel : StoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        storyViewModel.getMapStories()
        updateProgress()
    }
    private fun updateProgress() {
        storyViewModel.getMapStories.observe(this){
            when(it){
                is ApiResponse.Loading ->{}
                is ApiResponse.Success->{
                    it.data.listStory.map { fragmentStory->
                        // Add a marker in Sydney and move the camera
                        val positionMarker = LatLng(fragmentStory.lat!!, fragmentStory.lon!!)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(positionMarker).title(fragmentStory.description)
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(positionMarker))
                    }
                }
                is ApiResponse.Error->{Toast.makeText(this,"Gagal Catch data!",Toast.LENGTH_SHORT).show()}

                else -> {}
            }
        }
    }
}