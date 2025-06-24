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
    val serviceAccount = File("src/main/resources/serviceAccountKey.json").inputStream()

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
        println("✅ Firebase Admin SDK initialized.")
    }
}
