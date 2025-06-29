
package com.moly.edi.presentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
// import androidx.compose.runtime.collectAsState
// import androidx.compose.runtime.getValue
// import com.moly.edi.core.auth.AuthPreferences
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// import androidx.navigation.compose.currentBackStackEntryAsState
// import com.moly.edi.presentation.noticias.NoticiasScreen
// import com.moly.edi.presentation.perfil.ProfileScreen
// import com.moly.edi.presentation.splash.SplashActivity
// import com.moly.edi.presentation.login.LoginScreen
// import com.moly.edi.presentation.conecta.UserConnectScreen
// import com.moly.edi.core.componentes.BottomNavigationBar
import com.moly.edi.presentation.configuracion.ConfiguracionScreen
// import com.moly.edi.data.dataSource.remote.api.ConfiguracionApiService
// import com.moly.edi.domain.repository.ConfiguracionRepository
// import com.moly.edi.data.repository.ConfiguracionRepositoryImpl
// import com.moly.edi.domain.useCase.GetConfiguracionUseCase
// import retrofit2.Retrofit
// import retrofit2.converter.gson.GsonConverterFactory
// import kotlin.jvm.java
// import androidx.room.Room
@Composable
fun SetupNavGraph(navController: NavHostController, context: Context) {
  /*  val authPrefs = AuthPreferences(context)
    val userEmailFlow = authPrefs.userEmail
    val userEmail by userEmailFlow.collectAsState(initial = null)
*/
    Scaffold {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Configuracion.route, //  DIRECTO A CONFIGURACIÓN
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            /*
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
                NoticiasScreen()
            }
            */

            // configuracion
            composable(Screen.Configuracion.route) {
                ConfiguracionScreen(
                    correoElectronico = "gaguilarf@unsa.edu.pe"
                    // viewModel se inyecta automáticamente con @hiltViewModel
                )
            }
        }
    }
}