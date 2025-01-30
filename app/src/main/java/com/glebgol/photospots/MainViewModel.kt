package com.glebgol.photospots

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Mark
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.usecases.GetMapWithGeoMarksUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val getMapWithGeoMarksUseCase = GetMapWithGeoMarksUseCase()

    var geoTags = MutableLiveData<List<Tag>>()
        private set
    var isLoading = MutableLiveData(false)
        private set

    init {

        val call = ApiClient.tagApi.getTags()

        isLoading.value = false
        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>, response: Response<List<Tag>>) {
                if (response.isSuccessful && response.body() != null) {
                    geoTags.value = response.body()
                } else {
                    Log.w(
                        "Error",
                        "Error while getting tags: ${response.code()} - ${response.message()}"
                    )
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<Tag>>, t: Throwable) {
                Log.e("Failure when getting tags", "Failure ${t.message}")
                isLoading.value = false
            }
        })
    }
}
