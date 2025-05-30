package com.example.e_commerceshopping.presentation

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceshopping.data.model.Option
import com.example.e_commerceshopping.presentation.components.ColorSelector
import com.example.e_commerceshopping.presentation.components.ImageSlider
import com.example.e_commerceshopping.presentation.components.QuantitySelector
import com.example.e_commerceshopping.viewmodel.ProductDetailUiState

@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState,
    onColorSelected: (Option) -> Unit,
    onImageSelected: (Int) -> Unit,
    onQuantityIncrement: () -> Unit,
    onQuantityDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val product = uiState.product ?: return

    val displayImages = when {
        !uiState.selectedColorOption?.images.isNullOrEmpty() -> uiState.selectedColorOption!!.images
        !product.mediaGallery.isNullOrEmpty() -> product.mediaGallery
        product.image.isNotBlank() -> listOf(product.image)
        else -> listOf("")
    }

    val colorOptions = product.configurableOption
        ?.firstOrNull()
        ?.attributes
        ?.firstOrNull { it.label?.equals("Color", ignoreCase = true) == true }
        ?.options ?: emptyList()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            if (displayImages != null) {
                ImageSlider(
                    images = displayImages,
                    selectedIndex = uiState.selectedImageIndex,
                    onImageSelected = onImageSelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.brandName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "${product.price} KWD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "SKU: ${product.name.lowercase().replace(" ", "-")}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }

        if (colorOptions.isNotEmpty()) {
            item {
                ColorSelector(
                    colorOptions = colorOptions,
                    selectedOption = uiState.selectedColorOption,
                    onColorSelected = onColorSelected,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
        }

        item {
            QuantitySelector(
                quantity = uiState.quantity,
                onQuantityChanged = { },
                onIncrement = onQuantityIncrement,
                onDecrement = onQuantityDecrement,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "PRODUCT INFORMATION",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        item {
            val cleanDescription = Html.fromHtml(
                product.description,
                Html.FROM_HTML_MODE_COMPACT
            ).toString()

            Text(
                text = cleanDescription,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}