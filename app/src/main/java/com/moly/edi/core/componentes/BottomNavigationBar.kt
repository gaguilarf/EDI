package com.moly.edi.core.componentes

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moly.edi.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<Screen>, userEmail: String?) {
    val darkGray = Color(0xFF232323)
    val selectedColor = Color(0xFFFFFFFF)
    val unselectedColor = Color(0xFFB0B0B0)
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = darkGray,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val selected = currentRoute == screen.route ||
                (screen is Screen.Perfil && currentRoute?.startsWith("perfil/") == true) ||
                (screen is Screen.UserConnect && currentRoute?.startsWith("conecta/") == true)
            val iconColor by animateColorAsState(
                targetValue = if (selected) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = 350)
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = getIconForScreen(screen),
                        contentDescription = screen.route,
                        tint = iconColor
                    )
                },
                label = {
                    Text(
                        screen.route.replaceFirstChar { it.uppercase() },
                        color = iconColor
                    )
                },
                selected = selected,
                onClick = {
                    if (!selected) {
                        val route = when (screen) {
                            is Screen.Perfil -> if (!userEmail.isNullOrEmpty()) "perfil/$userEmail" else screen.route
                            is Screen.UserConnect -> if (!userEmail.isNullOrEmpty()) "conecta/$userEmail" else screen.route
                            else -> screen.route
                        }
                        navController.navigate(route) {
                            popUpTo(Screen.Noticias.route)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = darkGray,
                    selectedIconColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    selectedTextColor = selectedColor,
                    unselectedTextColor = unselectedColor
                )
            )
        }
    }
}

private fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        Screen.Noticias -> Icons.Default.Home
        Screen.UserConnect -> Icons.Default.Share
        Screen.Perfil -> Icons.Default.Person
        else -> Icons.Default.Home
    }
}
