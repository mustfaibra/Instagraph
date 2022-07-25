package com.mustfaibra.instagraph.data.fake

import com.mustfaibra.instagraph.models.Featured
import com.mustfaibra.instagraph.models.MyNotification
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse

interface FakeServices {
    suspend fun signInUser(email: String, password: String): DataResponse<User>

    suspend fun getFakePosts(): DataResponse<List<Post>>

    suspend fun getFakeStories(): DataResponse<List<Story>>

    suspend fun getFakeFeaturedStories(): DataResponse<List<Featured>>

    suspend fun findStoryById(storyId: Int): DataResponse<Story?>

    suspend fun findPostById(postId: Int): DataResponse<Post?>

    suspend fun getFakeUsers(userName: String): DataResponse<List<User>>

    suspend fun findUserByUsername(userName: String): DataResponse<User?>

    suspend fun getFakeNotifications(): DataResponse<List<MyNotification>>
}