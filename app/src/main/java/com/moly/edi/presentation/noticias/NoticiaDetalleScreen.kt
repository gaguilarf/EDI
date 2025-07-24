package com.moly.edi.presentation.noticias

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moly.edi.domain.model.Noticia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaDetalleScreen(
    navController: NavController,
    noticiaId: String,
    viewModel: NoticiaDetalleViewModel = hiltViewModel()
) {
    val noticia by viewModel.noticia.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    var reacciones by remember { mutableStateOf(0) }
    var isLiked by remember { mutableStateOf(false) }
    var isLoadingReaccion by remember { mutableStateOf(false) }

    LaunchedEffect(noticiaId) {
        viewModel.cargarNoticia(noticiaId)
    }

    LaunchedEffect(noticia) {
        noticia?.let {
            reacciones = it.reacciones
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Noticia", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Error desconocido",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
            noticia != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Título de la noticia
                        Text(
                            text = noticia!!.titulo,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        // Información del autor y fecha
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Por: ${noticia!!.autor}",
                                fontSize = 14.sp,
                                color = Color(0xFF888888)
                            )
                            Text(
                                text = noticia!!.fecha,
                                fontSize = 14.sp,
                                color = Color(0xFF888888)
                            )
                        }
                    }

                    item {
                        // Categoría
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF484E4E)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = noticia!!.categoria,
                                modifier = Modifier.padding(8.dp),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }

                    item {
                        // Descripción larga
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF484E4E)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = noticia!!.descripcion_larga,
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }

                    item {
                        // Sección de reacciones
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF484E4E)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Reacciones",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            if (!isLoadingReaccion) {
                                                isLoadingReaccion = true
                                                val accion = if (isLiked) "quitar" else "agregar"
                                                viewModel.modificarReaccion(accion) { success, nuevasReacciones ->
                                                    if (success) {
                                                        reacciones = nuevasReacciones
                                                        isLiked = !isLiked
                                                    }
                                                    isLoadingReaccion = false
                                                }
                                            }
                                        },
                                        enabled = !isLoadingReaccion
                                    ) {
                                        Icon(
                                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = if (isLiked) "Quitar reacción" else "Agregar reacción",
                                            tint = if (isLiked) Color.Red else Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    
                                    Text(
                                        text = "$reacciones reacciones",
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    
                                    if (isLoadingReaccion) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 