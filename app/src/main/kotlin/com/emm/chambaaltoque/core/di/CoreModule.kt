package com.emm.chambaaltoque.core.di

import com.emm.chambaaltoque.core.DispatcherProvider
import com.emm.chambaaltoque.core.RealDispatcherProvider
import io.github.jan.supabase.SupabaseClient
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

    singleOf(::RealDispatcherProvider) bind DispatcherProvider::class
}