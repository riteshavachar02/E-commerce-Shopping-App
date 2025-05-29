package com.example.e_commerceshopping.repository

import com.example.e_commerceshopping.data.model.ProductDetailResponse
import com.example.e_commerceshopping.data.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    private val apiService = NetworkModule.apiService

    suspend fun getProductDetails(id: String, productId: String): Result<ProductDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProductDetails(id, productId)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()
                    Result.success(body!!.data)
                } else {
                    Result.failure(Exception("Failed to fetch product details: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                println("Error fetching product details, $e")
                Result.failure(e)
            }
        }
    }
}