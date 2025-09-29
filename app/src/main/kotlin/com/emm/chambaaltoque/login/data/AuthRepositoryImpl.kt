package com.emm.chambaaltoque.login.data

import com.emm.chambaaltoque.login.domain.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(email: String, password: String) {
        TODO("Not yet implemented")
    }
}