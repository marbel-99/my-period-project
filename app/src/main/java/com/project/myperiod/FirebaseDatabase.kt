package com.project.myperiod

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

  fun getLastPeriodDate(userId: String, callback: (Period?) -> Unit) {
    val periodsRef = myPeriodDatabase.child("periods").child(userId)

    periodsRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object :
      ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
          // Itera sobre los hijos (debería ser solo uno debido a limitToLast(1))
          for (childSnapshot in snapshot.children) {
            val lastRecord = childSnapshot.getValue(Period::class.java)
            callback.invoke(lastRecord)
          }
        } else {
          // No se encontraron registros
          callback.invoke(null)
        }
      }

      override fun onCancelled(error: DatabaseError) {
        println("Error al obtener el último registro: ${error.message}")
        callback.invoke(null)
      }
    })
  }

  fun fetchFrequencyDays(userId: String, callback: (Int) -> Unit) {
    myPeriodDatabase.child("frequency_period").child(userId).child("frequency_days").get()
      .addOnSuccessListener { snapshot -> callback(snapshot.getValue(Int::class.java) ?: 0) }
      .addOnFailureListener { callback(0) }
  }


  fun fetchInitialPeriodDate(userId: String, callback: (String?) -> Unit) {
    myPeriodDatabase.child("period_initial_registered").child(userId).child("date").get()
      .addOnSuccessListener { snapshot -> callback(snapshot.getValue(String::class.java)) }
      .addOnFailureListener { callback(null) }
  }


  fun fetchAveragePeriodDays(userId: String, callback: (Int) -> Unit) {
    myPeriodDatabase.child("average_period").child(userId).child("average_days").get()
      .addOnSuccessListener { snapshot -> callback(snapshot.getValue(Int::class.java) ?: 0) }
      .addOnFailureListener { callback(0) }
  }






}