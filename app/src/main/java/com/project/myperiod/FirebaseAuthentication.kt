package com.project.myperiod

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.database
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {
  val auth: FirebaseAuth = Firebase.auth
    val database = com.google.firebase.Firebase.database("https://my-period-faa86-default-rtdb.europe-west1.firebasedatabase.app/")

  fun loginWithEmailAndPassword(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
  ) =
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          onSuccess()
        } else {
          // If sign in fails, display a message to the user.
          onFailure(task.exception ?: Exception("Authentication failed."))
        }
      }

    fun registerUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) = auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            if (user != null) {
                // Get a reference to the user's data location in the database

                val userRef = database.getReference("users").child(user.uid)

                // Create a data object to store the user's information
                val userData = mapOf(
                    "email" to email,
                    // Add other user data fields as needed
                )

                // Add the user's data to the database
                userRef.setValue(userData)
                    .addOnSuccessListener {
                        // Data added successfully
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        // Handle database write failure
                        onFailure(exception)
                    }
            } else {
                onFailure(Exception("User is null after registration."))
            }
        } else {
            // If sign in fails, display a message to the user.
            onFailure(task.exception ?: Exception("Authentication failed."))
        }
    }



  fun logout() {
    auth.signOut()
  }

  val isUserLoggedIn: Boolean
    get() = auth.currentUser != null
}