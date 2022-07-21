package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.sealed.DataResponse
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) {


    suspend fun getHomePosts(): DataResponse<List<Post>> {
        /** Wait for 5 seconds to simulate the loading from server process */
//        delay(5000)
        return fakeServicesImpl.getFakePosts()
    }
}