package com.emm.chambaaltoque.auth.domain

class WorkerRegister(
    val fullName: String,
    val dni: String,
    val dniPhoto: Photo,
    val selfie: Photo,
    val birth: String,
    val email: String,
    val password: String,
    val phone: String,
    val city: String,
    val district: String,
    val skills: String,
)

class Photo(val byteArray: ByteArray)