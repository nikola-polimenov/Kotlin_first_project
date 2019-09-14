package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ProfileApiService {

    @GET("profiles")
    suspend fun getProfiles(@Query("username") username: String?): Response<List<Profile>>

    @GET("profiles")
    suspend fun getProfilesByNickname(@Query("nickname") nickname: String?): Response<List<Profile>>

    @FormUrlEncoded
    @POST("profiles")
    suspend fun createProfile(@Field("username") username: String?,
                           @Field("picture") picture: String?,
                              @Field(value = "nickname") nickname: String?,
                              @Field(value = "contacts") contacts: ArrayList<String?>,
                              @Field(value = "status") status: Int?): Response<Profile>

    @FormUrlEncoded
    @PATCH("profiles/{id}")
    suspend fun editProfile(@Path("id") id:Int?,
                            @Field("picture") picture: String?,
                            @Field(value = "nickname") nickname: String?,
                            @Field(value = "status") status: Int?): Response<Profile>

    @FormUrlEncoded
    @PATCH("profiles/{id}")
    suspend fun addContact(@Path("id") id: Int?,
                           @Field("contacts") contacts: ArrayList<String?>): Response<Profile>

}

object ProfileApi {
    val retrofitService: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }
}