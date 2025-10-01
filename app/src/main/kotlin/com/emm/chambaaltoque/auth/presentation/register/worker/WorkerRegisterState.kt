package com.emm.chambaaltoque.auth.presentation.register.worker

import android.net.Uri

data class WorkerRegisterState(
    val fullName: String = "",
    val dni: String = "",
    val dniPhoto: Uri = Uri.EMPTY,
    val selfie: Uri = Uri.EMPTY,
    val birth: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val city: String = "",
    val district: String = "",
    val skills: String = "",
    val isTermsAccepted: Boolean = false,
    val isValidFields: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
)