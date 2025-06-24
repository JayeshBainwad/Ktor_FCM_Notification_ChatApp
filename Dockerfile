FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy everything
COPY . .

# ✅ Fix: Make gradlew executable
RUN chmod +x gradlew

# Build the application using Gradle
RUN ./gradlew installDist

# ✅ Make your app binary executable
RUN chmod +x ./build/install/Ktor_FCM_Notification_ChatApp/bin/Ktor_FCM_Notification_ChatApp

# Expose port (default 8080)
EXPOSE 8080

# Start the app
CMD ["./build/install/Ktor_FCM_Notification_ChatApp/bin/Ktor_FCM_Notification_ChatApp"]
