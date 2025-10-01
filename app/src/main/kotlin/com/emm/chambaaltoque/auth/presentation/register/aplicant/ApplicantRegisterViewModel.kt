@file:OptIn(FlowPreview::class)

package com.emm.chambaaltoque.auth.presentation.register.aplicant

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ApplicantRegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    var state by mutableStateOf(ApplicantRegisterState())
        private set

    init {
        snapshotFlow { state }
            .debounce(350L)
            .distinctUntilChanged()
            .onEach {
                state = state.copy(isFieldValid = isFieldValid())
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ApplicantRegisterAction) {
        when (action) {
            is ApplicantRegisterAction.PhoneChange -> onPhoneChange(action.phone)
            is ApplicantRegisterAction.FullNameChange -> onFullNameChange(action.fullName)
            is ApplicantRegisterAction.EmailChange -> onEmailChange(action.email)
            is ApplicantRegisterAction.AcceptedTermsChange -> onAcceptedTermsChange(action.acceptedTerms)
            ApplicantRegisterAction.Register -> onRegister()
            ApplicantRegisterAction.DismissDialog -> clearError()
        }
    }

    private fun clearError() {
        state = state.copy(error = null)
    }

    private fun onPhoneChange(phone: String) {
        state = state.copy(phone = phone)
    }

    private fun onFullNameChange(fullName: String) {
        state = state.copy(fullName = fullName)
    }

    private fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    private fun onAcceptedTermsChange(acceptedTerms: Boolean) {
        state = state.copy(acceptedTerms = acceptedTerms)
    }

    private fun onRegister() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                repository.registerApplicant(
                    phone = state.phone,
                    name = state.fullName,
                    email = state.email,
                )
                state = state.copy(isSuccessful = true)
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                state = state.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun isFieldValid(): Boolean {
        return state.phone.length in 7..12
                && state.fullName.trim().isNotEmpty()
                && state.acceptedTerms
                && state.email.trim().isNotEmpty()
    }
}