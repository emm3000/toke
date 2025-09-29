package com.emm.chambaaltoque.auth.presentation.register.aplicant

sealed interface ApplicationRegisterAction {

    class PhoneChange(val phone: String) : ApplicationRegisterAction

    class FullNameChange(val fullName: String) : ApplicationRegisterAction

    class EmailChange(val email: String) : ApplicationRegisterAction

    class AcceptedTermsChange(val acceptedTerms: Boolean) : ApplicationRegisterAction

    object Register : ApplicationRegisterAction

    object DismissDialog : ApplicationRegisterAction
}