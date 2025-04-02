package com.example.gpstracker

data class LocationEntity(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val accuracy: Float,
    val bearing: Float,
    val speed: Float,
    val timestamp: String?
)