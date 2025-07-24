package com.moly.edi.presentation.configuracion

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moly.edi.presentation.configuracion.ConfiguracionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(
    correoElectronico: String,
    onNavigateToSoporte: () -> Unit = {},
    onNavigateToAcercaDe: () -> Unit = {},
    navController: NavHostController, // Agregar este parámetro
    onLogout: () -> Unit = {},
    viewModel: ConfiguracionViewModel = hiltViewModel()
) {
    // Estados del ViewModel
    val configuracion by viewModel.configuracion.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()
    val hasPendingChanges by viewModel.hasPendingChanges.collectAsState()
    val isLoggingOut by viewModel.isLoggingOut.collectAsState()

    var categoriasSeleccionadas by remember { mutableStateOf(setOf<String>()) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val categoriasDisponibles = listOf(
        "Trabajo", "Proyectos", "Apoyos", "Avisos", "Emprende"
    )

    LaunchedEffect(configuracion) {
        configuracion?.let { config ->
            categoriasSeleccionadas = config.categoriasInteres!!.toSet()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título con indicadores de estado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CONFIGURACIÓN",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Indicador de sincronización
                if (isSyncing) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color.Cyan,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Sincronizando...",
                            color = Color.Cyan,
                            fontSize = 12.sp
                        )
                    }
                }

                // Indicador de offline
                if (isOffline) {
                    Icon(
                        Icons.Default.CloudOff,
                        contentDescription = "Sin conexión",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Indicador de cambios pendientes
                if (hasPendingChanges && !isSyncing) {
                    Icon(
                        Icons.Default.Sync,
                        contentDescription = "Cambios pendientes",
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Indicador de estado offline
        if (isOffline) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Yellow.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Default.CloudOff,
                        contentDescription = "Sin conexión",
                        tint = Color.Yellow
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Modo offline",
                            color = Color.Yellow,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Los cambios se guardarán localmente",
                            color = Color.Yellow.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                    if (!isSyncing) {
                        TextButton(
                            onClick = { viewModel.reintentarSincronizacion(correoElectronico) }
                        ) {
                            Text("Reintentar", color = Color.Yellow)
                        }
                    }
                }
            }
        }

        // Indicador de cambios pendientes
        if (hasPendingChanges && !isOffline) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Default.Sync,
                        contentDescription = "Cambios pendientes",
                        tint = Color.Cyan
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Cambios pendientes",
                            color = Color.Cyan,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Tienes cambios sin sincronizar",
                            color = Color.Cyan.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                    TextButton(
                        onClick = { viewModel.guardarCambiosEnServidor() },
                        enabled = !isLoading
                    ) {
                        Text("Sincronizar", color = Color.Cyan)
                    }
                }
            }
        }

        // Mostrar loading inicial
        if (isLoading && configuracion == null) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(color = Color.Cyan)
                    Text(
                        text = "Cargando configuración...",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            return@Column
        }

        // Mostrar error
        errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = Color.Red
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Error",
                            color = Color.Red,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = error,
                            color = Color.Red.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                    Row {
                        if (error.contains("conexión") || error.contains("internet")) {
                            TextButton(
                                onClick = { viewModel.reintentarSincronizacion(correoElectronico) }
                            ) {
                                Text("Reintentar", color = Color.Red)
                            }
                        }
                        TextButton(
                            onClick = { viewModel.limpiarError() }
                        ) {
                            Text("OK", color = Color.Red)
                        }
                    }
                }
            }
        }

        configuracion?.let { config ->

            // Notificaciones
            SwitchRow(
                label = "Notificaciones",
                checked = config.notificacionesEnabled,
                onCheckedChange = {
                    viewModel.toggleNotificaciones(correoElectronico)
                },
                enabled = true
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
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Chips de categorías
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(categoriasDisponibles) { categoria ->
                    val isSelected = categoriasSeleccionadas.contains(categoria)

                    AssistChip(
                        onClick = {
                            val nuevasSelecciones = if (isSelected) {
                                categoriasSeleccionadas - categoria
                            } else {
                                categoriasSeleccionadas + categoria
                            }

                            // Actualizar estado local
                            categoriasSeleccionadas = nuevasSelecciones

                            // Guardar en Room/Firebase
                            viewModel.actualizarCategorias(correoElectronico, nuevasSelecciones)
                        },
                        label = {
                            Text(
                                text = categoria,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (isSelected) {
                                Color.Cyan
                            } else {
                                Color.Gray.copy(alpha = 0.3f)
                            }
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
                checked = config.visibilidadEnabled,
                onCheckedChange = {
                    viewModel.toggleVisibilidad(correoElectronico)
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Disponibilidad para proyectos
            SwitchRow(
                label = "Disponibilidad para proy.",
                checked = config.disponibilidadEnabled,
                onCheckedChange = {
                    viewModel.toggleDisponibilidad(correoElectronico)
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para guardar cambios
            Button(
                onClick = { viewModel.guardarCambiosEnServidor() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = hasPendingChanges && !isLoading && !isSyncing,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasPendingChanges) Color.Cyan else Color.Gray,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                    disabledContentColor = Color.Gray
                )
            ) {
                if (isLoading || isSyncing) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Text("Guardando...")
                    }
                } else {
                    Text(
                        text = if (hasPendingChanges) "Guardar Cambios" else "Todo Guardado",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))



        } ?: run {
            // Si no hay configuración después de cargar, mostrar mensaje
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "No se pudo cargar la configuración",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Button(
                        onClick = { viewModel.cargarConfiguracion(correoElectronico) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                    ) {
                        Text(
                            text = "Reintentar",
                            color = Color.Black
                        )
                    }
                }
            }
        }

        ArrowOption(
            label = "Soporte y ayuda",
            onClick = onNavigateToSoporte
        )

        ArrowOption(
            label = "Acerca de",
            onClick = onNavigateToAcercaDe
        )

        ArrowOption(
            label = "Cambiar contraseña",
            onClick = { /* TODO: Implementar más tarde */ }
        )

        ArrowOption(
            label = "Cerrar sesión",
            onClick = { showLogoutDialog = true }
        )

        // Dialog de confirmación de logout
        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout(
                        navController = navController,
                        onSuccess = onLogout,
                        onError = { error ->
                            // Manejar error de logout si es necesario
                            // Podrías mostrar un snackbar o mensaje de error
                        }
                    )
                },
                onDismiss = { showLogoutDialog = false },
                isLoading = isLoggingOut,
                hasPendingChanges = hasPendingChanges
            )
        }
    }
}

