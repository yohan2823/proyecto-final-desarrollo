package com.example.fastfoodmanagement.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.comidasrapidaspionono.R
import kotlinx.coroutines.delay

@Composable
fun PcargaScreen(
    navController: NavController
) {
    var alphaValue by remember { mutableStateOf(0f) }

    // Animación de fade-in
    val alphaAnimation = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(Unit) {
        delay(4000) // Retraso de 4 segundos
        navController.navigate("LoginScreen") {
            popUpTo("PcargaScreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.hamburguesa_icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .alpha(alphaAnimation.value)
        )
    }
}
