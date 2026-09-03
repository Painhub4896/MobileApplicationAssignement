package com.example.aistudybuddy.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val NotificationPrimaryBlue = Color(0xFF4C6FFF)
val NotificationLightBlue = Color(0xFFE8F0FE)
val NotificationTextDark = Color(0xFF2C3E50)

// Enum for filter categories
enum class NotificationFilter {
    ALL,
    UNREAD,
    REMINDER,
    UPDATES
}

data class NotificationItem(
    val icon: ImageVector,
    val title: String,
    val message: String,
    val time: String,
    var isUnread: Boolean,  // ← Changed to var so it can be updated
    val iconColor: Color,
    val iconBg: Color,
    val category: NotificationFilter
)

@Composable
fun NotificationScreen(onBackClick: () -> Unit) {
    // State for selected filter
    var selectedFilter by remember { mutableStateOf(NotificationFilter.ALL) }

    // Sample Data with categories - Using mutableStateListOf so we can modify items
    val allNotifications = remember {
        mutableStateListOf(
            NotificationItem(
                icon = Icons.Outlined.Assignment,
                title = "Assignment Reminder",
                message = "Mobile Application is due in 3 days.",
                time = "10.30 AM",
                isUnread = true,
                iconColor = NotificationPrimaryBlue,
                iconBg = NotificationLightBlue,
                category = NotificationFilter.REMINDER
            ),
            NotificationItem(
                icon = Icons.Outlined.AutoAwesome,
                title = "AI Routine Is Ready",
                message = "Your personalized routine for today is ready!",
                time = "9.00 AM",
                isUnread = true,
                iconColor = NotificationPrimaryBlue,
                iconBg = NotificationLightBlue,
                category = NotificationFilter.UPDATES
            ),
            NotificationItem(
                icon = Icons.Outlined.Notifications,
                title = "Study Reminder",
                message = "Don't forget Mobile Application Revision at 8.00 PM.",
                time = "Yesterday",
                isUnread = true,
                iconColor = Color(0xFF22C55E),
                iconBg = Color(0xFFDCFCE7),
                category = NotificationFilter.REMINDER
            ),
            NotificationItem(
                icon = Icons.Outlined.EmojiEvents,
                title = "Focus Session Complete",
                message = "Great Job! You completed a 45 min focus session.",
                time = "Yesterday",
                isUnread = false,
                iconColor = Color(0xFFF59E0B),
                iconBg = Color(0xFFFEF3C7),
                category = NotificationFilter.UPDATES
            ),
            NotificationItem(
                icon = Icons.Outlined.CalendarMonth,
                title = "Upcoming Exam",
                message = "Mid Term test upcoming 10 days. Keep it up!",
                time = "14 Sep",
                isUnread = false,
                iconColor = Color(0xFFEF4444),
                iconBg = Color(0xFFFEE2E2),
                category = NotificationFilter.REMINDER
            ),
            NotificationItem(
                icon = Icons.Outlined.BarChart,
                title = "Weekly Progress",
                message = "You've improved 20% in problem solving this week",
                time = "30 Aug",
                isUnread = false,
                iconColor = Color(0xFF10B981),
                iconBg = Color(0xFFD1FAE5),
                category = NotificationFilter.UPDATES
            )
        )
    }

    // Filter notifications based on selected filter
    val filteredNotifications = when (selectedFilter) {
        NotificationFilter.ALL -> allNotifications
        NotificationFilter.UNREAD -> allNotifications.filter { it.isUnread }
        NotificationFilter.REMINDER -> allNotifications.filter { it.category == NotificationFilter.REMINDER }
        NotificationFilter.UPDATES -> allNotifications.filter { it.category == NotificationFilter.UPDATES }
    }

    Scaffold(
        containerColor = Color.White,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Notification",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Filter Tabs
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                FilterChip(
                    selected = selectedFilter == NotificationFilter.ALL,
                    onClick = { selectedFilter = NotificationFilter.ALL },
                    label = { Text("All", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NotificationPrimaryBlue,
                        selectedLabelColor = Color.White,
                        containerColor = NotificationLightBlue,
                        labelColor = NotificationTextDark
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterChip(
                    selected = selectedFilter == NotificationFilter.UNREAD,
                    onClick = { selectedFilter = NotificationFilter.UNREAD },
                    label = { Text("Unread", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NotificationPrimaryBlue,
                        selectedLabelColor = Color.White,
                        containerColor = NotificationLightBlue,
                        labelColor = NotificationTextDark
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterChip(
                    selected = selectedFilter == NotificationFilter.REMINDER,
                    onClick = { selectedFilter = NotificationFilter.REMINDER },
                    label = { Text("Reminder", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NotificationPrimaryBlue,
                        selectedLabelColor = Color.White,
                        containerColor = NotificationLightBlue,
                        labelColor = NotificationTextDark
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterChip(
                    selected = selectedFilter == NotificationFilter.UPDATES,
                    onClick = { selectedFilter = NotificationFilter.UPDATES },
                    label = { Text("Updates", fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NotificationPrimaryBlue,
                        selectedLabelColor = Color.White,
                        containerColor = NotificationLightBlue,
                        labelColor = NotificationTextDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notification List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                if (filteredNotifications.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No notifications in this category",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    items(filteredNotifications) { notification ->
                        NotificationItemRow(
                            item = notification,
                            onItemClick = {
                                // Mark as read when clicked
                                notification.isUnread = false
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItemRow(
    item: NotificationItem,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },  // ← Click to mark as read
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(item.iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = item.iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = if (item.isUnread) FontWeight.Bold else FontWeight.Normal,
                    color = NotificationTextDark,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.message,
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Blue dot - only shows if unread
            if (item.isUnread) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(NotificationPrimaryBlue, CircleShape)
                )
            }
        }
    }
}