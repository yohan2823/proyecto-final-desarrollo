package com.example.fastfoodmanagement.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.services.UserService
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun LoginScreen(navController: NavController, userService: UserService) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginError = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.value.isNotBlank() && password.value.isNotBlank()) {

                    if (email.value == "yohanlopez345@gmail.com" && password.value == "12345678") {
                        navController.navigate("AdminScreen")
                    } else {
                        navController.navigate("ClientScreen")
                    }
                } else {
                    loginError.value = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (loginError.value) {
            Text(
                text = "Por favor, ingresa tu correo y contraseña.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(
            onClick = { navController.navigate("RegisterScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        TextButton(
            onClick = { navController.navigate("ResetPasswordScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}
