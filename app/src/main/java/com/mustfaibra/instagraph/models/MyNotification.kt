package com.mustfaibra.instagraph.models

import com.mustfaibra.instagraph.sealed.NotificationType

data class MyNotification(
    val id: Int,
    val type: NotificationType,
    val time: Long,
)
