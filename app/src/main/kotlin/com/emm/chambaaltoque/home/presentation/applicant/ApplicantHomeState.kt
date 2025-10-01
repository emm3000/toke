package com.emm.chambaaltoque.home.presentation.applicant

import com.emm.chambaaltoque.core.domain.Job

data class ApplicantHomeState(
    val pendingJobs: List<Job> = emptyList(),
    val inProgressJobs: List<Job> = emptyList(),
    val doneJobs: List<Job> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
)