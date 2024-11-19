package com.example.fastfoodmanagement.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.Model.User

import com.example.fastfoodmanagement.services.UserService
import kotlinx.coroutines.launch

@Composable
fun ManageEmployeesScreen(
    navController: NavController,
    userService: UserService
) {
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
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
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Rol del Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && role.isNotEmpty()) {
                        coroutineScope.launch {
                            val success = userService.addUser(User(email = email, role = role))
                            if (success) {
                                snackbarHostState.showSnackbar("Usuario agregado con éxito")
                            } else {
                                snackbarHostState.showSnackbar("Error al agregar usuario")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Usuario")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        coroutineScope.launch {
                            val success = userService.deleteUser(email)
                            if (success) {
                                snackbarHostState.showSnackbar("Usuario eliminado con éxito")
                            } else {
                                snackbarHostState.showSnackbar("Error al eliminar usuario")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar Usuario")
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
