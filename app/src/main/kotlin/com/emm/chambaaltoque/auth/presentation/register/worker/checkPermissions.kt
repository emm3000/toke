package com.emm.chambaaltoque.auth.presentation.register.worker

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createTempFileUri(context: Context): Uri {

    val file: File = File.createTempFile("selfie_", ".jpg", context.cacheDir).apply {
        deleteOnExit()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}