package com.moly.edi.presentation.noticias

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    /*@Preview(showBackground = true)
    @Composable
    fun PreviewCard() {
        val longText =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries, but also the leap into electronic typesetting..."

        InfoCard(body = longText)
    }*/

    @Composable
    fun Noticias() {
        val viewModel = remember { NoticiasViewModel() }

        Column {
            viewModel.noticias.forEach { noticia: Noticia ->
                InfoCard(noticia = noticia)
            }
        }
    }
}