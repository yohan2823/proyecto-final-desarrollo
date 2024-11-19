package com.example.fastfoodmanagement.screens

import ProductService
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ManageInventoryScreen(
    navController: NavController,
    productService: ProductService
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
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
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (productName.isNotEmpty()) {
                        coroutineScope.launch {
                            val success = productService.deleteProductByName(productName)
                            if (success) {
                                snackbarHostState.showSnackbar("Producto eliminado con éxito")
                                productName = ""
                            } else {
                                snackbarHostState.showSnackbar("Error: Producto no encontrado o no se pudo eliminar")
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Por favor ingresa el nombre del producto a eliminar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar Producto")
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
