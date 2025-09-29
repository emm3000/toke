package com.emm.chambaaltoque.login.domain

interface AuthRepository {

    suspend fun login(email: String, password: String)

    suspend fun register(email: String, password: String)
}