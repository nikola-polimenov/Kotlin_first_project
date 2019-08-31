package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.User
import retrofit2.Response

class NetworkRepository {

    suspend fun getUsers(username: String?, password: String?):Response<List<User>> = UserApi.retrofitService.getUsers(username, password)

    suspend fun createUser (username: String?, password: String?):Response<User> = UserApi.retrofitService.createUser(username, password)

}
