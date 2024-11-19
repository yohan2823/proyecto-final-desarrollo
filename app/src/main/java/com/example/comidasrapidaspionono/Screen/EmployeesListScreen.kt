package com.example.fastfoodmanagement.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastfoodmanagement.services.UserService

@Composable
fun EmployeesListScreen(
    navController: NavController,
    userService: UserService
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista de Empleados",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("ManageEmployeesScreen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Empleado")
        }
    }
}
