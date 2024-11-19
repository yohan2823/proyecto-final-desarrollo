package com.example.comidasrapidaspionono

import ProductService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comidasrapidaspionono.ui.theme.ComidasRapidasPiononoTheme
import com.example.fastfoodmanagement.Model.User
import com.example.fastfoodmanagement.Screen.ResetPasswordScreen
import com.example.fastfoodmanagement.screens.*
import com.example.fastfoodmanagement.services.InventoryService
import com.example.fastfoodmanagement.services.UserService
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

// Centralized object for route definitions
object Routes {
    const val Login = "LoginScreen"
    const val Register = "RegisterScreen"
    const val ResetPassword = "ResetPasswordScreen"
    const val Client = "ClientScreen"
    const val ClientMenu = "ClientMenuScreen"
    const val Admin = "AdminScreen"
    const val AdminMenu = "AdminMenuScreen"
    const val InventoryList = "InventoryListScreen"
    const val EmployeesList = "EmployeesListScreen"
    const val ManageInventory = "ManageInventoryScreen"
    const val Splash = "SplashScreen"
    const val Orders = "OrdersScreen"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(this)


        val productService = ProductService()
        val userService = UserService()
        val inventoryService = InventoryService()

        setContent {
            ComidasRapidasPiononoTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Routes.Login) {


                    composable(Routes.Login) {
                        LoginScreen(
                            navController = navController,
                            userService = userService
                        )
                    }


                    composable(Routes.Register) {
                        RegisterScreen(
                            navController = navController,
                            userService = userService
                        )
                    }

                    // Reset Password Screen
                    composable(Routes.ResetPassword) {
                        ResetPasswordScreen(
                            navController = navController,
                            userService = userService
                        )
                    }

                    composable("ClientScreen") {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val userEmail = currentUser?.email ?: "Usuario"

                        ClientScreen(
                            navController = navController,
                            productService = productService,
                            userEmail = userEmail // Pass user email
                        )
                    }





                    composable(Routes.ClientMenu) {
                        ClientMenuScreen(
                            navController = navController,
                            productService = productService
                        )
                    }

                    composable(Routes.Admin) {
                        AdminScreen(
                            navController = navController,
                            onNavigateToMenu = { navController.navigate(Routes.AdminMenu) },
                            onNavigateToInventory = { navController.navigate(Routes.InventoryList) },
                            onNavigateToEmployees = { navController.navigate(Routes.EmployeesList) },
                            onNavigateToOrders = { navController.navigate(Routes.Orders) }, // Navegación a la pantalla de órdenes
                            onLogout = {
                                navController.navigate(Routes.Login) {
                                    popUpTo(Routes.Login) { inclusive = true }
                                }
                            }
                        )
                    }


                    // Inventory List Screen
                    composable(Routes.InventoryList) {
                        InventoryListScreen(
                            navController = navController,
                            inventoryService = inventoryService
                        )
                    }

                    // Employees List Screen
                    composable(Routes.EmployeesList) {
                        EmployeesListScreen(
                            navController = navController,
                            userService = userService
                        )
                    }

                    // Manage Inventory Screen
                    composable(Routes.ManageInventory) {
                        ManageInventoryScreen(
                            navController = navController,
                            productService = productService
                        )
                    }
                    composable(Routes.Orders) {
                        OrdersScreen(navController = navController)
                    }

                    // Admin Menu Screen
                    composable(Routes.AdminMenu) {
                        AdminMenuScreen(
                            navController = navController,
                            onAddProduct = { product ->
                                lifecycleScope.launch {
                                    productService.addProduct(product)
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
