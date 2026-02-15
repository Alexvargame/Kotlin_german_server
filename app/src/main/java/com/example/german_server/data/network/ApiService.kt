package com.example.german_server.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.Response
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncRequest
import com.example.german_server.data.network.models.SyncResponse
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.SyncProgressRequest
import com.example.german_server.data.network.models.SyncProgressResponse
import com.example.german_server.data.network.models.LeaderboardResponse

interface ApiService {

    @POST("api/register/")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>


    @GET("api/profile/")
    suspend fun getProfile(@Query("email") email: String? = null,
                           @Header("Authorization") token: String? = null): Response<ProfileResponse>

    @GET("api/rating/")
    suspend fun getRating(//@Query("email") email: String? = null,
                           @Header("Authorization") token: String? = null): Response<LeaderboardResponse>
    @POST("api/sync-user/")
    suspend fun syncUser(@Body request: SyncRequest): Response<SyncResponse>

    @POST("api/resend-verification/")
    suspend fun resendVerification(@Body request: ResendVerificationRequest): Response<Unit>


    @DELETE("api/delete/{uid}/")
    suspend fun deleteAccount(@Path("uid") uid: String): Response<Unit>

    @POST("api/sync-progress/")
    suspend fun syncProgress(@Body request: SyncProgressRequest): Response<SyncProgressResponse>

}

data class EmailRequest(
    val email: String
)



