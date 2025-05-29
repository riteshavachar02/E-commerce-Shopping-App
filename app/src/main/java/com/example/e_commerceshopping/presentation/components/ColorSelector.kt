package com.example.e_commerceshopping.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceshopping.data.model.Option

@Composable
fun ColorSelector(
    modifier: Modifier = Modifier,
    colorOptions: List<Option>,
    selectedOption: Option?,
    onColorSelected: (Option) -> Unit
) {
    if (colorOptions.isEmpty()) return

    Column(modifier = modifier) {
        Text(
            text = "Color:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colorOptions) { option ->
                val isSelected = option == selectedOption
                val imageUrl = option.images?.firstOrNull() ?: ""

                AsyncImage(
                    model = imageUrl,
                    contentDescription = option.value,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(option) },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}