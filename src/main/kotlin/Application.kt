package com.jsb

import io.ktor.server.application.*
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.jsb.plugins.configureRouting
import com.jsb.plugins.configureSerialization
import java.io.File

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSecurity()
    configureSerialization()
    configureRouting() // includes NotificationRoutes
    initializeFirebase()
    val host = environment.config.propertyOrNull("ktor.deployment.host")?.getString() ?: "0.0.0.0"
    val port = environment.config.propertyOrNull("ktor.deployment.port")?.getString() ?: "8080"

    log.info("🚀 Server is running on https://$host:$port")
}

fun initializeFirebase() {
    val base64Key = System.getenv("FIREBASE_KEY_BASE64")
    if (base64Key != null) {
        val decodedKey = java.util.Base64.getDecoder().decode(base64Key)
        val credentials = GoogleCredentials.fromStream(decodedKey.inputStream())
        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
            println("✅ Firebase Admin SDK initialized.")
        }
    } else {
        println("❌ FIREBASE_KEY_BASE64 environment variable not set.")
    }
}

