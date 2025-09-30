package com.emm.chambaaltoque.postjob.data

import com.emm.chambaaltoque.postjob.domain.CreateJob
import com.emm.chambaaltoque.postjob.domain.JobRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JobRepositoryImpl(private val client: SupabaseClient) : JobRepository {

    override suspend fun createJob(job: CreateJob) = withContext(Dispatchers.IO) {
        val createJobDto = CreateJobDto(
            requesterId = job.requesterId,
            categoryId = job.categoryId,
            title = job.title,
            description = job.description,
            budget = job.budget,
        )
        client.from("jobs").insert(createJobDto)
        Unit
    }
}