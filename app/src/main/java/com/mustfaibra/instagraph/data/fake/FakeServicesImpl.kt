package com.mustfaibra.instagraph.data.fake

import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.models.MyNotification
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.models.PostReacts
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import com.mustfaibra.instagraph.sealed.NotificationType
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

class FakeServicesImpl : FakeServices {
    private val users = listOf(
        User(
            userId = 1,
            userName = "its_camilla",
            name = "Camilla Capello",
            profile = R.drawable.camilla,
        ),
        User(
            userId = 2,
            userName = "the_weeknd",
            name = "The Weeknd",
            profile = R.drawable.weeknd,
        ),
        User(
            userId = 3,
            userName = "elon_musk",
            name = "Elon Musk",
            profile = R.drawable.elon_musk,
        ),
        User(
            userId = 4,
            userName = "the_rock_10",
            name = "The Rock",
            profile = R.drawable.the_rock,
        ),
    )
    private val posts = mutableListOf<Post>()
    private val stories = mutableListOf<Story>()
    private val images = listOf(
        R.drawable.cn_tower,
        R.drawable.effiel_tower,
        R.drawable.eiffel_tower_night,
        R.drawable.explorer_girl_with_map,
        R.drawable.girl_with_landscape,
        R.drawable.james_web_space_image_1,
        R.drawable.mountain_coast,
        R.drawable.oregon_coast,
        R.drawable.pacific_ocean_coast,
        R.drawable.space_explodusions,
        R.drawable.wine_cups,
    )

    init {
        /** Creating the fake posts */
        images.forEachIndexed { index, image ->
            for (user in users) {
                val currentIndex = (index * users.size).plus(user.userId)
                posts.add(
                    Post(
                        id = currentIndex,
                        user = user,
                        location = listOf(
                            "Tokyo, Japan",
                            "CA, USA",
                            "Khartoum, Sudan",
                            "Madrid, Spain",
                            "Berlin, German",
                        ).random(),
                        images = images.shuffled().take((Random.nextInt(1, 5))),
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
                                userName = "ed_sheeran",
                                profile = R.drawable.ed_sheeran,
                            ),
                            othersCount = (Math.random() * 100000).toInt(),
                        ),
                    )
                )
            }
        }
        /** Creating the fake stories */
        stories.addAll(
            users.map {
                Story(
                    id = it.userId,
                    url = images[Random.nextInt(0, images.size)],
                    user = it
                )
            }
        )
    }

    override suspend fun getFakeNotifications(): DataResponse<List<MyNotification>> {
        delay(3000)
        val milliSecondsInDay = 24 * 60 * 60 * 1000
        val milliSecondsInHour = 60 * 60 * 1000
        val todayDateAsLong = Date().time
        val yesterdayDateAsLong = Date().time.minus(milliSecondsInDay)

        /** now we return the fake notifications for today and yesterday */
        return DataResponse.Success(
            data = listOf(
                MyNotification(
                    id = 1,
                    type = NotificationType.FollowNotification(
                        user = users[0],
                        followed = true,
                    ),
                    time = todayDateAsLong
                ),
                MyNotification(
                    id = 3,
                    type = NotificationType.ReactsNotification(
                        postId = posts[1].id,
                        postCoverUrl = posts[1].images.first(),
                        postReacts = PostReacts(
                            recentUser = users[2],
                            othersCount = Random.nextInt(100, 12000)
                        ),
                    ),
                    time = todayDateAsLong.minus(2 * milliSecondsInHour)
                ),
                MyNotification(
                    id = 4,
                    type = NotificationType.MentionNotification(
                        user = users[3],
                        postId = posts[1].id,
                        commentId = 1,
                        comment = "Exactly! 🤩"
                    ),
                    time = todayDateAsLong.minus(3 * milliSecondsInHour)
                ),

                MyNotification(
                    id = 5,
                    type = NotificationType.FollowNotification(
                        user = users[3],
                        followed = false,
                    ),
                    time = yesterdayDateAsLong
                ),
                MyNotification(
                    id = 7,
                    type = NotificationType.ReactsNotification(
                        postId = posts[2].id,
                        postCoverUrl = posts[2].images.first(),
                        postReacts = PostReacts(
                            recentUser = users[2],
                            othersCount = Random.nextInt(100, 12000)
                        ),
                    ),
                    time = yesterdayDateAsLong.minus(2 * milliSecondsInHour)
                ),
                MyNotification(
                    id = 8,
                    type = NotificationType.MentionNotification(
                        user = users[1],
                        postId = posts[2].id,
                        commentId = 2,
                        comment = "Thank you man ❤️"
                    ),
                    time = yesterdayDateAsLong.minus(3 * milliSecondsInHour)
                ),
            )
        )
    }

    override suspend fun getFakePosts(): DataResponse<List<Post>> {
        return DataResponse.Success(data = posts)
    }

    override suspend fun getFakeFollowers(): DataResponse<List<User>> {
        return DataResponse.Success(data = users)
    }

    override suspend fun getFakeStories(): DataResponse<List<Story>> {
        return DataResponse.Success(data = stories)
    }

    override suspend fun findStoryById(storyId: Int): DataResponse<Story?> {
        return DataResponse.Success(data = stories.find { it.id == storyId })
    }

    override suspend fun findPostById(postId: Int): DataResponse<Post?> {
        return DataResponse.Success(data = posts.find { it.id == postId })
    }

    override suspend fun getFakeUsers(userName: String): DataResponse<List<User>> {
        return DataResponse.Success(data = users)
    }

    override suspend fun findUserByUsername(userName: String): DataResponse<User?> {
        return DataResponse.Success(data = users.find { it.userName == userName })
    }
}