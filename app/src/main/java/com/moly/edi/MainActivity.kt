package com.moly.edi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.moly.edi.core.ui.theme.EDITheme
import com.moly.edi.presentacion.navigation.SetupNavGraph
import com.moly.edi.presentation.noticias.NoticiasActivity

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
                    SetupNavGraph(navController = navController)
                    MainScreen()
                    // Solo mostrar el SplashScreen
                    /*splashActivity(
                        onNavigateToLogin = {
                            // Simplemente mostrar un Toast cuando termine el splash
                            Toast.makeText(
                                this@MainActivity,
                                "Navegando a login...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )*/
                }
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val intent = Intent(context, NoticiasActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Ver Noticias")
        }
    }
}