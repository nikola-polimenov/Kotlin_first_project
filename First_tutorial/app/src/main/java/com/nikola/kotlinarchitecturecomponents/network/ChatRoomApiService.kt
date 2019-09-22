package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.ChatRoom
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ChatRoomApiService {


    @GET("chatRooms")
    suspend fun getChatRoom(@Query("memberOne") memberOne: String?,
                            @Query("memberTwo") memberTwo: String?): Response<List<ChatRoom>>


    @FormUrlEncoded
    @POST("chatRooms")
    suspend fun createChatRoom(@Field("memberOne") memberOne: String?,
                               @Field("memberTwo") memberTwo: String?): Response<ChatRoom>



}

object ChatRoomApi {
    val retrofitService: ChatRoomApiService by lazy {
        retrofit.create(ChatRoomApiService::class.java)
    }
}