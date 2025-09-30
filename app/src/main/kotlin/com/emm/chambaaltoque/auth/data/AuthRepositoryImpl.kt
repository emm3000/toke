package com.emm.chambaaltoque.auth.data

import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.emm.chambaaltoque.auth.domain.WorkerRegister
import com.emm.chambaaltoque.core.DispatcherProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

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

    override suspend fun register(workerRegister: WorkerRegister) = withContext(io) {
        client.auth.signInWith(Email) {
            this.email = workerRegister.email
            this.password = workerRegister.password
            data = buildJsonObject {
                put("name", workerRegister.name)
                put("dni", workerRegister.dni)
                put("birth", workerRegister.birth)
                put("dni_path", workerRegister.dniPath)
                put("selfie_path", workerRegister.selfiePath)
                put("phone", workerRegister.phone)
                put("otp", workerRegister.otp)
                put("city", workerRegister.city)
                put("district", workerRegister.district)
                put("skills", workerRegister.skills)
                put("email", workerRegister.email)
                put("password", workerRegister.password)
                put("phone", workerRegister.phone)
                put("otp", workerRegister.otp)
                put("city", workerRegister.city)
                put("district", workerRegister.district)
                put("skills", workerRegister.skills)
            }
        }
    }

    override suspend fun registerWorker(phone: String, name: String, email: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = phone
            data = buildJsonObject {
                put("name", name)
                put("phone", phone)
                put("email", email)
            }
        }
    }

    override suspend fun currentUserId(): String {
        return client.auth.currentUserOrNull()?.id ?: throw IllegalStateException("User not logged in")
    }
}