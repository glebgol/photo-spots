package com.glebgol.photospots.presenter

import android.content.Context
import android.util.Log
import com.glebgol.photospots.MainView
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.location.LocationClient
import org.osmdroid.views.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultMainPresenter(private val mainView: MainView, private val context: Context)
    : MainPresenter {

    private lateinit var locationClient: LocationClient

    override fun loadMapWithTags() {
        val call = ApiClient.tagApi.getTags()

        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful && response.body() != null) {
                    mainView.showMapWithTags(response.body()!!)
                } else {
                    Log.w(
                        "Error",
                        "Error while getting tags: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<Tag>>, t: Throwable) {
                Log.e("Failure when getting tags", "Failure ${t.message}")
            }
        })
    }

    override fun updateLocation() {
        locationClient = LocationClient(context)
        locationClient.startLocationUpdates()
        locationClient.getLastKnownLocation { location ->
            location?.let {
                mainView.updateLocation(it.latitude, it.longitude)
            }
        }
    }

    override fun stop() {
        locationClient.stopLocationUpdates()
    }

    override fun loadTagDetails(marker: Marker, tag: Tag) {
        val call = ApiClient.tagApi.getTagById(tag.id)
        call.enqueue(object : Callback<TagDetails> {
            override fun onResponse(call: Call<TagDetails>, response: Response<TagDetails>) {
                if (response.isSuccessful && response.body() != null) {
                    val tagDetails = response.body()!!
                    mainView.showTagDetails(marker, tagDetails)
                } else {
                    Log.w(
                        "Error",
                        "Error while getting tag details: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<TagDetails>, t: Throwable) {
                Log.e("Failure when getting tags", "Failure ${t.message}")
            }
        })
    }
}
