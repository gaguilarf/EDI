package com.moly.edi.presentacion.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Noticias : Screen("noticias")
    object Configuracion : Screen("configuracion")
    object Perfil : Screen("perfil")
}
