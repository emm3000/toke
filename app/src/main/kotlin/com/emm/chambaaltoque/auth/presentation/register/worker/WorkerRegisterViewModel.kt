package com.emm.chambaaltoque.auth.presentation.register.worker

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.emm.chambaaltoque.auth.domain.WorkerRegister
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

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

class WorkerRegisterViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(WorkerRegisterState())
        private set

    fun onAction(action: WorkerRegisterAction) {
        when (action) {
            is WorkerRegisterAction.SetBirth -> state = state.copy(birth = action.birth)
            is WorkerRegisterAction.SetCity -> state = state.copy(city = action.city)
            is WorkerRegisterAction.SetDistrict -> state = state.copy(district = action.district)
            is WorkerRegisterAction.SetDni -> state = state.copy(dni = action.dni)
            is WorkerRegisterAction.SetDniPhoto -> state = state.copy(dniPhoto = action.dniPhoto)
            is WorkerRegisterAction.SetEmail -> state = state.copy(email = action.email)
            is WorkerRegisterAction.SetFullName -> state = state.copy(fullName = action.fullName)
            is WorkerRegisterAction.SetPassword -> state = state.copy(password = action.password)
            is WorkerRegisterAction.SetPhone -> state = state.copy(phone = action.phone)
            is WorkerRegisterAction.SetSelfie -> state = state.copy(selfie = action.selfie)
            is WorkerRegisterAction.SetSkills -> state = state.copy(skills = action.skills)
            is WorkerRegisterAction.SetTermsAccepted -> state = state.copy(isTermsAccepted = action.isTermsAccepted)
            WorkerRegisterAction.Submit -> register()
        }
    }

    private fun register() = viewModelScope.launch {
        try {
            state = state.copy(isLoading = true)
//            WorkerRegister(
//                fullName = state.fullName,
//                dni = state.dni,
//                dniPhoto = state.dniPhoto,
//                selfie = state.selfie,
//                birth = state.birth,
//                phone = state.phone,
//                email = state.email,
//                password = state.password,
//                city = state.city,
//                district = state.district,
//                skills = state.skills,
//                isTermsAccepted = state.isTermsAccepted,
//            )
//            authRepository.register()
            state = state.copy(isSuccessful = true)
        } catch (throwable: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(throwable)
            state = state.copy(isLoading = false, error = throwable.message)
        }
    }
}