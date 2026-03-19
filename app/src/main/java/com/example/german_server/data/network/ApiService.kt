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
import retrofit2.http.HTTP

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
import com.example.german_server.data.network.models.AvatarServerUploadResponse
import com.example.german_server.data.network.models.SupportChatMessageResponse
import com.example.german_server.data.network.models.MarkReadSupportMessageRequest
import com.example.german_server.data.network.models.SendSupportChatMessageRequest
import com.example.german_server.data.network.models.DeleteMessageRequest
import com.example.german_server.data.network.models.SupportChatMessageResponseDTO
import com.example.german_server.data.network.models.SenderUser


interface ApiService {

    @POST("api/register/")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>


    @GET("api/profile/")
    suspend fun getProfile(@Query("email") email: String? = null,
                           @Header("Authorization") token: String? = null): Response<ProfileResponse>

    @GET("api/get_sender/")
    suspend fun getSender(@Query("uid") uid: String,
                           @Header("Authorization") token: String? = null): Response<SenderUser>

    @GET("api/get_all_senders/")
    suspend fun getAllSenders(@Header("Authorization") token: String? = null): Response<List<SenderUser>>

    @GET("api/get_all_admin/")
    suspend fun getAllAdmin(@Header("Authorization") token: String? = null): Response<List<SenderUser>>

    @GET("api/rating/")
    suspend fun getRating(@Header("Authorization") token: String? = null): Response<LeaderboardResponse>
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
        @Part image: MultipartBody.Part,
        @Part("uid") uid: RequestBody,
        ): Response<AvatarServerUploadResponse>

    // Отправка сообщения
    @POST("support_chat/send_message/")
    suspend fun sendMessage(
        @Body request: SendSupportChatMessageRequest,
        @Header("Authorization") token: String? = null
    ): Response<SupportChatMessageResponseDTO>


    // Получение новых сообщений после последнего server_id
    @GET("support_chat/check_new_message/")
    suspend fun syncMessages(
        @Query("last_message_id") lastMessageId: Long,
        @Header("Authorization") token: String? = null,
    ): Response<List<SupportChatMessageResponse>>

    // Пометка сообщений прочитанными
    @POST("support_chat/readed_message/")
    suspend fun markMessagesRead(
        @Body request: MarkReadSupportMessageRequest,
        @Header("Authorization") token: String? = null,
        ): Response<Unit>

    @HTTP(method = "DELETE", path = "support_chat/delete_message/", hasBody = true)
    suspend fun deleteMessage(
        @Body request: DeleteMessageRequest,
        @Header("Authorization") token: String? = null
    ): Response<Unit>

}



