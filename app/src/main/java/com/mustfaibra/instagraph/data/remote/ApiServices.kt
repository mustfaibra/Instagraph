package com.mustfaibra.instagraph.data.remote

import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse

interface ApiServices {

    /** A function to authorize an existed user */
    suspend fun signIn(
        email: String,
        password: String,
    ) : DataResponse<User>

    /** A function to create a new user */
    suspend fun signup(
        email: String,
        password: String,
        name: String,
    ) : DataResponse<User>
}