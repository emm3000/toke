package com.emm.chambaaltoque.auth.presentation.login

import com.emm.chambaaltoque.auth.domain.UserType

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isValidFields: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val userType: UserType = UserType.Applicant,
    val error: String? = null,
)