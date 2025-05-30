package com.example.e_commerceshopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceshopping.data.model.Option
import com.example.e_commerceshopping.data.model.ProductDetailResponse
import com.example.e_commerceshopping.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: ProductDetailResponse? = null,
    val error: String? = null,
    val selectedColorOption: Option? = null,
    val selectedImageIndex: Int = 0,
    val quantity: Int = 0
)

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProductDetails(id: String = "6701", productId: String = "253620") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getProductDetails(id, productId)
                .onSuccess { product ->
                    val firstColorOption = product.configurableOption
                        ?.firstOrNull()
                        ?.attributes
                        ?.firstOrNull { it.label?.equals("Color", ignoreCase = true) == true }
                        ?.options
                        ?.firstOrNull()

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        product = product,
                        selectedColorOption = firstColorOption
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun selectColorOption(option: Option) {
        _uiState.value = _uiState.value.copy(
            selectedColorOption = option,
            selectedImageIndex = 0
        )
    }

    fun selectImage(index: Int) {
        _uiState.value = _uiState.value.copy(selectedImageIndex = index)
    }

    fun updateQuantity(newQuantity: Int) {
        if (newQuantity > 0) {
            _uiState.value = _uiState.value.copy(quantity = newQuantity)
        }
    }

    fun incrementQuantity() {
        _uiState.value = _uiState.value.copy(quantity = _uiState.value.quantity + 1)
    }

    fun decrementQuantity() {
        if (_uiState.value.quantity > 0) {
            _uiState.value = _uiState.value.copy(quantity = _uiState.value.quantity - 1)
        }
    }
}