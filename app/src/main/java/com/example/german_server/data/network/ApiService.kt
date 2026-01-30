package com.example.german_server.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.Response
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncRequest
import com.example.german_server.data.network.models.SyncResponse
import com.example.german_server.data.network.models.ResendVerificationRequest

interface ApiService {

    @POST("api/register/")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/verify-email/")
    suspend fun resendVerificationEmail(@Body emailRequest: EmailRequest): Response<Unit>

    @GET("api/profile/")
    suspend fun getProfile(): Response<ProfileResponse>

    @POST("api/sync-user/")
    suspend fun syncUser(@Body request: SyncRequest): Response<SyncResponse>

    @POST("api/resend-verification/")
    suspend fun resendVerification(@Body request: ResendVerificationRequest): Response<Unit>

}


data class EmailRequest(
    val email: String
)
data class EmailStatusResponse(
    val email_verified: Boolean
)



