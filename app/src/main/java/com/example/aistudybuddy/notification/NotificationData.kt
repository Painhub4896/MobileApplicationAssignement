package com.example.aistudybuddy.notification

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDateTime
import java.time.ZoneId

data class AppNotification(
    val title: String,
    val message: String,
    val dateTime: LocalDateTime,
    var isUnread: Boolean = true
)

object NotificationData {

    private val malaysiaZone =
        ZoneId.of("Asia/Kuala_Lumpur")

    val notifications =
        mutableStateListOf<AppNotification>()

    fun addNotification(
        title: String,
        message: String
    ) {

        notifications.add(
            0,
            AppNotification(
                title = title,
                message = message,
                dateTime =
                    LocalDateTime.now(malaysiaZone),
                isUnread = true
            )
        )

        if (notifications.size > 20) {
            notifications.removeAt(
                notifications.size - 1
            )
        }
    }
}