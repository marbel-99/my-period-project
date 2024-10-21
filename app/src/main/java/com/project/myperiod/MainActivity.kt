package com.project.myperiod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.myperiod.ui.theme.MyPeriodTheme
import components.MonthCarousel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://ydlptuukgkqmlfhywmse.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlkbHB0dXVrZ2txbWxmaHl3bXNlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjk1MzAyOTcsImV4cCI6MjA0NTEwNjI5N30.T-phORm3N8G-tEBMMS4bXDFf1YYrZCn9r0Ai68Gv5hA"
) {
    install(Postgrest)
}

class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Comprobar si el usuario está autenticado
        val currentUser: FirebaseUser? = mAuth.currentUser
        if (currentUser != null) {
            // Si el usuario ya está autenticado, hacer algo
            println("Usuario autenticado: ${currentUser.email}")
        } else {
            // Si no está autenticado, podrías mostrar una pantalla de inicio de sesión o registrarse
            println("No hay usuario autenticado")
        }

        // Configuración de Jetpack Compose
        enableEdgeToEdge()
        setContent {
            MyPeriodTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainContent() // Llamar a la función que contiene el contenido principal
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController() // Recordar el controlador de navegación

    // Aquí puedes llamar a tu función de navegación o a MonthCarousel directamente
    MonthCarousel(
        selectedMonth = 10, // Por ejemplo, octubre
        selectedYear = 2023,
        onMonthSelected = { month, year ->
            println("Mes seleccionado: $month, Año seleccionado: $year")
        },
        onDaySelected = { day ->
            println("Día seleccionado: $day")
        }
    )
}
