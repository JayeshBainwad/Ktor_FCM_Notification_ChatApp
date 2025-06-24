package com.jsb.routes

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.jsb.NotificationRequest
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Route.notificationRoutes() {
    post("/send-notification") {
        try {
            val requestBody = call.receiveText()
            println("🟡 Raw JSON received: $requestBody")

            val request = Json.decodeFromString<NotificationRequest>(requestBody)
            println("🟢 Parsed NotificationRequest: $request")

            val message = Message.builder()
                .putData("senderId", request.senderId)
                .putData("receiverId", request.receiverId)
                .putData("senderName", request.senderName)
                .putData("receiverName", request.receiverName)
                .putData("content", request.content)
                .putData("otherUserFcmToken", request.otherUserFcmToken)
                .putData("currentUserFcmToken", request.currentUserFcmToken)
                .setToken(request.otherUserFcmToken)
                .build()

            val response = FirebaseMessaging.getInstance().send(message)
            println("✅ Notification sent successfully: $response")

            call.respondText("Notification sent successfully")
        } catch (e: Exception) {
            println("❌ Firebase error: ${e.message}")
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Firebase failed: ${e.message}"))
        }
    }
}