package com.example.dicodingstoryapp1

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstoryapp1.api.ApiConfig
import com.example.dicodingstoryapp1.api.ListStoryItem
import com.example.dicodingstoryapp1.api.StoriesResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.dicodingstoryapp1.databinding.ActivityStoryMapsBinding
import com.google.android.gms.maps.model.MapStyleOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var storyMapsViewModel: SharedViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var activityStoryMapsBinding: ActivityStoryMapsBinding

    private val _listStoriesLocation = MutableLiveData<List<ListStoryItem>>()
    private val listStoriesLocation: LiveData<List<ListStoryItem>> = _listStoriesLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityStoryMapsBinding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(activityStoryMapsBinding.root)

        title = getString(R.string.app_name)

        setupViewModel()
        getStoriesLocation()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getStoriesLocation() {
        storyMapsViewModel.getUser().observe(this) {
            if(it != null) {
                val client = ApiConfig.getApiService().getStoriesWithLocation("Bearer " + it.token, 1)
                client.enqueue(object : Callback<StoriesResponse> {
                    override fun onResponse(
                        call: Call<StoriesResponse>,
                        response: Response<StoriesResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse1: $responseBody")
                        if(response.isSuccessful && responseBody?.message == "Stories fetched successfully") {
                            _listStoriesLocation.value = responseBody.listStory
                        }
                    }

                    override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure2: ${t.message}")
                    }

                })
            }
        }
    }

    private fun setupViewModel() {
        storyMapsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        val jakarta = LatLng(-6.23, 106.76)

        Log.d(TAG, "onMapReady1: ${_listStoriesLocation.value}")
        listStoriesLocation.observe(this) {
            Log.d(TAG, "onMapReady2: ${listStoriesLocation.value}")
            Log.d(TAG, "onMapReady3: ${listStoriesLocation.value?.indices}")
            for(i in listStoriesLocation.value?.indices!!) {
                val location = LatLng(listStoriesLocation.value?.get(i)?.lat!!, listStoriesLocation.value?.get(i)?.lon!!)
                mMap.addMarker(MarkerOptions().position(location).title(getString(R.string.story_uploaded_by) + listStoriesLocation.value?.get(i)?.name))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 2f))
            }
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    companion object {
        const val TAG = "StoryMapsActivity"
    }

}