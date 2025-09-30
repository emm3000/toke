package com.emm.chambaaltoque.postjob.domain

data class CreateJob(
    val requesterId: String,
    val categoryId: String,
    val title: String,
    val description: String,
    val budget: Double,
    val photo: String,
)