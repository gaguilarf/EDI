package com.moly.edi

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.moly.edi.core.ui.theme.EDITheme
import com.moly.edi.presentation.perfil.PerfilActivity
import com.moly.edi.presentacion.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EDITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()

                    // Toda la app ahora maneja navegación
                    Box(modifier = Modifier.fillMaxSize()) {
                        SetupNavGraph(navController = navController)
                        
                        // Botón flotante para abrir el perfil
                        Button(
                            onClick = {
                                val intent = Intent(this@MainActivity, PerfilActivity::class.java)
                                startActivity(intent)
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Text("Perfil")
                        }
                    }
                }
            }
        }
    }
}
