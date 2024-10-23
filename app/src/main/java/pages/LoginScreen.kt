package pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.myperiod.R


@Composable
fun LoginScreen(navController: NavHostController, onLogin: () -> Unit)  {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }
    val isUserNotFound by remember { mutableStateOf(false) }
//    var showCityPopup by remember { mutableStateOf(false) }
//    var city by remember { mutableStateOf<City?>(null) }
//    val supabaseDatabase = SupabaseData()
//    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Iniciar Sesión",
            color = Color(0xFF000000),
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el título y la imagen

        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.avatar_user), // Reemplaza con tu recurso de imagen
            contentDescription = "Logo de Usuario",
            modifier = Modifier
                .size(190.dp)
                .padding(bottom = 50.dp)
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio entre la imagen y los botones



        Spacer(modifier = Modifier.height(10.dp)) // Espacio entre los botones


        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isUsernameValid = it.isNotBlank()
                            },
            label = { Text("Nombre de usuario o email", color = Color(0xFF65558F)) },
            leadingIcon = {
                Icon(
                    painter =  painterResource(id = R.drawable.baseline_person_3_24),
                    contentDescription = "Person Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordValid = it.isNotBlank()
                            },
            label = { Text("Contraseña",  color = Color(0xFF65558F) ) },
            leadingIcon = {
                Icon(
                    painter =  painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = "Lock Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter =  if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_off_24) else painterResource(id = R.drawable.baseline_remove_red_eye_24),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
//                coroutineScope.launch {
//                    try {
//                        city = supabaseDatabase.getCityNames()
//                        showCityPopup = true
//                    } catch (e: Exception) {
//                        // Handle error, e.g., log it or display an error message
//                        Log.e("LoginScreen", "Error fetching city names", e)
//                    }
//                }




// Process the cityNames list
//                auth.signInWithEmailAndPassword(username, password)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            // Sign-in successful
//                            onLogin()
//                            navController.navigate("home") // Navigate to home screen
//                        } else {
//                            val exception = task.exception
//                            if (exception is FirebaseAuthInvalidUserException) {
//                                isUserNotFound = true
//                            } else {
//                                // Other authentication error
//                                // Handle the error accordingly
//                            }
//                        }
//                    }
            },
            modifier = Modifier
                .width(160.dp)
                .padding(horizontal = 12.dp, vertical = 18.dp)
                .background(Color(0xFFECE6F0), shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECE6F0)),
            contentPadding = PaddingValues(0.dp),

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Acceder",
                    color = Color(0xFF65558F),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 24.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No tengo cuenta, quiero registrarme",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF65558F),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { navController.navigate("registration") }
                .padding(8.dp)
        )
    }
    if (isUserNotFound) {
        Text(
            "Este usuario no está registrado",
            color = Color.Red,
            modifier = Modifier.padding(8.dp)
        )
    }


//    if (showCityPopup && city != null) { // Check if city is not null
//        AlertDialog(
//            onDismissRequest = { showCityPopup = false },
//            title = { Text("City Names") },
//            text = {
//                Text("City ID: ${city?.id}, City Name: ${city?.name}") // Display city data
//            },
//            confirmButton = {
//                Button(onClick = { showCityPopup = false }) {
//                    Text("OK")
//                }
//            }
//        )
//    }



}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController , onLogin = {}) // Provide an empty lambda for onLogin)
}