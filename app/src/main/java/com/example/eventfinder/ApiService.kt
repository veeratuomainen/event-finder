package com.example.eventfinder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events?apikey=HBGGZJUUFXnnJ1cbj4tDGWQXUW0n8mvA&locale=*&size=20")
    suspend fun getEventResponse(@Query("city") city: String): EventResponse
}

object RetrofitInstance {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://app.ticketmaster.com/discovery/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
