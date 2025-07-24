package com.moly.edi.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.moly.edi.core.auth.AuthPreferences
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moly.edi.presentation.noticias.NoticiasScreen
import com.moly.edi.presentation.perfil.ProfileScreen
import com.moly.edi.presentation.splash.SplashScreenWithAuth
import com.moly.edi.presentation.login.LoginScreenWithAuth
import com.moly.edi.presentation.conecta.UserConnectScreen
import com.moly.edi.core.componentes.BottomNavigationBar
import com.moly.edi.data.dataSource.remote.api.ConfiguracionApiService
import com.moly.edi.presentation.configuracion.ConfiguracionScreen
import com.moly.edi.presentation.auth.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController, context: Context) {
    val authPrefs = AuthPreferences(context)
    val userEmailFlow = authPrefs.userEmail
    val userEmail by userEmailFlow.collectAsState(initial = null)

    // AuthViewModel para la persistencia de login
    val authViewModel: AuthViewModel = hiltViewModel()

    val bottomBarScreens = listOf(
        Screen.Noticias,
        Screen.UserConnect,
        Screen.Perfil
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            val showBottomBar =
                currentRoute == Screen.Noticias.route ||
                (currentRoute?.startsWith("perfil/") == true) ||
                (currentRoute?.startsWith("conecta/") == true)
            if (showBottomBar) {
                BottomNavigationBar(navController, bottomBarScreens, userEmail)
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
                SplashScreenWithAuth(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Noticias.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel
                )
            }

            composable(Screen.Login.route) {
                LoginScreenWithAuth(
                    onLoginSuccess = { email, name ->
                        // Guardar la sesión usando AuthViewModel
                        authViewModel.login(email, name)
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
                // Instancias manuales para la pantalla de configuración
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://edi-backend-ww44.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(ConfiguracionApiService::class.java)

                ConfiguracionScreen(
                    correoElectronico = userEmail.orEmpty()
                )
            }

            composable(
                route = "perfil/{${Screen.Perfil.PERFIL_ARG}}"
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString(Screen.Perfil.PERFIL_ARG) ?: userEmail.orEmpty()
                ProfileScreen(
                    userEmail = email,
                    onSettingsClick = { navController.navigate(Screen.Configuracion.route) }
                )
            }
            composable(
                route = "conecta/{${Screen.UserConnect.CONECTA_ARG}}"
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString(Screen.UserConnect.CONECTA_ARG) ?: userEmail.orEmpty()
                UserConnectScreen(navController, email)
            }
        }
    }
}
