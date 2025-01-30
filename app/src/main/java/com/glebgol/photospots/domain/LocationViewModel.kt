package com.glebgol.photospots.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationResult

class LocationViewModel : ViewModel() {

    private val liveData: LiveData<LocationResult> = MutableLiveData()

}