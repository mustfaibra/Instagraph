package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.models.PostReacts
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostRepository @Inject constructor() {
    private val users = listOf(
        User(
            userId = 1,
            userName = "its_camilla",
            profile = R.drawable.camilla,
        ),
        User(
            userId = 2,
            userName = "the_weeknd",
            profile = R.drawable.weeknd,
        ),
        User(
            userId = 3,
            userName = "elon_musk",
            profile = R.drawable.elon_musk,
        ),
        User(
            userId = 4,
            userName = "the_rock_10",
            profile = R.drawable.the_rock,
        ),
    )

    suspend fun getHomePosts() : DataResponse<List<Post>> {
        /** Wait for 5 seconds to simulate the loading from server process */
//        delay(5000)
        return DataResponse.Success(
            data = users.map {
                Post(
                    id = it.userId,
                    user = it,
                    location = listOf(
                        "Tokyo, Japan",
                        "CA, USA",
                        "Khartoum, Sudan",
                        "Madrid, Spain",
                        "Berlin, German",
                    ).random(),
                    images = users.map { it1 -> it1.profile }.shuffled(),
                    caption = listOf(
                        "Good Morning ❤️",
                        "Good Evening 🤩✨",
                        "Do you feel that? 🤩❤️",
                        "New era, new me ! 🔥",
                        "On fire ! 🔥🔥🔥"
                    ).random(),
                    reacts = PostReacts(
                        recentUser = User(
                            userId = 5,
                            userName = "sas_eldin",
                            profile = R.drawable.ic_user,
                        ),
                        othersCount = (Math.random() * 100000).toInt(),
                    ),
                )
            }
        )
    }
}