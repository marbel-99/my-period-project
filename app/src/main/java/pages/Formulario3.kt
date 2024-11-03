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
import com.project.myperiod.FirebaseAuthentication
import com.project.myperiod.FirebaseDatabase
import components.MonthCarousel
import java.time.LocalDate

@Composable
fun Formulario3(navController: NavHostController) {
    var selectedMonth by remember { mutableStateOf(LocalDate.now().monthValue) } // Default to actual month
    var selectedYear by remember { mutableStateOf(LocalDate.now().year) } // Default to actual year
    var selectedDay by remember { mutableStateOf(LocalDate.now()) } // Default to today
    val firebaseDatabase = FirebaseDatabase()
    val firebaseAuthentication = FirebaseAuthentication()

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
                progress = 0.65f,
                modifier = Modifier
                    .width(280.dp)
                    .height(8.dp)
                    .align(Alignment.Center),
                color = Color(0xFF65558F),
                trackColor = Color(0xFFE8DEF8),
            )
            // Back Icon
            IconButton(
                onClick = { navController.navigate("Formulario2") },
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
            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "¿Cuándo terminó tu último período?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF49454F),
            )

            Spacer(modifier = Modifier.padding(36.dp))

            MonthCarousel(
                selectedMonth = selectedMonth,
                selectedYear = selectedYear,
                onMonthSelected = { month, year ->
                    selectedMonth = month
                    selectedYear = year
                },
                onDaySelected = { date ->
                    selectedDay = date
                }
            )

            Spacer(modifier = Modifier.padding(70.dp))

            Button(
                onClick = {
                    firebaseAuthentication.getCurrentUserUid()
                        ?.let { firebaseDatabase.setInitialPeriod(it,"$selectedDay" ) }
                    navController.navigate("formulario4")
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
