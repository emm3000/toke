package com.emm.chambaaltoque.home.presentation.applicant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.chambaaltoque.core.domain.Job
import com.emm.chambaaltoque.core.domain.JobRepository
import com.emm.chambaaltoque.core.domain.JobStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ApplicantHomeViewModel(
    jobRepository: JobRepository,
) : ViewModel() {

    val state: StateFlow<ApplicantHomeState> = jobRepository.jobs
        .map(::mapToState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ApplicantHomeState()
        )

    private fun mapToState(jobs: List<Job>): ApplicantHomeState {
        val pending: List<Job> = jobs.filter { job -> job.status == JobStatus.Pending }
        val inProgress: List<Job> = jobs.filter { job -> job.status == JobStatus.InProgress }
        val done: List<Job> = jobs.filter { job -> job.status == JobStatus.Completed }
        return ApplicantHomeState(
            pendingJobs = pending,
            inProgressJobs = inProgress,
            doneJobs = done,
        )
    }
}