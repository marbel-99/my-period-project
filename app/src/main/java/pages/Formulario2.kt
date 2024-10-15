package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.myperiod.R
import components.DayCarousel

@Composable
fun Formulario2(navController: NavHostController) {
    var selectedDays by remember { mutableIntStateOf(28) } // Default value


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
                    0.45f // Adjust progress value
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
                text = "¿Tu ciclo promedio de duración?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF49454F),


                )

            Spacer(modifier = Modifier.padding( 20.dp))

            // Calendar Image
            Image(
                painter = painterResource(id = R.drawable.vulva_formulary2), // Replace with your calendar image
                contentDescription = "Calendar",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)


            )

            Spacer(modifier = Modifier.padding( 20.dp))

            DayCarousel(selectedDays) { newDays ->
                selectedDays = newDays
            }

            Spacer(modifier = Modifier.padding( 20.dp))

            Button(
                onClick = {
                    // Save selectedDays to database or state
                    // ...
                    navController.navigate("formulario3")
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
fun Formulario2Preview() {
    val navController = rememberNavController()
    Formulario2(navController = navController) // Provide an empty lambda for onLogin
}