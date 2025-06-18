package com.moly.edi.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.perfil.PerfilActivity
import com.moly.edi.presentation.splash.SplashActivity
import com.moly.edi.presentation.configuracion.ConfiguracionScreen
import com.moly.edi.presentation.login.LoginScreen
import com.moly.edi.presentation.navigation.Screen
import com.moly.edi.presentation.conecta.UserConnectScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashActivity(
                onNavigateToLogin = {
                    navController.navigate(Screen.Noticias.route) {
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
            NoticiasScreen()
        }
        
        composable(Screen.Configuracion.route) {
            //ConfiguracionScreen()
        }
        
        composable(Screen.Perfil.route) {
            PerfilActivity()
        }
        composable(Screen.UserConnect.route) {
            UserConnectScreen(navController, "bhanccoco@unsa.edu.pe")
        }
    }
}
