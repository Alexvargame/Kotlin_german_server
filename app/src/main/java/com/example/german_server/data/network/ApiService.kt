package com.example.german_server.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.Response

import okhttp3.MultipartBody
import okhttp3.RequestBody

import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncRequest
import com.example.german_server.data.network.models.SyncResponse
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.SyncProgressRequest
import com.example.german_server.data.network.models.SyncProgressResponse
import com.example.german_server.data.network.models.LeaderboardResponse
import com.example.german_server.data.network.models.AvatarUploadRequest
import com.example.german_server.data.network.models.AvatarUploadResponse
import com.example.german_server.data.network.models.AvatarGalleryUploadResponse


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

    @POST("api/upload-avatar/")
    suspend fun uploadAvatar(@Body request: AvatarUploadRequest): Response<AvatarUploadResponse>


    @Multipart
    @POST("api/upload-gallery-avatar/")
    suspend fun uploadGalleryAvatar(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part,
        @Part("uid") serverUid: RequestBody,
        @Part("avatar_last_changed") avatarLastChanged: RequestBody,
       // @Part("loginToken") token: RequestBody
    ): Response<AvatarGalleryUploadResponse>



}
//
//data class EmailRequest(
//    val email: String
//)



