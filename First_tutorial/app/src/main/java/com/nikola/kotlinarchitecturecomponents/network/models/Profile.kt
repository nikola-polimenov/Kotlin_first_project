package com.nikola.kotlinarchitecturecomponents.network.models

class Profile(
    val username: String = "",
    var picture: String = "",
    var nickname: String = "",
    var contacts: ArrayList<String> = ArrayList(),
    var status: Int = 1
) {
    val id: Int = 0
}