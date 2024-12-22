package com.angelemv.android.pruebabbva.model.network

import com.angelemv.android.pruebabbva.model.LoginRequest
import com.angelemv.android.pruebabbva.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
