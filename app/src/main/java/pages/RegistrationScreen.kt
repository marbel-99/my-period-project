package pages

import android.util.Patterns
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
fun RegistrationScreen(navController: NavHostController, onRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var repeatPassword by remember { mutableStateOf("") }
    var isRepeatPasswordValid by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }
    var isEmailRegistered by remember { mutableStateOf(false) }
    if (isEmailRegistered) {
        Text("Este usuario ya está registrado", color = Color.Red, modifier = Modifier.padding(8.dp))
    }
    val onRegister = {
//        val user = auth.currentUser // Get the currently authenticated user
//        if (user != null) {
//            val userId = user.uid
//            val userData = HashMap<String, Any>()
//            userData["email"] = email
//            userData["username"] = username
            // Add other user data as needed

//            database.reference.child("users").child(userId).setValue(userData)
//                .addOnSuccessListener {
//                    // Data saved successfully
//                    navController.navigate("formulario1") // Navigate to Formulario1
//                }

//        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Nueva cuenta",
            color = Color(0xFF000000),
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        Spacer(modifier = Modifier.height(10.dp)) // Espacio entre el título y la imagen

        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.avatar_user), // Reemplaza con tu recurso de imagen
            contentDescription = "Logo de Usuario",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp)) // Espacio entre la imagen y los botones

        Spacer(modifier = Modifier.height(10.dp)) // Espacio entre los botones

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                            },
            label = { Text("Email", color = Color(0xFF65558F)) },
            leadingIcon = {
                Icon(
                    painter =  painterResource(id = R.drawable.baseline_email_24),
                    contentDescription = "Email Icon"
                )
            },
            isError = !isEmailValid, // Show error state if email is invalid
            supportingText = { // Display error message
                if (!isEmailValid) {
                    Text("Email inválido", color = Color.Red)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 0.dp, top = 16.dp),


        )


        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isUsernameValid = it.isNotBlank()
                            },
            label = { Text("Nombre de usuario", color = Color(0xFF65558F)) },
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

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = {
                repeatPassword = it
                isRepeatPasswordValid = it == password // Check if passwords match
            },
            label = { Text("Repita su contraseña", color = Color(0xFF65558F)) },
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
            isError = !isRepeatPasswordValid, // Show error state if passwords don't match
            supportingText = { // Display error message
                if (!isRepeatPasswordValid) {
                    Text("Las contraseñas no coinciden", color = Color.Red)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                // Validate email and passwords here (you already have this logic)
//                if (isEmailValid && isRepeatPasswordValid && isUsernameValid && isPasswordValid) {
//                    auth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                // Registration successful
//                                onRegister()
//                                // ...
//                            } else {
//                                // Registration failed
//                                val exception = task.exception
//                                if (exception is FirebaseAuthUserCollisionException) {
//                                    // Email is already registered
//                                    isEmailRegistered = true
//                                }
//                            }
//                        }
//                }
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
                    text = "Crear cuenta",
                    color = Color(0xFF65558F),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Ya tengo cuenta, quiero iniciar sesión",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF65558F),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { navController.navigate("login") }
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    val navController = rememberNavController()
    RegistrationScreen(navController = navController, onRegister = {}) // Provide an empty lambda for onLogin
}