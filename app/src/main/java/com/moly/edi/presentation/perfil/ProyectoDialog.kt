package com.moly.edi.presentation.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moly.edi.domain.model.Project

@Composable
fun ProyectoDialog(
    proyecto: Project? = null,
    onDismiss: () -> Unit,
    onConfirm: (Project) -> Unit
) {
    var titulo by remember { mutableStateOf(proyecto?.titulo ?: "") }
    var descripcion by remember { mutableStateOf(proyecto?.descripcion ?: "") }
    val esEdicion = proyecto != null

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = if (esEdicion) "Editar Proyecto" else "Nuevo Proyecto",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val nuevoProyecto = if (proyecto != null) {
                                proyecto.copy(
                                    titulo = titulo,
                                    descripcion = descripcion
                                )
                            } else {
                                Project(
                                    userId = "USER_ID", // <- Asegúrate de reemplazar esto con el real
                                    titulo = titulo,
                                    descripcion = descripcion,
                                    isSynced = false,
                                    deletedLocally = false,
                                    modifiedLocally = false,
                                    createdAt = System.currentTimeMillis(),
                                    lastModified = System.currentTimeMillis()
                                )
                            }
                            onConfirm(nuevoProyecto)
                        }
                    ) {
                        Text(if (esEdicion) "Actualizar" else "Guardar")
                    }
                }
            }
        }
    }
}
