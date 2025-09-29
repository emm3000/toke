package com.emm.chambaaltoque.auth.presentation.login.applicant

sealed interface LoginApplicantAction {

    data class EmailChanged(val email: String) : LoginApplicantAction

    data class PasswordChanged(val password: String) : LoginApplicantAction

    object Login : LoginApplicantAction

    object DismissError : LoginApplicantAction
}