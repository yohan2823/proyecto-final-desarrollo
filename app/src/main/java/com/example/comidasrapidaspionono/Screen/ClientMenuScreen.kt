package com.example.fastfoodmanagement.screens

import ProductService
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.Model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
@Composable
fun ClientMenuScreen(
    navController: NavController,
    productService: ProductService
) {
    val products = remember { mutableStateListOf<Product>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val cart = remember { mutableStateListOf<Product>() }
    var isPurchasing by remember { mutableStateOf(false) }
    var estimatedTime by remember { mutableStateOf<Int?>(null) }


    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        try {
            val fetchedProducts = db.collection("products")
                .get()
                .await()
                .map { document ->
                    Product(
                        id = document.id,
                        name = document.getString("name") ?: "Sin nombre",
                        description = document.getString("description") ?: "Sin descripción",
                        price = document.getDouble("price") ?: 0.0
                    )
                }
            products.clear()
            products.addAll(fetchedProducts)
            isLoading = false
        } catch (e: Exception) {
            errorMessage = "Error al cargar productos: ${e.message}"
            isLoading = false
        }
    }

    if (isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(errorMessage ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(products) { product ->
                    ClientMenuItemCard(
                        product = product,
                        onAddToCart = { cart.add(it) } // Agregar producto al carrito
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Button(
                onClick = {
                    isPurchasing = true
                    estimatedTime = (10..30).random() // Simulación de tiempo de entrega en minutos
                    isPurchasing = false
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = cart.isNotEmpty()
            ) {
                if (isPurchasing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Comprar (${cart.size} items)")
                }
            }


            estimatedTime?.let {
                Text(
                    text = "Tiempo estimado de entrega: $it minutos",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
@Composable
fun ClientMenuItemCard(
    product: Product,
    onAddToCart: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))


            Button(onClick = { onAddToCart(product) }) {
                Text("Agregar al Carrito")
            }
        }
    }
}
