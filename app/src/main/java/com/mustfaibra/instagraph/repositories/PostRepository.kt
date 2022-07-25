package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.sealed.DataResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) {

    suspend fun getFakePosts(): DataResponse<List<Post>> {
        return fakeServicesImpl.getFakePosts()
    }
}