@Composable
fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean = false,
    hasPendingChanges: Boolean = false
) {
    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = {
            Text(
                text = "Cerrar sesión",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "¿Estás seguro de que deseas cerrar sesión?",
                    color = Color.White.copy(alpha = 0.8f)
                )
                if (hasPendingChanges) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "⚠ Tienes cambios sin guardar que se perderán.",
                        color = Color.Yellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Cerrando...",
                            color = Color.Red
                        )
                    }
                } else {
                    Text(
                        text = "Cerrar sesión",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text(
                    text = "Cancelar",
                    color = Color.Cyan
                )
            }
        },
        containerColor = Color.Black,
        tonalElevation = 8.dp
    )
}

@Composable
fun ArrowOption(
    label: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) {
            Color.Cyan.copy(alpha = 0.1f)
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 150),
        label = "backgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isPressed) Color.Cyan else Color.White,
        animationSpec = tween(durationMillis = 150),
        label = "textColor"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = textColor,
                fontSize = 16.sp
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir a $label",
                tint = Color.Cyan
            )
        }
    }
}
@Composable
fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = if (enabled) Color.White else Color.Gray,
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Cyan,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f),
                disabledCheckedThumbColor = Color.Gray.copy(alpha = 0.5f),
                disabledCheckedTrackColor = Color.Gray.copy(alpha = 0.2f),
                disabledUncheckedThumbColor = Color.Gray.copy(alpha = 0.5f),
                disabledUncheckedTrackColor = Color.Gray.copy(alpha = 0.2f)
            )
        )
    }
}