package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class StoryRepository @Inject constructor() {
    suspend fun getStories() : DataResponse<List<Story>> {
        /** Wait for 3 seconds to simulate the loading from server process */
//        delay(3000)
        return DataResponse.Success(
            data = listOf(
                Story(
                    id = 1,
                    url = R.drawable.camilla,
                    user = User(
                        userId = 1,
                        name = "Camilla Capello",
                        userName = "its_camilla",
                        email = "iamcamilla@gmail.com",
                        profile = R.drawable.camilla,
                    )
                ),
                Story(
                    id = 2,
                    url = R.drawable.weeknd,
                    user = User(
                        userId = 2,
                        name = "The Weeknd",
                        userName = "the_weeknd",
                        email = "iamweeknd@gmail.com",
                        profile = R.drawable.weeknd,
                    )
                ),
                Story(
                    id = 3,
                    url = R.drawable.elon_musk,
                    user = User(
                        userId = 3,
                        name = "Elon Musk",
                        userName = "elon_musk",
                        email = "iamelienmusk@gmail.com",
                        profile = R.drawable.elon_musk,
                    )
                ),
                Story(
                    id = 4,
                    url = R.drawable.the_rock,
                    user = User(
                        userId = 4,
                        name = "Dwayne Johnson",
                        userName = "the_rock_10",
                        email = "therock@gmail.com",
                        profile = R.drawable.the_rock,
                    )
                ),

            )
        )
    }
}