package com.project.myperiod

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FirebaseDatabase {

  val database = Firebase.database("https://my-period-faa86-default-rtdb.europe-west1.firebasedatabase.app/")
  val citiesDatabase = database.reference

  fun getCities(callback: (response: List<City>) -> Unit) {
    citiesDatabase.child("cities").get().addOnSuccessListener { response ->
      val cities = response.children.mapNotNull { it.getValue(City::class.java) }
      callback.invoke(cities)
    }
  }

}