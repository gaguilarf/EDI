package com.moly.edi.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Noticias : Screen("noticias")
    object Configuracion : Screen("configuracion")
    object Perfil : Screen("perfil")
    object UserConnect : Screen("conecta")
}
