package com.angelemv.android.pruebabbva.model.network

import retrofit2.Response
import retrofit2.http.GET

data class DogResponse(
    val message: String,
    val status: String
)

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<DogResponse>
}