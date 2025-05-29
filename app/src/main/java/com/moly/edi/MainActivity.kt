package com.moly.edi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.moly.edi.core.ui.theme.EDITheme
import com.moly.edi.presentation.splash.splashActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EDITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Solo mostrar el SplashScreen
                    splashActivity(
                        onNavigateToLogin = {
                            Toast.makeText(
                                this@MainActivity,
                                "Navegando a login...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}