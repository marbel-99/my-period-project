package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.myperiod.FirebaseAuthentication
import com.project.myperiod.FirebaseDatabase
import com.project.myperiod.R
import components.DayCarousel

@Composable
fun Formulario1(navController: NavHostController) {
    var selectedDays by remember { mutableIntStateOf(7) } // Default value
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
                progress = {
                    0.25f
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
                onClick = { navController.navigate("registration") },
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

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text =
                        "¿Tu período promedio de duración?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF49454F),


            )

            Spacer(modifier = Modifier.padding( 16.dp))


            Image(
                painter = painterResource(id = R.drawable.calendar_formulary1),
                contentDescription = "Calendar",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)


            )

            DayCarousel(selectedDays) { newDays ->
                selectedDays = newDays
            }

            Spacer(modifier = Modifier.padding( 16.dp))

            Button(
                onClick = {
                    firebaseAuthentication.getCurrentUserUid()
                        ?.let { firebaseDatabase.setAveragePeriod(it,selectedDays) }
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
fun Formulario1Preview() {
    val navController = rememberNavController()
    Formulario1(navController = navController)
}