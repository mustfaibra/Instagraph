package com.mustfaibra.instagraph.models

data class Featured(
    val id: Int,
    val title: String,
    val user: User,
    val images: List<Int>,
)
