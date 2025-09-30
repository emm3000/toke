@file:OptIn(SupabaseExperimental::class)

package com.emm.chambaaltoque.home.data

import com.emm.chambaaltoque.home.domain.Job
import com.emm.chambaaltoque.home.domain.JobRepository
import com.emm.chambaaltoque.home.domain.JobStatus
import com.emm.chambaaltoque.postjob.data.CreateJobDto
import com.emm.chambaaltoque.postjob.domain.CreateJob
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class JobRepositoryImpl(private val client: SupabaseClient) : JobRepository {

    private val source: PostgrestQueryBuilder = client.from("jobs")

    override val jobs: Flow<List<Job>> = source.selectAsFlow(JobDto::id)
        .map(::toDomain)

    private fun toDomain(jobs: List<JobDto>): List<Job> = jobs.map(::dtoToDomain)

    private fun dtoToDomain(job: JobDto): Job = Job(
        id = job.id,
        requesterId = job.requesterId,
        categoryId = job.categoryId,
        title = job.title,
        description = job.description,
        budget = job.budget,
        status = JobStatus.fromKey(job.status),
        createdAt = LocalDateTime.parse(job.createdAt)
    )

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