package com.moly.edi.presentation.noticias

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moly.edi.domain.model.Noticia
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.KeyboardArrowDown

class NoticiasScreen {

    @Composable
    fun InfoCard(noticia: Noticia) {
        var expanded by remember { mutableStateOf(false) }
        var isTextOverflowing by remember { mutableStateOf(false) }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E), //Card background color
                contentColor = Color.White  //Card content color,e.g.text
            ),
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Author",
                            tint = Color(0xFFFFA5D2)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(noticia.author, color = Color.White)
                    }
                    Text(noticia.date, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(15.dp))
                Text(noticia.title, fontWeight = FontWeight.Bold, color = Color.White)

                Spacer(modifier = Modifier.height(8.dp))
                // Body text con "Leer más"
                Box {
                    val textLayoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

                    Text(
                        text = noticia.body,
                        color = Color.White,
                        maxLines = if (expanded) Int.MAX_VALUE else 4,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { result ->
                            isTextOverflowing = result.hasVisualOverflow
                            textLayoutResult.value = result
                        }
                    )

                    if (isTextOverflowing && !expanded) {
                        Text(
                            text = "Leer más",
                            color = Color(0xFF64B5F6),
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clickable { expanded = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                // Tags
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    noticia.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF333333), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(tag, color = Color.White, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                // Likes
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${noticia.likes}", color = Color.White)
                }
            }
        }
    }

    @Composable
    fun Noticias(navController: NavController) {
        val viewModel: NoticiasViewModel = viewModel()
        val noticias by viewModel.noticias.collectAsState()
        var searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // Titulo
            Text(
                text = "NOTICIAS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de busqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Busca algo", color = Color.Gray) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Filtrar")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de noticias
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val filteredNoticias = noticias.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            it.body.contains(searchQuery, ignoreCase = true)
                }
                items(filteredNoticias) { noticia ->
                    InfoCard(noticia = noticia)
                }
            }
        }
    }
}