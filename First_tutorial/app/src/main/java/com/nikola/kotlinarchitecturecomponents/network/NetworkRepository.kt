package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import com.nikola.kotlinarchitecturecomponents.network.models.User
import retrofit2.Response

class NetworkRepository {

    suspend fun getUsers(username: String?, password: String?): Response<List<User>> =
        UserApi.retrofitService.getUsers(username, password)

    suspend fun createUser(username: String?, password: String?): Response<User> =
        UserApi.retrofitService.createUser(username, password)

    suspend fun getProfiles(username: String?): Response<List<Profile>> =
        ProfileApi.retrofitService.getProfiles(username)

    suspend fun createProfile(
        username: String?,
        picture: String?,
        nickname: String?,
        contacts: ArrayList<String?>,
        status: Int?
    ): Response<Profile> =
        ProfileApi.retrofitService.createProfile(username, picture, nickname, contacts, status)

    suspend fun editProfile(
        id: Int?,
        picture: String?,
        nickname: String?,
        status: Int?
    ): Response<Profile> =
        ProfileApi.retrofitService.editProfile(id, picture, nickname, status)

    suspend fun addContact(id: Int?, contacts: ArrayList<String?>): Response<Profile> =
        ProfileApi.retrofitService.addContact(id, contacts)
}
