@file:OptIn(FlowPreview::class)

package com.emm.chambaaltoque.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.login.domain.AuthRepository
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

    fun onAction(action: ApplicationRegisterAction) {
        when (action) {
            is ApplicationRegisterAction.PhoneChange -> onPhoneChange(action.phone)
            is ApplicationRegisterAction.FullNameChange -> onFullNameChange(action.fullName)
            is ApplicationRegisterAction.EmailChange -> onEmailChange(action.email)
            is ApplicationRegisterAction.AcceptedTermsChange -> onAcceptedTermsChange(action.acceptedTerms)
            ApplicationRegisterAction.Register -> onRegister()
            ApplicationRegisterAction.DismissDialog -> clearError()
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
                repository.registerWorker(
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