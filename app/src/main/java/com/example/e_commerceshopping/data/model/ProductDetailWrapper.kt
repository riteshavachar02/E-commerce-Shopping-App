package com.example.e_commerceshopping.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailWrapper(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ProductDetailResponse
)
