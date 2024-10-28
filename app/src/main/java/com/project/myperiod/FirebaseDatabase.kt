package com.project.myperiod

import com.google.firebase.Firebase
import com.google.firebase.database.database


class FirebaseDatabase {

  private val database = Firebase.database("https://my-period-faa86-default-rtdb.europe-west1.firebasedatabase.app/")
  private val citiesDatabase = database.reference
  private val myPeriodDatabase = database.reference


  fun getCities(callback: (response: List<City>) -> Unit) {
    citiesDatabase.child("cities").get().addOnSuccessListener { response ->
      val cities = response.children.mapNotNull { it.getValue(City::class.java) }
      callback.invoke(cities)
    }
  }

  fun setAveragePeriod(userId: String, averageDays: Int) {
    myPeriodDatabase.child("average_period").child(userId).child("average_days").setValue(averageDays)
  }

  fun setFrequencyPeriod(userId: String, frequencyDays: Int) {
    myPeriodDatabase.child("frequency_period").child(userId).child("frequency_days").setValue(frequencyDays)
  }

  fun setInitialPeriod(userId: String, date: String) {
    myPeriodDatabase.child("period_initial_registered").child(userId).child("date").setValue(date)
  }


}