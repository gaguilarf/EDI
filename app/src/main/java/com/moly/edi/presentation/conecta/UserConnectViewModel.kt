package com.moly.edi.presentation.conecta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.data.dataSource.api.entity.dto.EstudianteDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class UserConnectViewModel : ViewModel() {

    var estudiante by mutableStateOf<EstudianteDTO?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadEstudiante(correo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            errorMessage = null
            try {
                val urlString = "https://edi-backend-ww44.onrender.com/usuario/${URLEncoder.encode(correo, "UTF-8")}/acerca"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                if (connection.responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val dto = parseEstudianteDTO(response)
                    withContext(Dispatchers.Main) {
                        estudiante = dto
                    }
                } else {
                    throw IOException("Error de red: ${connection.responseCode}")
                }
                connection.disconnect()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage = e.message
                }
            } finally {
                withContext(Dispatchers.Main) {
                    isLoading = false
                }
            }
        }
    }

    private fun parseEstudianteDTO(json: String): EstudianteDTO {
        val jsonObject = JSONObject(json)

        // Mapeo actualizado para el formato de respuesta
        val aptitudes = jsonObject.getJSONArray("aptitudes").let { array ->
            List(array.length()) { i -> array.getString(i) }
        }

        return EstudianteDTO(
            aptitudes = aptitudes,
            carrera = jsonObject.getString("carrera"),
            categoriasInteres = jsonObject.getString("categorias_interes"),  // actualizando el nombre del campo
            palabrasClave = jsonObject.getString("palabras_clave"),           // actualizando el nombre del campo
            semestre = jsonObject.getInt("semestre"),
            sobreMi = jsonObject.getString("sobre_mi")  // actualizando el nombre del campo
        )
    }
}
