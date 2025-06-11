package com.moly.edi.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.presentation.noticias.NoticiasActivity
import com.moly.edi.presentation.perfil.PerfilActivity
import com.moly.edi.presentation.splash.SplashActivity
import com.moly.edi.presentation.configuracion.ConfiguracionActivity
import com.moly.edi.presentation.login.LoginScreen
import com.moly.edi.presentation.navigation.Screen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashActivity(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Noticias.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Noticias.route) {
            NoticiasActivity()
        }
        
        composable(Screen.Configuracion.route) {
            ConfiguracionActivity()
        }
        
        composable(Screen.Perfil.route) {
            PerfilActivity()
        }
    }
}
