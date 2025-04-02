package com.example.gpstracker

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirebaseUtils (locationEntity: LocationEntity){
    val db = Firebase.firestore
    private val locationDataSet = createLocationDataSet(locationEntity)

    private fun createLocationDataSet(location: LocationEntity): Map<String, Any> {
        return mapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "altitude" to location.altitude,
            "accuracy" to location.accuracy,
            "bearing" to location.bearing,
            "speed" to location.speed,
            "timestamp" to location.timestamp,
        ) as Map<String, Any>
    }
    fun uploadLocationData() {
        db.collection("location") // Replace "locations" with your collection name
            .add(locationDataSet)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
}