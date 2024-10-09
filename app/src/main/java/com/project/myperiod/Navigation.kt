package com.project.myperiod

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pages.LoginScreen
import pages.RegistrationScreen
import pages.SplashScreen
import pages.WelcomeScreen

@Composable
fun MyNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") {
            WelcomeScreen(

                onLoginClick = { navController.navigate("login") }, // Navegar a la pantalla de Login
                onRegisterClick = { navController.navigate("registration") } // Navegar a la pantalla de Registro
            )
        }
//        composable("registration") { RegistrationScreen(navController) }
//        composable("login") { LoginScreen(navController) }
    }
}
