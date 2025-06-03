package com.moly.edi.core.componentes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    topSpacing: Boolean = true
) {
    if (topSpacing) {
        Spacer(modifier = Modifier.height(8.dp))
    }
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}