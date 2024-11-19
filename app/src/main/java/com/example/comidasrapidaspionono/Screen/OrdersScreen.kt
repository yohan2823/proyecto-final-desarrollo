package com.example.fastfoodmanagement.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Order(
    val id: String,
    val customerEmail: String,
    val items: List<String>,
    val total: Double
)

@Composable
fun OrdersScreen(navController: NavController) {
    val orders = remember { mutableStateListOf<Order>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        try {
            val fetchedOrders = db.collection("orders")
                .get()
                .await()
                .map { document ->
                    Order(
                        id = document.id,
                        customerEmail = document.getString("customerEmail") ?: "Sin correo",
                        items = document.get("items") as? List<String> ?: emptyList(),
                        total = document.getDouble("total") ?: 0.0
                    )
                }
            orders.clear()
            orders.addAll(fetchedOrders)
            isLoading = false
        } catch (e: Exception) {
            errorMessage = "Error al cargar las órdenes: ${e.message}"
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
    } else if (orders.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No hay órdenes disponibles")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(orders) { order ->
                OrderCard(order = order)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
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
                text = "Orden ID: ${order.id}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cliente: ${order.customerEmail}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total: $${order.total}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Productos: ${order.items.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
