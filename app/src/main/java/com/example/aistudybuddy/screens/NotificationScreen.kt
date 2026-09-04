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
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.notification.AppNotification
import com.example.aistudybuddy.notification.NotificationData
import java.time.format.DateTimeFormatter


val NotificationPrimaryBlue = Color(0xFF4C6FFF)
val NotificationLightBlue = Color(0xFFE8F0FE)
val NotificationTextDark = Color(0xFF2C3E50)


enum class NotificationFilter {
    ALL,
    UNREAD
}


@Composable
fun NotificationScreen(
    onBackClick: () -> Unit
) {

    var selectedFilter by remember {
        mutableStateOf(NotificationFilter.ALL)
    }

    val allNotifications =
        NotificationData.notifications

    val filteredNotifications =
        when (selectedFilter) {

            NotificationFilter.ALL ->
                allNotifications

            NotificationFilter.UNREAD ->
                allNotifications.filter {
                    it.isUnread
                }
        }


    Scaffold(
        containerColor = Color.White
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {

            // =====================================================
            // TOP HEADER
            // =====================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .height(42.dp)
            ) {

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(42.dp)
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(22.dp)
                    )
                }


                Text(
                    text = "Notification",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = NotificationTextDark,
                    modifier = Modifier.align(Alignment.Center)
                )
            }


            // =====================================================
            // FILTER TABS
            // =====================================================

            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth()
            ) {

                FilterChip(
                    selected =
                        selectedFilter ==
                                NotificationFilter.ALL,

                    onClick = {
                        selectedFilter =
                            NotificationFilter.ALL
                    },

                    label = {

                        Text(
                            text = "All",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },

                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor =
                                NotificationPrimaryBlue,

                            selectedLabelColor =
                                Color.White,

                            containerColor =
                                NotificationLightBlue,

                            labelColor =
                                NotificationTextDark
                        )
                )


                Spacer(
                    modifier = Modifier.width(8.dp)
                )


                FilterChip(
                    selected =
                        selectedFilter ==
                                NotificationFilter.UNREAD,

                    onClick = {
                        selectedFilter =
                            NotificationFilter.UNREAD
                    },

                    label = {

                        Text(
                            text = "Unread",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },

                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor =
                                NotificationPrimaryBlue,

                            selectedLabelColor =
                                Color.White,

                            containerColor =
                                NotificationLightBlue,

                            labelColor =
                                NotificationTextDark
                        )
                )
            }


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // =====================================================
            // NOTIFICATION LIST
            // =====================================================

            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                contentPadding =
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 6.dp,
                        bottom = 16.dp
                    )
            ) {

                if (filteredNotifications.isEmpty()) {

                    item {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    "No notifications yet.",

                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                } else {

                    items(
                        filteredNotifications,
                        key = {
                            it.dateTime.toString()
                        }
                    ) { notification ->

                        NotificationItemRow(
                            item = notification,

                            onItemClick = {

                                val index =
                                    NotificationData
                                        .notifications
                                        .indexOf(notification)

                                if (
                                    index in
                                    NotificationData
                                        .notifications
                                        .indices
                                ) {

                                    NotificationData
                                        .notifications[index] =
                                        notification.copy(
                                            isUnread = false
                                        )
                                }
                            }
                        )


                        Spacer(
                            modifier =
                                Modifier.height(14.dp)
                        )
                    }
                }
            }
        }
    }
}


// =====================================================
// NOTIFICATION ITEM
// =====================================================

@Composable
fun NotificationItemRow(
    item: AppNotification,
    onItemClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },

        verticalAlignment =
            Alignment.Top
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    NotificationLightBlue
                ),

            contentAlignment =
                Alignment.Center
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.Notifications,

                contentDescription = null,

                tint =
                    NotificationPrimaryBlue,

                modifier =
                    Modifier.size(21.dp)
            )
        }


        Spacer(
            modifier =
                Modifier.width(12.dp)
        )


        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = item.title,

                    fontSize = 14.sp,

                    fontWeight =
                        if (item.isUnread) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },

                    color =
                        NotificationTextDark,

                    modifier =
                        Modifier.weight(1f)
                )


                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )


                Text(
                    text =
                        item.dateTime.format(
                            DateTimeFormatter.ofPattern(
                                "hh:mm a"
                            )
                        ),

                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }


            Spacer(
                modifier =
                    Modifier.height(3.dp)
            )


            Text(
                text = item.message,

                fontSize = 12.sp,

                color = Color.Gray
            )


            if (item.isUnread) {

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )

                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            NotificationPrimaryBlue,
                            CircleShape
                        )
                )
            }
        }
    }
}