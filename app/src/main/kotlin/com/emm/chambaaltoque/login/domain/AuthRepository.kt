package com.emm.chambaaltoque.login.domain

interface AuthRepository {

    suspend fun login(email: String, password: String)

    suspend fun register(workerRegister: WorkerRegister)

    suspend fun registerWorker(
        phone: String,
        name: String,
        email: String,
    )
}