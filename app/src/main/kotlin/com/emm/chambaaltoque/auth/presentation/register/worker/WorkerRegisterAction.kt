package com.emm.chambaaltoque.auth.presentation.register.worker

import android.net.Uri

sealed interface WorkerRegisterAction {

    data class SetFullName(val fullName: String) : WorkerRegisterAction

    data class SetDni(val dni: String) : WorkerRegisterAction

    data class SetDniPhoto(val dniPhoto: Uri) : WorkerRegisterAction

    data class SetSelfie(val selfie: Uri) : WorkerRegisterAction

    data class SetBirth(val birth: String) : WorkerRegisterAction

    data class SetPhone(val phone: String) : WorkerRegisterAction

    data class SetEmail(val email: String) : WorkerRegisterAction

    data class SetPassword(val password: String) : WorkerRegisterAction

    data class SetCity(val city: String) : WorkerRegisterAction

    data class SetDistrict(val district: String) : WorkerRegisterAction

    data class SetSkills(val skills: String) : WorkerRegisterAction

    data class SetTermsAccepted(val isTermsAccepted: Boolean) : WorkerRegisterAction

    object Submit : WorkerRegisterAction
}