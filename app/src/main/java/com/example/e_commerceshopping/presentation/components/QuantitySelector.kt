package com.example.e_commerceshopping.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Quantity",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    Color.Gray.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, RoundedCornerShape(6.dp))
                    .clickable { onDecrement() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "−",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (quantity > 1) Color.Black else Color.Gray
                )
            }
            Text(
                text = quantity.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black, RoundedCornerShape(6.dp))
                    .clickable { onIncrement() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}