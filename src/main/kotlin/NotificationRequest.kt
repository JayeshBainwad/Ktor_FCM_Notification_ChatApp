package com.jsb

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest(
    val otherUserFcmToken: String,
    val currentUserFcmToken: String,
    val senderId: String,
    val receiverId: String,
    val senderName: String,
    val receiverName: String,
    val content: String
)
