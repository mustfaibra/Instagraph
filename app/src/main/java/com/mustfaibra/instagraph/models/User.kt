package com.mustfaibra.instagraph.models

data class User(
    val userId: Int,
    val name: String? = null,
    val userName: String,
    val email: String? = null,
    val profile: Int,
    val token: String? = null,
)
