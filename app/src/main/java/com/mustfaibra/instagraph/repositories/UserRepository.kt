package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepository @Inject constructor() {
    suspend fun signInUser(email: String, password: String): DataResponse<User> {
        /** Faking the process of authenticating actual user, wait for 4 seconds and then go */
//        delay(4000)
        return DataResponse.Success(
            data = User(
                userId = 1,
                name = "Mustafa Ibrahim",
                userName = "mustfaibra",
                email = email,
                profile = R.drawable.mustapha_profile,
                token = "fake-token-baby-haha"
            )
        )
    }
}