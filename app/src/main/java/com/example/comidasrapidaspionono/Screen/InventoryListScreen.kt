package com.example.fastfoodmanagement.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.services.InventoryService

@Composable
fun InventoryListScreen(
    navController: NavController,
    inventoryService: InventoryService
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista de Inventario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("ManageInventoryScreen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar al Inventario")
        }
    }
}
