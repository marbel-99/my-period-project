package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Formulario3(navController: NavHostController) {
    var selectedMonth by remember { mutableStateOf(10) } // Default to October
    var selectedYear by remember { mutableStateOf(2024) } // Default to 2024
    var selectedDay by remember { mutableStateOf(0L) }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        // Progress Bar and Back Navigation
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)) {
            // Progress Bar
            LinearProgressIndicator(
                progress = {
                    0.65f // Adjust progress value
                },
                modifier = Modifier
                    .width(280.dp)
                    .height(8.dp)
                    .align(Alignment.Center),
                color = Color(0xFF65558F),
                trackColor = Color(0xFFE8DEF8),
            )
            // Back Icon
            IconButton(
                onClick = { navController.navigate("welcome") },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFAE6BA4)
                )
            }
        }

        // Content with Background
        Column(modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFFF4F4F4))
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Title
            Text(
                text = "¿Cuándo comenzó tu último período?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF49454F),


                )

            Spacer(modifier = Modifier.padding( 16.dp))





            Spacer(modifier = Modifier.padding( 16.dp))

            Button(
                onClick = {
                    // Save selectedDays to database or state
                    // ...
                    navController.navigate("formulario2")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
            ) {
                Text("Siguiente paso", style = MaterialTheme.typography.labelLarge, color = Color(0xFF65558F))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Formulario3Preview() {
    val navController = rememberNavController()
    Formulario3(navController = navController) // Provide an empty lambda for onLogin
}