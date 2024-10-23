package com.project.myperiod

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.Serializable

@Serializable
data class City(val id: Int, val name: String)

class SupabaseData {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://ydlptuukgkqmlfhywmse.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlkbHB0dXVrZ2txbWxmaHl3bXNlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjk1MzAyOTcsImV4cCI6MjA0NTEwNjI5N30.T-phORm3N8G-tEBMMS4bXDFf1YYrZCn9r0Ai68Gv5hA"
    ) {
        install(Auth)
        install(Postgrest) {
            defaultSchema = "public" // default: "public"
            propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME // default: PropertyConversionMethod.CAMEL_CASE_TO_SNAKE_CASE
        }
    }


    suspend fun getCityNames(): City {
        val response = supabase
            .from("city")
            .select(columns = Columns.list("id, name"))
            .decodeSingle<City>()
return response

    }
}
