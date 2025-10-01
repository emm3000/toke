@file:OptIn(FlowPreview::class)

package com.emm.chambaaltoque.auth.presentation.login

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

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    var state by mutableStateOf(LoginState())
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

    fun onAction(event: LoginAction) {
        when (event) {
            is LoginAction.EmailChanged -> state = state.copy(email = event.email)
            is LoginAction.PasswordChanged -> state = state.copy(password = event.password)
            is LoginAction.Login -> login()
            LoginAction.DismissError -> state = state.copy(error = null)
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