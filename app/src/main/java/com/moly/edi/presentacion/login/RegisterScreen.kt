@file:OptIn(ExperimentalMaterial3Api::class)
package com.moly.edi.presentacion.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moly.edi.CORE.ui.theme.turquesa
import com.moly.edi.R

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Título
        Text(
            text = "Regístrate",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Campo Nombre
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = viewModel::updateNombre,
            label = { Text("Nombre", color = Color.Gray) },
            placeholder = { Text("Nombre", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Email
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email*", color = Color.Gray) },
            placeholder = { Text("Email", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = uiState.emailError != null
        )

        if (uiState.emailError != null) {
            Text(
                text = uiState.emailError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = uiState.contrasena,
            onValueChange = viewModel::updateContrasena,
            label = { Text("Contraseña*", color = Color.Gray) },
            placeholder = { Text("Contraseña", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = Color.Gray
                    )
                }
            },
            singleLine = true,
            isError = uiState.contrasenaError != null
        )

        if (uiState.contrasenaError != null) {
            Text(
                text = uiState.contrasenaError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo LinkedIn
        OutlinedTextField(
            value = uiState.linkedin,
            onValueChange = viewModel::updateLinkedin,
            label = { Text("LinkedIn", color = Color.Gray) },
            placeholder = { Text("https://www.linkedin.com/in/tu-linkedin/", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo GitHub
        OutlinedTextField(
            value = uiState.github,
            onValueChange = viewModel::updateGithub,
            label = { Text("GitHub", color = Color.Gray) },
            placeholder = { Text("https://github.com/tu-github", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila con Número Celular e Instagram
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo Número Celular
            OutlinedTextField(
                value = uiState.numeroCelular,
                onValueChange = viewModel::updateNumeroCelular,
                label = { Text("Número Celular*", color = Color.Gray) },
                placeholder = { Text("999999999", color = Color.Gray) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                isError = uiState.numeroCelularError != null
            )

            // Campo Instagram
            OutlinedTextField(
                value = uiState.instagram,
                onValueChange = viewModel::updateInstagram,
                label = { Text("Instagram", color = Color.Gray) },
                placeholder = { Text("@hola_1", color = Color.Gray) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                singleLine = true
            )
        }

        if (uiState.numeroCelularError != null) {
            Text(
                text = uiState.numeroCelularError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Crear cuenta
        Button(
            onClick = { viewModel.crearCuenta() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = turquesa
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = "Crear cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(120.dp, 60.dp)
                .background(
                    turquesa,
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_edi),
                contentDescription = "Logo EPI",
                modifier = Modifier
                    .size(120.dp, 60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    // Mostrar mensajes de éxito o error
    uiState.successMessage?.let { message ->
        LaunchedEffect(message) {
            // Aquí podrías mostrar un Snackbar o Toast
            // Por ahora solo limpiamos el mensaje después de un tiempo
            kotlinx.coroutines.delay(3000)
            viewModel.clearMessages()
        }
    }

    uiState.errorMessage?.let { message ->
        LaunchedEffect(message) {
            // Aquí podrías mostrar un Snackbar o Toast
            kotlinx.coroutines.delay(3000)
            viewModel.clearMessages()
        }
    }
}