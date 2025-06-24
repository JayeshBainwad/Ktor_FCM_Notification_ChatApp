FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy everything
COPY . .

# Build the application using Gradle
RUN ./gradlew installDist
RUN chmod +x ./build/install/Ktor_FCM_Notification_ChatApp/bin/Ktor_FCM_Notification_ChatApp

# Expose port (match your Ktor app port, default is 8080)
EXPOSE 8080

# Start the app (path comes from installDist)
CMD ["./build/install/Ktor_FCM_Notification_ChatApp/bin/Ktor_FCM_Notification_ChatApp"]
