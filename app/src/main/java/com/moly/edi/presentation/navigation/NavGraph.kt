package com.moly.edi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.presentation.login.LoginScreen
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.splash.SplashActivity

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
            LoginScreen()
        }
        
        composable(Screen.Noticias.route) {
            NoticiasScreen().Noticias(navController)
        }
        
        composable(Screen.Configuracion.route) {
            //ConfiguracionScreen(navController)
        }
        
        composable(Screen.Perfil.route) {
            //PerfilScreen(navController)
        }
    }
}
