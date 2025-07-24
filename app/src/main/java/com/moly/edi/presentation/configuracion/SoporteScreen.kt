package com.moly.edi.presentation.configuracion
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoporteScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con botón de regreso
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.Cyan
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "SOPORTE Y AYUDA",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Sección de Contacto
        Text(
            text = "¿Necesitas ayuda?",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Estamos aquí para ayudarte. Contacta con nuestro equipo de soporte:",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tarjeta de Email
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color.Cyan,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Email",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "soporte@edi.com",
                        color = Color.Cyan,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Tarjeta de Teléfono
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Teléfono",
                    tint = Color.Cyan,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Teléfono",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "+51 999 888 777",
                        color = Color.Cyan,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Preguntas Frecuentes
        Text(
            text = "Preguntas Frecuentes",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // FAQ Items
        FAQItem(
            question = "¿Cómo cambio mi configuración de notificaciones?",
            answer = "Ve a Configuración > Notificaciones y activa/desactiva según tus preferencias."
        )

        FAQItem(
            question = "¿Cómo actualizo mi perfil?",
            answer = "Ve a Configuración > Sobre perfil y modifica tu visibilidad y disponibilidad."
        )

        FAQItem(
            question = "¿Los cambios se guardan automáticamente?",
            answer = "Los cambios se guardan localmente de inmediato y se sincronizan con el servidor cuando hay conexión."
        )
    }
}


@Composable
fun FAQItem(
    question: String,
    answer: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    Icons.Default.Help,
                    contentDescription = "FAQ",
                    tint = Color.Cyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = question,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
            Text(
                text = answer,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 28.dp)
            )
        }
    }
}
