package com.example.fastfoodmanagement.screens


import ProductService
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.Model.Product

@Composable
fun AdminMenuScreen(
    navController: NavController,
    onAddProduct: (Product) -> Unit
) {
    val productName = remember { mutableStateOf("") }
    val productPrice = remember { mutableStateOf("") }
    val productDescription = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = productPrice.value,
            onValueChange = { productPrice.value = it },
            label = { Text("Precio del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = productDescription.value,
            onValueChange = { productDescription.value = it },
            label = { Text("Descripción del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val product = Product(
                    name = productName.value,
                    price = productPrice.value.toDoubleOrNull() ?: 0.0,
                    description = productDescription.value
                )
                onAddProduct(product)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Producto")
        }
    }
}
