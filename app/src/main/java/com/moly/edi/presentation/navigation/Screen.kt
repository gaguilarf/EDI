package com.moly.edi.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Noticias : Screen("noticias")
    object NoticiaDetalle : Screen("noticia_detalle/{noticia_id}") {
        const val NOTICIA_ID_ARG = "noticia_id"
    }
    object Configuracion : Screen("configuracion")
    object Soporte : Screen("soporte")
    object AcercaDe : Screen("acerca_de")
    object Perfil : Screen("perfil") {
        const val PERFIL_ARG = "correo"
    }
    object UserConnect : Screen("conecta") {
        const val CONECTA_ARG = "correo"
    }
    object PerfilConecta : Screen("perfil_conecta/{correo}") {
        const val CORREO_ARG = "correo"
    }
}
