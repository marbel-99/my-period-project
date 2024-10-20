package com.project.myperiod

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pages.Formulario1
import pages.Formulario2
import pages.Formulario3
import pages.Formulario4
import pages.Home
import pages.LoginScreen
import pages.RegistrationScreen
import pages.SplashScreen

@Composable
fun MyNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
        SplashScreen(navController) {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
        composable("login") {
            LoginScreen(navController, onLogin = { navController.navigate("home") })
        }
        composable("registration") {
            RegistrationScreen(navController, onRegister = { navController.navigate("formulario1") })
        }
        composable("home") { Home() } // Assuming you have a Home composable
        composable("formulario1") {
            val userId = Firebase.auth.currentUser?.uid
            var username by remember { mutableStateOf("") }

            LaunchedEffect(userId) {
                if (userId != null) {
                    val databaseRef = Firebase.database.reference.child("users").child(userId)
                    databaseRef.child("username").get().addOnSuccessListener { snapshot ->
                        username = snapshot.value as? String ?: ""
                    }
                }
            }

            Formulario1(navController, username)
        }
        composable("formulario2") { Formulario2(navController) }
        composable("formulario3") { Formulario3(navController) }
        composable("formulario4") { Formulario4(navController) }
    }
}

