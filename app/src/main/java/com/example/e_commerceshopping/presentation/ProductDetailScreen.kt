package com.example.e_commerceshopping.presentation

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerceshopping.data.model.Option
import com.example.e_commerceshopping.presentation.components.ColorSelector
import com.example.e_commerceshopping.presentation.components.ImageSlider
import com.example.e_commerceshopping.presentation.components.ProductBottomBar
import com.example.e_commerceshopping.presentation.components.QuantitySelector
import com.example.e_commerceshopping.viewmodel.ProductDetailUiState
import com.example.e_commerceshopping.viewmodel.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackPressed: () -> Unit = {},
    onFavoriteClick : () -> Unit = {},
    viewModel: ProductDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProductDetails()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.product?.name ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onFavoriteClick()
                    }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                    }
                    IconButton(onClick = { /* Handle share */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* Handle cart */ }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            if (uiState.quantity > 0) {
                ProductBottomBar()
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error!!,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }

            uiState.product != null -> {
                ProductDetailContent(
                    uiState = uiState,
                    onColorSelected = viewModel::selectColorOption,
                    onImageSelected = viewModel::selectImage,
                    onQuantityIncrement = viewModel::incrementQuantity,
                    onQuantityDecrement = viewModel::decrementQuantity,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun ProductDetailContent(
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

        // Product Description
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