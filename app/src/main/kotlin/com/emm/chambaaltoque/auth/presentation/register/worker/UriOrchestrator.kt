package com.emm.chambaaltoque.auth.presentation.register.worker

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream

class UriOrchestrator(private val context: Context) {

    fun createTempFileUri(): Uri {

        val currentTimeMillis: Long = System.currentTimeMillis()

        val file: File = File.createTempFile("${currentTimeMillis}_", ".jpg", context.cacheDir).apply {
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    fun byteArray(uri: Uri): ByteArray = context
        .contentResolver
        .openInputStream(uri)
        .use(::readFileToByteArray)
        ?: byteArrayOf()

    private fun readFileToByteArray(stream: InputStream?): ByteArray? = stream
        ?.buffered()
        ?.use(BufferedInputStream::readBytes)
}