package com.emm.chambaaltoque.auth.presentation.register.aplicant

sealed interface ApplicantRegisterAction {

    class PhoneChange(val phone: String) : ApplicantRegisterAction

    class FullNameChange(val fullName: String) : ApplicantRegisterAction

    class EmailChange(val email: String) : ApplicantRegisterAction

    class AcceptedTermsChange(val acceptedTerms: Boolean) : ApplicantRegisterAction

    object Register : ApplicantRegisterAction

    object DismissDialog : ApplicantRegisterAction
}