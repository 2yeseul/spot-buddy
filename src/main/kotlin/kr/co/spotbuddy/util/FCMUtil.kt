package kr.co.spotbuddy.util

import com.github.kittinunf.fuel.httpPost
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kr.co.spotbuddy.interfaces.request.PushAlarmRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

val log: Logger = LoggerFactory.getLogger("FCMUtil")

@Component
class FCMUtil(
    @Value("\${firebase.api}")
    private val api: String,
    @Value("\${firebase.config-path}")
    private val configPath: String,
) {
    fun sendNotification(alarmRequest: PushAlarmRequest): String? {
        val (token, title, body, path) = alarmRequest
        val notification = generateNotification(token, title, body, path)
        val (request, response, result) = api.httpPost()
            .body(notification)
            .response()

        return response.toString()
    }

    private fun generateNotification(token: String, title: String, body: String, path: String): String {
        return """
            {
                "message": {
                    "token": $token,
                    "notification": {
                        "title": $title,
                        "body": $body
                    },
                    "data": {
                        "path": $path
                    }
                }
            }
        """.trimIndent()
    }
}

data class FCMRequest(
    val validate_only: Boolean,
    val message: AppMessage,
)

data class AppMessage(
    val notification: Notification,
    val data: FCMData,
    val token: String,
)

data class Notification(
    val title: String,
    val body: String,
    val image: String,
)

data class FCMData(
    val path: String,
)