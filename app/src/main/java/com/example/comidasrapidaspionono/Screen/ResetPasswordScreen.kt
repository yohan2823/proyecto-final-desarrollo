package com.example.fastfoodmanagement.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.services.UserService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    userService: UserService,
    auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    var email by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Restablecer Contraseña",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        coroutineScope.launch {
                            try {
                                sendPasswordResetEmail(auth, email)
                                snackbarHostState.showSnackbar("Correo de restablecimiento enviado")
                                email = ""
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Por favor, ingresa tu correo")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar Correo de Restablecimiento")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Regresar")
            }
        }
    }
}

suspend fun sendPasswordResetEmail(auth: FirebaseAuth, email: String) {
    auth.sendPasswordResetEmail(email)
}
