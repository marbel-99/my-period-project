package com.project.myperiod

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {
  val auth: FirebaseAuth = Firebase.auth

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
    ) = auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
        if (task.isSuccessful) {

            onSuccess()

        } else {
            onFailure(task.exception ?: Exception("Registration failed."))
        }
    }

    fun getCurrentUserUid(): String? {
        val user = Firebase.auth.currentUser
        return user?.uid
    }

    fun logout() {
        auth.signOut()
     }

  val isUserLoggedIn: Boolean
    get() = auth.currentUser != null
}