package com.emm.chambaaltoque.login.data

import com.emm.chambaaltoque.core.DispatcherProvider
import com.emm.chambaaltoque.login.domain.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject

class AuthRepositoryImpl(
    dispatcherProvider: DispatcherProvider,
    private val client: SupabaseClient,
) : AuthRepository, DispatcherProvider by dispatcherProvider {

    override suspend fun login(email: String, password: String) = withContext(io) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun register(email: String, password: String) = withContext(io) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {

            }
        }
    }
}