package com.emm.chambaaltoque.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDto(

    val id: String,

    @SerialName("solicitante_id")
    val requesterId: String,

    @SerialName("category_id")
    val categoryId: String,

    val title: String,
    val description: String,
    val budget: Double,
    val status: String,

    @SerialName("created_at")
    val createdAt: String,
)