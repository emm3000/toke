package com.emm.chambaaltoque.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isValidFields: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)