package com.project.myperiod

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pages.Formulario1
import pages.Home
import pages.LoginScreen
import pages.RegistrationScreen
import pages.SplashScreen

@Composable
fun MyNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            Home(navController)
        }
        composable("registration") {
            RegistrationScreen( navController)
        }
        composable("formulario1") {
            Formulario1(navController)
        }
//        composable("formulario2") { Formulario2(navController) }
//        composable("formulario3") { Formulario3(navController) }
//        composable("formulario4") { Formulario4(navController) }
//    }
    }
}
