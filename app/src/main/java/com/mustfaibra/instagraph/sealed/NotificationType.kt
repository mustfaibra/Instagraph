package com.mustfaibra.instagraph.sealed

import com.mustfaibra.instagraph.models.PostReacts
import com.mustfaibra.instagraph.models.User

sealed class NotificationType {
    class FollowNotification(val user: User, val followed: Boolean) : NotificationType()

    class ReactsNotification(
        val postId: Int,
        val postCoverUrl: Int,
        val postReacts: PostReacts
    ) : NotificationType()

    class MentionNotification(
        val user: User,
        val postId: Int,
        val commentId: Int,
        val comment: String,
    ) : NotificationType()
}
