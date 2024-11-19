package com.example.fastfoodmanagement.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun AdminScreen(
    navController: NavController,
    onNavigateToMenu: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToEmployees: () -> Unit,
    onNavigateToOrders: () -> Unit, // Nueva función de navegación
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onNavigateToMenu,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Menú")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNavigateToInventory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Inventario")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNavigateToEmployees,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Empleados")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nuevo botón para ver órdenes
        Button(
            onClick = onNavigateToOrders,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Órdenes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}
