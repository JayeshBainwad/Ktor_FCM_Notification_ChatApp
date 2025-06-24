package com.jsb.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.jsb.routes.notificationRoutes

fun Application.configureRouting() {
    routing {
        notificationRoutes()
    }
}
