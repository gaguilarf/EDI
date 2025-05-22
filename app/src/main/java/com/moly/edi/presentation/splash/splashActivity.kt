// splashActivity.kt
package com.moly.edi.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moly.edi.R
import kotlinx.coroutines.delay

@Composable
fun splashActivity(onNavigateToLogin: () -> Unit) {
    // Efecto para navegar automáticamente después de un tiempo
    LaunchedEffect(key1 = true) {
        delay(3000) // 3 segundos de retraso
        onNavigateToLogin()
    }

    // Box principal con imagen de fondo
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo que ocupa toda la pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Texto "UNSA" como marca de agua
        Text(
            text = "UNSA",
            color = Color.White,
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(0.2f)
        )

        // Contenido principal en columna centrada
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto de Bienvenida
            Text(
                text = "BIENVENIDO",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Imagen del logo EDI
            Image(
                painter = painterResource(id = R.drawable.logo_edi),
                contentDescription = "Logo EDI",
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(16.dp)
            )

            // Espacio antes del botón
            Spacer(modifier = Modifier.height(40.dp))

            // Botón circular turquesa con flecha
            FloatingActionButton(
                onClick = { onNavigateToLogin() },
                containerColor = Color(0xFF009688), // Color turquesa
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Avanzar",
                    tint = Color.White
                )
            }
        }
    }
}