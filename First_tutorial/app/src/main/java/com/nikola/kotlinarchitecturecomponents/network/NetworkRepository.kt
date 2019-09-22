package com.nikola.kotlinarchitecturecomponents.network

import com.nikola.kotlinarchitecturecomponents.network.models.ChatRoom
import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import com.nikola.kotlinarchitecturecomponents.network.models.ProfileContacts
import com.nikola.kotlinarchitecturecomponents.network.models.User
import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import retrofit2.Response

class NetworkRepository {

    suspend fun getUsers(username: String?, password: String?): Response<List<User>> =
        UserApi.retrofitService.getUsers(username, password)

    suspend fun createUser(username: String?, password: String?): Response<User> =
        UserApi.retrofitService.createUser(username, password)

    suspend fun getProfiles(username: String?): Response<List<Profile>> =
        ProfileApi.retrofitService.getProfiles(username)

    suspend fun getProfilesByNickname(nickname: String?): Response<List<Profile>> =
        ProfileApi.retrofitService.getProfilesByNickname(nickname)

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

    suspend fun addContact(id: Int?, contacts: ProfileContacts): Response<Profile> =
        ProfileApi.retrofitService.addContact(id, contacts)

    suspend fun getChatRoom(memberOne: String?, memberTwo: String?): Response<List<ChatRoom>> =
        ChatRoomApi.retrofitService.getChatRoom(memberOne, memberTwo)

    suspend fun createChatRoom(memberOne: String?, memberTwo: String?): Response<ChatRoom> =
        ChatRoomApi.retrofitService.createChatRoom(memberOne, memberTwo)

    suspend fun getMessages(chatRoomId:Int?): Response<List<MessageModel>> =
        MessageApi.retrofitService.getMessages(chatRoomId)

    suspend fun sendMessage(chatRoomId: Int?, messageText:String?, sender: String?, receiver: String?, time:String?): Response<MessageModel> =
        MessageApi.retrofitService.sendMessage(chatRoomId, messageText, sender, receiver, time)

}
