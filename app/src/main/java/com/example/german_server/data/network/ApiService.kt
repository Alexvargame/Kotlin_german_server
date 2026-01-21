package com.example.german_server.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse

interface ApiService {

    @POST("api/register/")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/verify-email/")
    suspend fun resendVerificationEmail(@Body emailRequest: EmailRequest): Response<Unit>

   // @POST("api/check-email-status/") // <- этот URL должен быть на сервере
   // suspend fun checkEmailStatus(@Body request: EmailRequest): Response<EmailStatusResponse>
}


data class EmailRequest(
    val email: String
)
data class EmailStatusResponse(
    val email_verified: Boolean
)



