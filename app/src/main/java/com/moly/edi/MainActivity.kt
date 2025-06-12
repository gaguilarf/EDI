package com.moly.edi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionApiService
import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionRepository
import com.moly.edi.domain.useCase.GetConfiguracionUseCase
import com.moly.edi.presentation.configuracion.ConfiguracionScreen
import com.moly.edi.presentation.configuracion.ConfiguracionViewModel
import com.moly.edi.presentation.configuracion.ConfiguracionViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Crear dependencias manualmente
        val retrofit = Retrofit.Builder()
            .baseUrl("https://edi-backend-ww44.onrender.com/")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ConfiguracionApiService::class.java)
        val repository = ConfiguracionRepository(apiService)
        val useCase = GetConfiguracionUseCase(repository)
        val factory = ConfiguracionViewModelFactory(useCase)

        setContent {
            ConfiguracionScreen(
                viewModelFactory = factory,
                correoElectronico = "bhanccoco@unsa.edu.pe"
            )
        }
    }
}