package com.emm.chambaaltoque.core.domain

import com.emm.chambaaltoque.postjob.domain.CreateJob
import kotlinx.coroutines.flow.Flow

interface JobRepository {

    suspend fun createJob(job: CreateJob)

    val jobs: Flow<List<Job>>
}