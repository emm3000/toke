package com.emm.chambaaltoque.postjob.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateJobDto(

    @SerialName("solicitante_id")
    val requesterId: String,

    @SerialName("category_id")
    val categoryId: String,

    val title: String,
    val description: String,
    val budget: Double,
)