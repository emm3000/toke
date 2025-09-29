package com.emm.chambaaltoque.login.presentation

data class ApplicantRegisterState(
    val phone: String = "",
    val fullName: String = "",
    val email: String = "",
    val acceptedTerms: Boolean = false,
    val isFieldValid: Boolean = false,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)