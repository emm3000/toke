package com.emm.chambaaltoque.home.presentation.worker

import com.emm.chambaaltoque.core.domain.Job

data class WorkerHomeState(
    val pendingJobs: List<Job> = emptyList(),
    val isLoading: Boolean = false,
)