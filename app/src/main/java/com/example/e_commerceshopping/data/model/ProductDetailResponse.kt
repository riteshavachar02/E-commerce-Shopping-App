package com.example.e_commerceshopping.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("brand_name") val brandName: String,
    @SerializedName("price") val price: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("media_gallery") val mediaGallery: List<String>? = null,
    @SerializedName("configurable_option") val configurableOption: List<ConfigurableOption>? = null
)

data class ConfigurableOption(
    @SerializedName("attributes") val attributes: List<Attribute>? = null
)

data class Attribute(
    @SerializedName("label") val label: String? = null,
    @SerializedName("options") val options: List<Option>? = null
)

data class Option(
    @SerializedName("value") val value: String = "",
    @SerializedName("images") val images: List<String>? = null,
    @SerializedName("price") val price: String = ""
)
