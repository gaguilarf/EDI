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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moly.edi.R
import com.moly.edi.core.ui.theme.EDITheme
import com.moly.edi.presentation.auth.AuthState
import com.moly.edi.presentation.auth.AuthViewModel
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashPreview() {
    EDITheme {
        SplashScreenWithAuth(
            onNavigateToLogin = {},
            onNavigateToHome = {},
            authViewModel = viewModel()
        )
    }
}

@Composable
fun SplashScreenWithAuth(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    authViewModel: AuthViewModel,
    viewModel: SplashViewModel = viewModel()
) {
    val welcomeText by viewModel.welcomeText.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    // Efecto que maneja la navegación basada en el estado de autenticación
    LaunchedEffect(authState) {
        delay(3000) // Mostrar splash por 3 segundos como en el original

        when (authState) {
            is AuthState.Authenticated -> {
                onNavigateToHome()
            }
            is AuthState.Unauthenticated, is AuthState.Error -> {
                onNavigateToLogin()
            }
            is AuthState.Loading -> {
                // Esperar a que se determine el estado
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Marca de agua UNSA
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

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = welcomeText,
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.logo_edi),
                contentDescription = "Logo EDI",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(width = 300.dp, height = 200.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Mostrar el botón solo si no está autenticado o hay error
            if (authState is AuthState.Unauthenticated || authState is AuthState.Error) {
                FloatingActionButton(
                    onClick = { onNavigateToLogin() },
                    containerColor = MaterialTheme.colorScheme.primary,
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
}

// Función de compatibilidad para mantener la interfaz anterior
@Composable
fun SplashActivity(
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = viewModel()
) {
    val welcomeText by viewModel.welcomeText.collectAsState()
    val navigate by viewModel.navigateToLogin.collectAsState()

    LaunchedEffect(navigate) {
        if (navigate) {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Marca de agua UNSA
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

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = welcomeText,
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.logo_edi),
                contentDescription = "Logo EDI",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(width = 300.dp, height = 200.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            FloatingActionButton(
                onClick = { onNavigateToLogin() },
                containerColor = MaterialTheme.colorScheme.primary,
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
