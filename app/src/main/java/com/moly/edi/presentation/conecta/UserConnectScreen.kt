package com.moly.edi.presentation.conecta

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.moly.edi.R
import com.moly.edi.data.dataSource.api.entity.dto.EstudianteDTO
import com.moly.edi.presentation.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import com.moly.edi.core.componentes.SectionHeader

@Composable
fun UserConnectScreen(
    navController: NavController,
    correoElectronico: String,
    viewModel: UserConnectViewModel = viewModel()
) {
    LaunchedEffect(correoElectronico) {
        viewModel.loadEstudiante(correoElectronico)
    }

    Scaffold(
        topBar = { /* tu top bar */ },
        containerColor = Color.Black // Igual que en Noticias
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
                        CircularProgressIndicator(color = Color.White)
                    }
                    viewModel.errorMessage != null -> {
                        Text(
                            text = viewModel.errorMessage ?: "Error",
                            color = Color.Red
                        )
                    }
                    viewModel.estudiante != null -> {
                        UserConnectCard(
                            estudiante = viewModel.estudiante!!,
                            onSettingsClick = { navController.navigate(Screen.Configuracion.route) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun UserConnectCard(estudiante: EstudianteDTO, onSettingsClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(color = Color(0xFF484E4E), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(id = R.drawable.unsa),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(width = 80.dp, height = 60.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = estudiante.carrera,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Semestre: ${estudiante.semestre}",
                    color = Color(0xFF888888),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = estudiante.categoriasInteres,
                    color = Color(0xFF888888),
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Palabras clave: ${estudiante.palabrasClave}",
            color = Color.White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color(0xFF3F3F3F), shape = RoundedCornerShape(20.dp))
                .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Text(
                text = estudiante.sobreMi,
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { println("Conectando con ${estudiante.carrera}") },
                shape = RoundedCornerShape(10.dp),
                elevation = null
            ) {
                Text(text = "Conectemos", color = Color.White)
            }
        }
    }
}
