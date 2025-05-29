package com.moly.edi.presentation.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.splash.splashActivity

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            splashActivity(navController)
        }
        
        composable(Screen.Login.route) {
            //LoginScreen(navController)
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
