package com.moly.edi.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.perfil.ProfileScreen
import com.moly.edi.presentation.splash.SplashActivity
import com.moly.edi.presentation.login.LoginScreen
import com.moly.edi.presentation.conecta.UserConnectScreen
import com.moly.edi.core.componentes.BottomNavigationBar

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val bottomBarScreens = listOf(
        Screen.Noticias,
        Screen.UserConnect,
        Screen.Perfil
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        // Eliminado contentWindowInsets para respetar los insets del sistema
        bottomBar = {
            if (currentRoute in bottomBarScreens.map { it.route }) {
                BottomNavigationBar(navController, bottomBarScreens)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                // ConfiguracionScreen() - comentado temporalmente
            }

            composable(Screen.Perfil.route) {
                ProfileScreen(userEmail = "gaguilarf@unsa.edu.pe")
            }
            
            composable(Screen.UserConnect.route) {
                UserConnectScreen(navController, "bhanccoco@unsa.edu.pe")
            }
        }
    }
}
