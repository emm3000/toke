package com.emm.chambaaltoque.login.presentation

sealed interface LoginApplicantEvent {

    data class EmailChanged(val email: String) : LoginApplicantEvent

    data class PasswordChanged(val password: String) : LoginApplicantEvent

    object Login : LoginApplicantEvent

    object DismissError : LoginApplicantEvent
}