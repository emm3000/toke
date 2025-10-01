package com.emm.chambaaltoque.auth.data

import com.emm.chambaaltoque.auth.domain.AuthRepository
import com.emm.chambaaltoque.auth.domain.UserType
import com.emm.chambaaltoque.auth.domain.WorkerRegister
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.storage.FileUploadResponse
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

class AuthRepositoryImpl(
    private val client: SupabaseClient,
) : AuthRepository {

    override suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        val metadata: JsonObject = client.auth.currentUserOrNull()?.userMetadata ?: throw IllegalStateException("User not logged in")

        val content: String = metadata["type"]?.jsonPrimitive?.content.orEmpty()
        return@withContext UserType.valueOf(content)
    }

    override suspend fun registerWorker(workerRegister: WorkerRegister) = withContext(Dispatchers.IO) {

        val bucket = client.storage.from("photos")
        val upload: FileUploadResponse = bucket.upload(
            path = "${workerRegister.email}-dni.jpg",
            data = workerRegister.dniPhoto.byteArray
        ) {
            upsert = true
        }
        val upload1 = bucket.upload(
            path = "${workerRegister.email}-selfie.jpg",
            data = workerRegister.selfie.byteArray
        ) {
            upsert = true
        }

        client.auth.signUpWith(Email) {
            this.email = workerRegister.email
            this.password = workerRegister.password
            data = buildJsonObject {
                put("full", workerRegister.fullName)
                put("dni", workerRegister.dni)
                put("birth", workerRegister.birth)
                put("phone", workerRegister.phone)
                put("city", workerRegister.city)
                put("district", workerRegister.district)
                put("skills", workerRegister.skills)
                put("email", workerRegister.email)
                put("type", UserType.Worker.name)
                put("dniPhoto", upload.path)
                put("selfie", upload1.path)
            }
        }
        Unit
    }

    override suspend fun registerApplicant(phone: String, name: String, email: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = phone
            data = buildJsonObject {
                put("name", name)
                put("phone", phone)
                put("email", email)
                put("type", UserType.Applicant.name)
            }
        }
    }

    override suspend fun currentUserId(): String {
        return client.auth.currentUserOrNull()?.id ?: throw IllegalStateException("User not logged in")
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        client.auth.signOut()
    }
}