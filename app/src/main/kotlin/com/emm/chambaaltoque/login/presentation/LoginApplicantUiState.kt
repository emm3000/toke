package com.emm.chambaaltoque.login.presentation

data class LoginApplicantUiState(
    val email: String = "",
    val password: String = "",
    val isValidFields: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)