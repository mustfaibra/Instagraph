package com.mustfaibra.instagraph.models

data class User(
    val userId: Int,
    val name: String? = null,
    val userName: String,
    val email: String? = null,
    val profile: Int,
    val bio: String? = null,
    var posts: List<Post> = mutableListOf(),
    val followers: List<User> = mutableListOf(),
    val following: List<User> = mutableListOf(),
    val token: String? = null,
)
