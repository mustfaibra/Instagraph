package com.mustfaibra.instagraph.models

data class Message(
    val id: Int,
    val body: String,
    val sender: User? = null,
    val receiver: User ? = null,
    val time: String = "1h",
)
