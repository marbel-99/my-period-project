package pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
fun RegistrationScreen(onRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Correo electrónico") })
        TextField(value = username, onValueChange = { username = it }, label = { Text("Nombre de usuario") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation())
        TextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Repetir contraseña") }, visualTransformation = PasswordVisualTransformation())
        Button(onClick = {
            // Lógica para registrar al usuario con Firebase
            // Llama a onRegister() después de registrar
        }) {
            Text("Crear cuenta")
        }
    }
}
