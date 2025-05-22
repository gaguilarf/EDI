package com.moly.edi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.pages.ConfiguracionScreen
import com.moly.edi.pages.LoginScreen
import com.moly.edi.pages.PerfilScreen
import com.moly.edi.pages.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
            )
        }
        
        composable(Screen.Noticias.route) {

        }
        
        composable(Screen.Configuracion.route) {
            ConfiguracionScreen()
        }
        
        composable(Screen.Perfil.route) {
            PerfilScreen()
        }
    }
}
