package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MessageApiService {


    @GET("messages")
    suspend fun getMessages(@Query("chatRoomId") chatRoomId: Int?): Response<List<MessageModel>>


    @FormUrlEncoded
    @POST("messages")
    suspend fun sendMessage(
        @Field("chatRoomId") chatRoomId: Int?,
        @Field("messageText") messageText:String?,
        @Field("sender") sender: String?,
        @Field("receiver") receiver: String?,
        @Field("time") time:String?
    ): Response<MessageModel>


}

object MessageApi {
    val retrofitService: MessageApiService by lazy {
        retrofit.create(MessageApiService::class.java)
    }
}