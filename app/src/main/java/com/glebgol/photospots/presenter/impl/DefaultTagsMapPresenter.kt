package com.glebgol.photospots.presenter.impl

import android.content.Context
import android.util.Log
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.location.LocationClient
import com.glebgol.photospots.presenter.TagsMapPresenter
import com.glebgol.photospots.view.TagsMapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultTagsMapPresenter(private val view: TagsMapView, private val context: Context) :
    TagsMapPresenter {

    private lateinit var locationClient: LocationClient

    override fun loadMapWithTags() {
        val call = ApiClient.tagApi.getTags()

        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful && response.body() != null) {
                    view.showMapWithTags(response.body()!!)
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
                view.updateLocation(it.latitude, it.longitude)
            }
        }
    }

    override fun stopLocationUpdates() {
        locationClient.stopLocationUpdates()
    }
}
