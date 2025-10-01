package com.emm.chambaaltoque.home.presentation.worker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.core.domain.Job
import com.emm.chambaaltoque.core.domain.JobRepository
import com.emm.chambaaltoque.core.domain.JobStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WorkerHomeViewModel(
    jobRepository: JobRepository,
) : ViewModel() {

    val state: StateFlow<WorkerHomeState> = jobRepository.jobs
        .map(::mapToState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WorkerHomeState()
        )

    private fun mapToState(jobs: List<Job>): WorkerHomeState {
        val pending: List<Job> = jobs.filter { job -> job.status == JobStatus.Pending }
        return WorkerHomeState(pendingJobs = pending)
    }
}