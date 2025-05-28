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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moly.edi.R
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

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
                containerColor = Color(0xFF009688),
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
