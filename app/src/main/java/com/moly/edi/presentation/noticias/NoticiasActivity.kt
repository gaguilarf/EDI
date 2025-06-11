package com.moly.edi.presentation.noticias

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moly.edi.core.componentes.NoticiaCard
import com.moly.edi.core.componentes.SearchBarWithFilter
import com.moly.edi.core.componentes.SectionHeader

@Composable
fun NoticiasScreen(
    viewModel: NoticiasViewModel = hiltViewModel()
) {
    println("NoticiasScreen: Composable llamado")
    val noticias by viewModel.noticias
    println("NoticiasScreen: Número de noticias: ${noticias.size}")
    
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
                modifier = Modifier.fillMaxSize(),
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