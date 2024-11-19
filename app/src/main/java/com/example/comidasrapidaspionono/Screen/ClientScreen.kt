package com.example.fastfoodmanagement.screens

import ProductService
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.Model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
@Composable
fun ClientScreen(
    navController: NavController,
    productService: ProductService,
    userEmail: String
) {
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Bienvenido, $userEmail",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Indicador de carga
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }


        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    try {
                        val products = productService.getProducts()
                        if (products.isNotEmpty()) {
                            navController.navigate("ClientMenuScreen")
                        } else {
                            errorMessage = "No hay productos disponibles en este momento."
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error al cargar los productos: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Ver Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("LoginScreen") {
                    popUpTo("ClientScreen") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Cerrar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))


        errorMessage?.let { message ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                action = {
                    Button(onClick = { errorMessage = null }) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text(message)
            }
        }
    }
}
