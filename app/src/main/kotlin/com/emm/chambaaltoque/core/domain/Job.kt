package com.emm.chambaaltoque.core.domain

import java.time.LocalDateTime

data class Job(
    val id: String,
    val requesterId: String,
    val categoryId: String,
    val title: String,
    val description: String,
    val budget: Double,
    val status: JobStatus,
    val createdAt: LocalDateTime,
)