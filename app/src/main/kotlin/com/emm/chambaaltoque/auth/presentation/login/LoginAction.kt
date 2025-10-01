package com.emm.chambaaltoque.auth.presentation.login

sealed interface LoginAction {

    data class EmailChanged(val email: String) : LoginAction

    data class PasswordChanged(val password: String) : LoginAction

    object Login : LoginAction

    object DismissError : LoginAction
}