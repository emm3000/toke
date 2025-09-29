package com.emm.chambaaltoque.login.presentation

sealed interface LoginApplicantAction {

    data class EmailChanged(val email: String) : LoginApplicantAction

    data class PasswordChanged(val password: String) : LoginApplicantAction

    object Login : LoginApplicantAction

    object DismissError : LoginApplicantAction
}