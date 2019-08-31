package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {

    @GET("users")
    suspend fun getUsers(@Query("username") username: String?,
                 @Query("password") password: String?): Response<List<User>>


    @FormUrlEncoded
    @POST("users")
    suspend fun createUser(@Field("username") username: String?,
                   @Field("password") password: String?): Response<User>


}

object UserApi {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}