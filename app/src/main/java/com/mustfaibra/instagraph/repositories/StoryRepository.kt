package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.sealed.DataResponse
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) {
    suspend fun getStories(): DataResponse<List<Story>> {
        return fakeServicesImpl.getFakeStories()
    }
}