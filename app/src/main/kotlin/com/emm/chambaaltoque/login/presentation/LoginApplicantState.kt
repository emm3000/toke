package com.emm.chambaaltoque.login.presentation

data class LoginApplicantState(
    val email: String = "",
    val password: String = "",
    val isValidFields: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)