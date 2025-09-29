@file:OptIn(FlowPreview::class)

package com.emm.chambaaltoque.auth.presentation.login.applicant

import android.util.Patterns
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

class LoginApplicantViewModel(private val repository: AuthRepository) : ViewModel() {

    var state by mutableStateOf(LoginApplicantState())
        private set

    init {
        snapshotFlow { state }
            .debounce(350L)
            .distinctUntilChanged()
            .onEach {
                state = state.copy(isValidFields = isFieldValid())
            }
            .launchIn(viewModelScope)
    }

    private fun isFieldValid(): Boolean {
        val matches: Boolean = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        val passwordMatches: Boolean = state.password.length >= 8
        return matches && passwordMatches
    }

    fun onAction(event: LoginApplicantAction) {
        when (event) {
            is LoginApplicantAction.EmailChanged -> state = state.copy(email = event.email)
            is LoginApplicantAction.PasswordChanged -> state = state.copy(password = event.password)
            is LoginApplicantAction.Login -> login()
            LoginApplicantAction.DismissError -> state = state.copy(error = null)
        }
    }

    private fun login() = viewModelScope.launch {
        try {
            state = state.copy(isLoading = true)
            repository.login(email = state.email, password = state.password)
            state = state.copy(isSuccessful = true)
        } catch (throwable: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(throwable)
            state = state.copy(error = throwable.message)
        }
    }
}