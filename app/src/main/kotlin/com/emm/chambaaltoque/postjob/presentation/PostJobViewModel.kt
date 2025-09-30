@file:OptIn(FlowPreview::class)

package com.emm.chambaaltoque.postjob.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.emm.chambaaltoque.home.domain.JobRepository
import com.emm.chambaaltoque.postjob.domain.CreateJob
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostJobViewModel(
    private val jobRepository: JobRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(PostJobState())
        private set

    init {
        snapshotFlow { state }
            .debounce(333L)
            .distinctUntilChanged()
            .onEach {
                val isValid = it.title.isNotBlank() && it.description.isNotBlank() && it.budget >= 1.0
                state = it.copy(isValid = isValid)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: PostJobAction) {
        when (action) {
            is PostJobAction.TitleChanged -> state = state.copy(title = action.title)
            is PostJobAction.DescriptionChanged -> state = state.copy(description = action.description)
            is PostJobAction.BudgetChanged -> state = state.copy(budget = action.budget)
            is PostJobAction.Publish -> publish()
            PostJobAction.DismissError -> state = state.copy(error = null)
        }
    }

    private fun publish() = viewModelScope.launch {
        try {
            state = state.copy(isLoading = true)
            val userId: String = authRepository.currentUserId()
            val createJob: CreateJob = createJob(userId)
            jobRepository.createJob(createJob)
            state = state.copy(isSuccessful = true)
        } catch (throwable: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(throwable)
            state = state.copy(error = throwable.message, isLoading = false)
        }
    }

    private fun createJob(userId: String): CreateJob = CreateJob(
        requesterId = userId,
        categoryId = "123e4567-e89b-12d3-a456-426614174000",
        title = state.title,
        description = state.description,
        budget = state.budget,
        photo = "",
    )
}