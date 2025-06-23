package com.moly.edi.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Noticias : Screen("noticias")
    object Configuracion : Screen("configuracion")
    object Perfil : Screen("perfil") {
        const val PERFIL_ARG = "correo"
    }
    object UserConnect : Screen("conecta")
}
