package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.project.myperiod.R

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Bienvenido",
            color = Color(0xFF000000),
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el título y la imagen

        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.avatar_user), // Reemplaza con tu recurso de imagen
            contentDescription = "Logo de Usuario",
            modifier = Modifier.size(120.dp) // Ajusta el tamaño de la imagen según lo necesites
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio entre la imagen y los botones

        // Primer botón: Acceder
        Button(
            onClick = { onLoginClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(Color(0xFFECE6F0), shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
        ) {
            Text(
                text = "Acceder",
                color = Color(0xFFFFFFFF),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(end = 8.dp) // Espacio entre el texto y el icono
            )
            // Icono de acceso (debes tener el ícono adecuado)
            Image(
                painter = painterResource(id = R.drawable.baseline_input_24), // Reemplaza con tu recurso de ícono
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Ajusta el tamaño del ícono
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones

        // Segundo botón: Crear una cuenta
        Button(
            onClick = { onRegisterClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(Color(0xFFECE6F0), shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0))
        ) {
            Text(
                text = "Crear una cuenta",
                color = Color(0xFFFFFFFF),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(end = 8.dp) // Espacio entre el texto y el icono
            )
            // Icono de agregar usuario (debes tener el ícono adecuado)
            Image(
                painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24), // Reemplaza con tu recurso de ícono
                contentDescription = null,
                modifier = Modifier.size(24.dp) // Ajusta el tamaño del ícono
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(
        onLoginClick = { /* Acción de login */ },
        onRegisterClick = { /* Acción de registro */ }
    )
}
