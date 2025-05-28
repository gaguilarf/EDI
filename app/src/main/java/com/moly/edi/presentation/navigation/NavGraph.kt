package com.moly.edi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.presentation.configuracion.configuracionActivity
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.splash.splashActivity

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Noticias.route
    ) {
        composable(Screen.Splash.route) {
            splashActivity(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {

        }
        
        composable(Screen.Noticias.route) {
            NoticiasScreen()
        }
        
        composable(Screen.Configuracion.route) {
            configuracionActivity()
        }
        
        composable(Screen.Perfil.route) {
        }
    }
}
