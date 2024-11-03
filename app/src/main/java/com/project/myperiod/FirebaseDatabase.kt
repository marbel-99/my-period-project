package com.project.myperiod

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class FirebaseDatabase {

  private val database = Firebase.database("https://my-period-faa86-default-rtdb.europe-west1.firebasedatabase.app/")
  private val myPeriodDatabase = database.reference

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

  fun setLastPeriodEndDate(userId: String, endDate: String, callback: (Boolean) -> Unit) {
    val periodsRef = myPeriodDatabase.child("periods").child(userId)

    periodsRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
          for (childSnapshot in snapshot.children) {
            val key = childSnapshot.key
            if (key != null) {
              periodsRef.child(key).child("end_date").setValue(endDate)
                .addOnSuccessListener {
                  callback(true)
                }
                .addOnFailureListener { error ->
                  println("Error al actualizar el end_date: ${error.message}")
                  callback(false)
                }
            } else {
              println("No se pudo obtener la clave del último período")
              callback(false)
            }
          }
        } else {
          println("No se encontraron períodos para el usuario $userId")
          callback(false)
        }
      }

      override fun onCancelled(error: DatabaseError) {
        println("Error al actualizar el end_date del último período: ${error.message}")
        callback(false)
      }
    })
  }

  fun addNewPeriod(userId: String, startDate: String, callback: (Boolean) -> Unit) {
    val periodsRef = myPeriodDatabase.child("periods").child(userId)

    // Obtener la última clave numérica
    periodsRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        var newKey = 1
        if (snapshot.exists()) {
          for (childSnapshot in snapshot.children) {
            val lastKey = childSnapshot.key?.toIntOrNull()
            if (lastKey != null) {
              newKey = lastKey + 1
            }
          }
        }
        // Añadir el nuevo período
        val newPeriodRef = periodsRef.child(newKey.toString())
        val periodData = mapOf(
          "start_date" to startDate,
          "end_date" to "" // end_date vacío
        )
        newPeriodRef.setValue(periodData)
          .addOnSuccessListener {
            callback(true)
          }
          .addOnFailureListener { error ->
            println("Error al añadir el nuevo período: ${error.message}")
            callback(false)
          }
      }

      override fun onCancelled(error: DatabaseError) {
        println("Error al obtener el último período: ${error.message}")
        callback(false)
      }
    })
  }






}