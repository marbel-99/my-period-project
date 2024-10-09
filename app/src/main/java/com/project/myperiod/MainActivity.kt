package com.project.myperiod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.myperiod.ui.theme.MyPeriodTheme

class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Comprobar si el usuario está autenticado
        val currentUser: FirebaseUser? = mAuth.currentUser
        if (currentUser != null) {
            // Si el usuario ya está autenticado, hacer algo (ejemplo: redirigirlo)
            println("Usuario autenticado: ${currentUser.email}")
        } else {
            // Si no está autenticado, podrías mostrar una pantalla de inicio de sesión o registrarse
            println("No hay usuario autenticado")
        }

        // Configuración de Jetpack Compose
        enableEdgeToEdge()
        setContent {
            MyPeriodTheme {
                val navController = rememberNavController() // Recordar el controlador de navegación
                MyNavigation(navController) // Llamar a la función MyApp
            }
        }
    }
}


