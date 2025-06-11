package com.moly.edi.presentation.configuracion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(
    viewModelFactory: ConfiguracionViewModelFactory,
    correoElectronico: String
) {
    val viewModel: ConfiguracionViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()

    // Cargar configuración cuando se crea el composable
    LaunchedEffect(correoElectronico) {
        viewModel.loadConfiguracion(correoElectronico)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título
        Text(
            text = "CONFIGURACIÓN",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Mostrar loading
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Cyan)
            }
            return@Column
        }

        // Mostrar error
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Notificaciones
        SwitchRow(
            label = "Notificaciones",
            checked = uiState.notificacionesEnabled,
            onCheckedChange = { viewModel.toggleNotificaciones() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sobre noticias
        Text(
            text = "Sobre noticias",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Categorías de interés
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categorías de interés",
                color = Color.Gray,
                fontSize = 14.sp
            )
            IconButton(
                onClick = { /* Abrir diálogo para agregar categoría */ }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar categoría",
                    tint = Color.Cyan
                )
            }
        }

        // Chips de categorías
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(uiState.categoriesIntereses) { categoria ->
                AssistChip(
                    onClick = { /* Manejar click en categoría */ },
                    label = {
                        Text(
                            text = categoria,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.Gray.copy(alpha = 0.3f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sobre perfil
        Text(
            text = "Sobre perfil",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Visibilidad
        SwitchRow(
            label = "Visibilidad",
            checked = uiState.visibilidadEnabled,
            onCheckedChange = { viewModel.toggleVisibilidad() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Disponibilidad para proyectos
        SwitchRow(
            label = "Disponibilidad para proy.",
            checked = uiState.disponibilidadProyEnabled,
            onCheckedChange = { viewModel.toggleDisponibilidadProy() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Opciones con flecha
        ArrowOption(
            label = "Soporte y ayuda",
            onClick = { /* Navegar a soporte */ }
        )

        ArrowOption(
            label = "Acerca de",
            onClick = { /* Navegar a acerca de */ }
        )

        ArrowOption(
            label = "Cambiar contraseña",
            onClick = { /* Navegar a cambiar contraseña */ }
        )

        ArrowOption(
            label = "Cerrar sesión",
            onClick = { /* Cerrar sesión */ }
        )
    }
}

@Composable
fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Cyan,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun ArrowOption(
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp
        )
        IconButton(onClick = onClick) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir a $label",
                tint = Color.Cyan
            )
        }
    }
}