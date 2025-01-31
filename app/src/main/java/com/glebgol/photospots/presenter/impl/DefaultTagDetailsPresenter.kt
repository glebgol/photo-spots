package com.glebgol.photospots.presenter.impl

import android.util.Log
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.presenter.TagDetailsPresenter
import com.glebgol.photospots.view.TagDetailsView
import org.osmdroid.views.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultTagDetailsPresenter(private val view: TagDetailsView) : TagDetailsPresenter {

    override fun loadTagDetails(marker: Marker, tag: Tag) {
        val call = ApiClient.tagApi.getTagById(tag.id)
        call.enqueue(object : Callback<TagDetails> {
            override fun onResponse(call: Call<TagDetails>, response: Response<TagDetails>) {
                if (response.isSuccessful && response.body() != null) {
                    val tagDetails = response.body()!!
                    view.showTagDetails(marker, tagDetails)
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
