package com.mustfaibra.instagraph.data.fake

import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse

interface FakeServices {
    suspend fun getFakePosts() : DataResponse<List<Post>>

    suspend fun getFakeFollowers() : DataResponse<List<User>>

    suspend fun getFakeStories() : DataResponse<List<Story>>

    suspend fun findStoryById(storyId: Int) : DataResponse<Story?>

    suspend fun findPostById(postId: Int) : DataResponse<Post?>

    suspend fun getFakeUsers(userName: String) : DataResponse<List<User>>

    suspend fun findUserByUsername(userName: String) : DataResponse<User?>
}