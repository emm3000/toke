package com.emm.chambaaltoque.auth.domain

interface AuthRepository {

    suspend fun login(email: String, password: String)

    suspend fun register(workerRegister: WorkerRegister)

    suspend fun registerWorker(
        phone: String,
        name: String,
        email: String,
    )
}