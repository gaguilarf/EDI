package com.moly.edi.presentation.perfil
/*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moly.edi.domain.model.Project
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.moly.edi.R
import com.moly.edi.core.componentes.SectionHeader
import com.moly.edi.core.ui.theme.EDITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EDITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val userEmail = "gaguilarf@unsa.edu.pe"
                    ProfileScreen(userEmail = userEmail)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userEmail: String,
    viewModel: PerfilViewModel = hiltViewModel(),
    onSettingsClick: (() -> Unit)? = null
) {
    var mostrarDialogo by remember { mutableStateOf(false)}
    val user by viewModel.user.collectAsState()
    val technologies by viewModel.technologies.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(key1 = userEmail) {
        viewModel.loadUserData(userEmail)
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    error?.let { errorMessage ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "PERFIL",
                modifier = Modifier.padding(top = 24.dp,
                    bottom = 12.dp)
            )
        }

        item {

            user?.let { user ->
                ProfileHeader(
                    name = user.nombre,
                    email = user.correo ?: "",
                    phone = user.telefono ?: "",
                    linkedin = user.linkedin ?: "",
                    github = user.github ?: "",
                    instagram = user.instagram ?: "",
                    onSettingsClick = onSettingsClick
                )
            }
        }
        if (technologies.isNotEmpty()) {
            item {
                TechnologiesSection(technologies = technologies)
            }
        }
        if (projects.isNotEmpty()) {
            item {
                ProjectsSection(projects = projects)
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Añadir")
                }

                if (mostrarDialogo) {
                    ProyectoDialog(
                        onDismiss = { mostrarDialogo = false },
                        onConfirm = { nuevoProyecto ->
                            // Manejo del nuevo proyecto
                            mostrarDialogo = false
                        }
                    )
                }

                Button(
                    onClick = { /* Handle edit action */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Editar")
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    email: String,
    phone: String,
    linkedin: String,
    github: String,
    instagram: String,
    onSettingsClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Surface(
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6200EE),
                                    Color(0xFFBB86FC)
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Name and Email
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = email,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Settings Icon
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onSettingsClick?.invoke()
                        }
                )
            }

            // Social Icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialIcon(
                    iconRes = R.drawable.ic_phone,
                    contentDescription = "Phone",
                    enabled = phone.isNotBlank(),
                    onClick = {
                        if (phone.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$phone")
                            }
                            context.startActivity(intent)
                        }
                    }
                )
                SocialIcon(
                    iconRes = R.drawable.ic_linkedin,
                    contentDescription = "LinkedIn",
                    enabled = linkedin.isNotBlank(),
                    onClick = {
                        if (linkedin.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedin))
                            context.startActivity(intent)
                        }
                    }
                )
                SocialIcon(
                    iconRes = R.drawable.ic_github,
                    contentDescription = "GitHub",
                    enabled = github.isNotBlank(),
                    onClick = {
                        if (github.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(github))
                            context.startActivity(intent)
                        }
                    }
                )
                SocialIcon(
                    iconRes = R.drawable.ic_instagram,
                    contentDescription = "Instagram",
                    enabled = instagram.isNotBlank(),
                    onClick = {
                        if (instagram.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagram))
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SocialIcon(
    iconRes: Int,
    contentDescription: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val iconColor = if (enabled) Color(0xFF03DAC5) else Color.Gray
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(iconColor.copy(alpha = 0.2f))
            .let {
                if (enabled) it.clickable { onClick() } else it
            }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = if (enabled) Color(0xFF03DAC5) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun TechnologiesSection(technologies: List<String>) {
    Surface(
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tecnologías",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                technologies.forEach { tech ->
                    Surface(
                        color = Color(0xFF2A2A2A),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = tech,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectsSection(projects: List<Project>) {
    Surface(
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Proyectos",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            if (projects.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    projects.forEach { project ->
                        ProjectCard(project = project)
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF2A2A2A),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = project.titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = project.descripcion,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}*/