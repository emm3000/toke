package com.emm.chambaaltoque.postjob.domain

interface JobRepository {

    suspend fun createJob(job: CreateJob)
}