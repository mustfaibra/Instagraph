package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class StoryRepository @Inject constructor() {
    suspend fun getStories() : DataResponse<List<Story>> {
        delay(3000)
        return DataResponse.Success(
            data = listOf(
                Story(
                    id = 1,
                    url = "",
                    user = User(
                        userId = 1,
                        name = "Camilla Capello",
                        email = "iamcamilla@gmail.com",
                    )
                ),
                Story(
                    id = 2,
                    url = "",
                    user = User(
                        userId = 2,
                        name = "The Weeknd",
                        email = "iamweeknd@gmail.com",
                    )
                ),

            )
        )
    }
}