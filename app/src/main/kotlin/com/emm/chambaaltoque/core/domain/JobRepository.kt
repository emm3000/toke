package com.emm.chambaaltoque.core.domain

import kotlinx.coroutines.flow.Flow

interface JobRepository {

    suspend fun createJob(job: CreateJob)

    val jobs: Flow<List<Job>>
}