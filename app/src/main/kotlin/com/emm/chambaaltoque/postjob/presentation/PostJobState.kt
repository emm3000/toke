package com.emm.chambaaltoque.postjob.presentation

data class PostJobState(
    val title: String = "",
    val description: String = "",
    val budget: Double = 0.0,
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)