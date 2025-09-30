package com.emm.chambaaltoque.core.di

import android.content.Context
import com.emm.chambaaltoque.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

fun provideSupabaseClient(
    context: Context,
    json: Json,
): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = context.getString(R.string.supabase_url),
        supabaseKey = context.getString(R.string.supabase_key),
    ) {
        defaultSerializer = KotlinXSerializer(json)
        install(Auth)
        install(Postgrest)
        install(Realtime)
    }
}