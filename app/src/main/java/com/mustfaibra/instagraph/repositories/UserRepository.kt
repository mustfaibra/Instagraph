package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) {
    suspend fun signInUser(email: String, password: String): DataResponse<User> {
        return fakeServicesImpl.signInUser(email = email, password = password)
    }

    suspend fun getFakeFeaturedStories() =
        fakeServicesImpl.getFakeFeaturedStories()
}