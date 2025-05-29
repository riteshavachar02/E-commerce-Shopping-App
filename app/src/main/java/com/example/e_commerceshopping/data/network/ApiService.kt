package com.example.e_commerceshopping.data.network

import com.example.e_commerceshopping.data.model.ProductDetailWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("rest/V1/productdetails/{id}/{productId}")
    suspend fun getProductDetails(
        @Path("id") id: String,
        @Path("productId") productId: String,
        @Query("lang") lang: String = "en",
        @Query("store") store: String = "KWD"
    ): Response<ProductDetailWrapper>
}