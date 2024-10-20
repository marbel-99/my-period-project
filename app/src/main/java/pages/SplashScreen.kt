package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.myperiod.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, param: (Any) -> Unit) {
    // Importa las fuentes personalizadas
    val arbutusSlab = FontFamily(Font(R.font.arbutus_slab_regular))
    val aoboshiOne = FontFamily(Font(R.font.aoboshi_one_regular))

    // Pantalla principal con fondo blanco
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            // Imagen en la parte superior
            Image(
                painter = painterResource(id = R.drawable.splash_image), // Usa el ID correcto de tu imagen
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Ajusta la altura según sea necesario
            )

            // Texto principal "My Period"
            BasicText(
                text = "My Period",
                style = TextStyle(
                    fontFamily = arbutusSlab,
                    fontWeight = FontWeight.Normal,
                    fontSize = 48.sp,
                    color = Color(0xFFAE6BA4) // Color: AE6BA4
                ),
                modifier = Modifier.padding(top = 250.dp)
            )

            // Subtítulo "Haz de tu autocuidado una prioridad"
            BasicText(
                text = "Haz de tu autocuidado una prioridad",
                style = TextStyle(
                    fontFamily = aoboshiOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = Color(0xFF2B2B2B) // Color: 2B2B2B
                ),
                modifier = Modifier.padding(bottom = 20.dp, top = 80.dp)
            )
        }
    }

    LaunchedEffect(key1 = true) {
        delay(2000) // Delay for 2 seconds
        navController.navigate("login") {
            popUpTo(route = "splash") { inclusive = true } // Remove splash screen from back stack
        }
    }
}