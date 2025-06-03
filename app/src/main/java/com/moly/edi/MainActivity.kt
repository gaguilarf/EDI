package com.moly.edi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moly.edi.core.ui.theme.EDITheme
import com.moly.edi.core.componentes.NoticiaCard
import com.moly.edi.core.componentes.SearchBarWithFilter
import com.moly.edi.core.componentes.SectionHeader
import com.moly.edi.presentacion.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EDITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EdiMainScreen()
                }
            }
        }
    }
}

@Composable
fun EdiMainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val noticias by viewModel.noticias
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Evento") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sección de Todas las Noticias
                item {
                    SectionHeader(
                        title = "NOTICIAS"
                    )
                }

                item {
                    SearchBarWithFilter(
                        searchText = searchText,
                        onSearchTextChange = { searchText = it },
                        selectedFilter = selectedFilter,
                        onFilterSelected = {
                            selectedFilter = it
                            viewModel.obtenerNoticiasPorCategoria(it)
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(noticias) { noticia ->
                    NoticiaCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        noticia = noticia,
                        isImportant = false,
                        onClick = {
                            Toast.makeText(
                                context,
                                "Abriendo: ${noticia.titulo}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }

                // Espaciado final
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

