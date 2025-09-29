package com.emm.chambaaltoque.core.di

import android.content.Context
import com.emm.chambaaltoque.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
    single<SupabaseClient> {
        provideSupabaseClient(context = androidApplication(), json = get())
    }
}

private fun provideSupabaseClient(
    context: Context,
    json: Json,
): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = context.getString(R.string.supabase_key),
        supabaseKey = context.getString(R.string.supabase_key),
    ) {
        defaultSerializer = KotlinXSerializer(json)
        install(Auth)
    }
}