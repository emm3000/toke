package com.emm.chambaaltoque.auth.domain

interface AuthRepository {

    suspend fun login(email: String, password: String): UserType

    suspend fun registerWorker(workerRegister: WorkerRegister)

    suspend fun registerApplicant(
        phone: String,
        name: String,
        email: String,
    )

    suspend fun currentUserId(): String

    suspend fun logout()
}