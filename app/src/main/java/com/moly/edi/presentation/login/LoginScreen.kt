package com.moly.edi.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moly.edi.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.moly.edi.presentation.login.LoginViewModel

@Composable
fun LoginScreenWithAuth(
    onLoginSuccess: (email: String, name: String) -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            // Extraer el nombre del email para la persistencia
            val userName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            onLoginSuccess(email, userName)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 50.dp)
            .clickable(onClick = { /* unfocus inputs */ }),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_edi),
            contentDescription = "Logo",
            modifier = Modifier
                .width(313.dp)
                .height(94.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(30.dp))
        Text("Iniciar Sesión", fontSize = 40.sp, fontWeight = FontWeight.SemiBold)
        Text("¿Eres nuevo aquí? Regístrate", color = Color(0xFF0F8B8D))
        Spacer(Modifier.height(30.dp))

        Text("Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", color = Color(0xFF0F8B8D)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Email Icon", tint = Color(0xFF0F8B8D))
            }
        )
        Spacer(Modifier.height(10.dp))
        Text("Contraseña")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña", color = Color(0xFF0F8B8D)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Lock Icon", tint = Color(0xFF0F8B8D))
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = Color(0xFF0F8B8D)
                    )
                }
            }
        )
        Spacer(Modifier.height(10.dp))
        Text("¿Olvidaste tu contraseña?", color = Color(0xFF0F8B8D))
        Spacer(Modifier.height(30.dp))
        if (errorMessage != null) {
            Text(errorMessage ?: "", color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            Log.e("LoginScreen", "Error de login: $errorMessage")
        }
        Button(
            onClick = {
                Log.d("LoginScreen", "Intentando login con email: '$email' y password: '$password'")
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Iniciar Sesión", color = Color.White, fontSize = 30.sp)
            }
        }
    }
}
