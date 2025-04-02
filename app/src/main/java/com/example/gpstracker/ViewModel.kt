package com.example.gpstracker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationEntity?>(null)
    val location: State<LocationEntity?> = _location

    fun updateLocation(newLocation: LocationEntity){
        _location.value = newLocation
    }

}