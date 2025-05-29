package com.moly.edi.presentacion.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Componente 1: Barra de progreso del formulario
@Composable
fun FormProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progreso del formulario",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                color = Color(0xFF2196F3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF2196F3))
            )
        }
    }
}

// Componente 2: Indicador de estado de campos requeridos
@Composable
fun RequiredFieldsStatus(
    fieldsStatus: Map<String, Boolean>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Campos requeridos",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            fieldsStatus.forEach { (fieldName, isCompleted) ->
                RequiredFieldItem(
                    fieldName = getFieldDisplayName(fieldName),
                    isCompleted = isCompleted
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun RequiredFieldItem(
    fieldName: String,
    isCompleted: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (isCompleted) Color.Green else Color.Red,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = fieldName,
            color = if (isCompleted) Color.White else Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

private fun getFieldDisplayName(fieldName: String): String {
    return when (fieldName) {
        "email" -> "Email"
        "contrasena" -> "Contraseña"
        "numeroCelular" -> "Número Celular"
        else -> fieldName
    }
}

// Componente adicional: Circular Progress para visualizar progreso general
@Composable
fun CircularFormProgress(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2

            // Background circle
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                radius = radius,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = Color(0xFF2196F3),
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${(progress * 100).toInt()}%",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}