package com.moly.edi.presentation.conecta

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.moly.edi.R
import com.moly.edi.data.model.ConectaDTO
import com.moly.edi.presentation.navigation.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import com.moly.edi.core.componentes.SectionHeader

@Composable
fun UserConnectScreen(
    navController: NavController,
    correoElectronico: String,
    viewModel: UserConnectViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    
    LaunchedEffect(correoElectronico) {
        viewModel.loadEstudiante(correoElectronico)
    }

    Scaffold(
        topBar = { /* tu top bar */ },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                SectionHeader(
                    title = "CONECTA",
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            item {
                when {
                    viewModel.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                    viewModel.errorMessage != null -> {
                        Column {
                            Text(
                                text = viewModel.errorMessage ?: "Error",
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { viewModel.testServer() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF484E4E)
                                )
                            ) {
                                Text("Probar Servidor", color = Color.White)
                            }
                        }
                    }
                    viewModel.estudiante != null -> {
                        UserConnectCard(
                            estudiante = viewModel.estudiante!!,
                            onConectarClick = {
                                // Abrir WhatsApp con mensaje predeterminado
                                val mensaje = "¡Hola! Vi tu perfil en EDI y me gustaría conectar contigo. ¿Te parece si conversamos sobre proyectos o colaboraciones?"
                                val whatsappUrl = "https://wa.me/${viewModel.estudiante?.celular?.replace("+", "")?.replace(" ", "")}?text=${Uri.encode(mensaje)}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
                                context.startActivity(intent)
                            },
                            onConoceMasClick = {
                                // Navegar a una vista de perfil simplificada
                                navController.navigate("perfil_conecta/${correoElectronico}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserConnectCard(
    estudiante: ConectaDTO,
    onConectarClick: () -> Unit,
    onConoceMasClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar y información básica
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF8C42)), // Naranja claro
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Información del usuario
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = estudiante.nombres,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = estudiante.carrera,
                        fontSize = 14.sp,
                        color = Color(0xFF888888)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Semestre: ${estudiante.semestre}",
                        fontSize = 14.sp,
                        color = Color(0xFF888888)
                    )
                }
            }
            
            // Tecnologías
            Column {
                Text(
                    text = "Tecnologías",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Chips de tecnologías
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    estudiante.palabras_clave?.split(",")?.take(3)?.forEach { tecnologia ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF20B2AA) // Teal
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = tecnologia.trim(),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            
            // Sobre mí
            Column {
                Text(
                    text = "Sobre mí",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2A2A2A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = estudiante.sobre_mi,
                        modifier = Modifier.padding(12.dp),
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onConoceMasClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF20B2AA) // Teal
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Conoce más",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                
                Button(
                    onClick = onConectarClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF20B2AA) // Teal
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Conectar",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
