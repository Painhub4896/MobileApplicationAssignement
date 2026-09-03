package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar


@Composable
fun ProgressDashboardScreen(
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val completedMinutes = StudyProgress.getThisWeekMinutes()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Progress",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentsClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick,
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {

            AppHeader()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                // TITLE

                Text(
                    text = "Progress Dashboard",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF252838)
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )


                // STATISTICS CARDS

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Timer,
                        title = "Study Hours",
                        value = formatStudyHours(completedMinutes),
                        subtitle = "This Week",
                        cardBackground = Color(0xFFF0F5FF),
                        iconColor = Color(0xFF4169E1)
                    )

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CheckCircle,
                        title = "Completed Assignments",
                        value = "6",
                        subtitle = "This Week",
                        cardBackground = Color(0xFFF1FAF0),
                        iconColor = Color(0xFF5DBB63)
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.FlashOn,
                        title = "Study Streak",
                        value = "${StudyProgress.getStudyStreak()} Days",                        subtitle = "Keep it up!",
                        cardBackground = Color(0xFFF6F0FF),
                        iconColor = Color(0xFF8755D6)
                    )

                    ProgressStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.ShowChart,
                        title = "AI Plan Completion",
                        value = "82%",
                        subtitle = "On Track",
                        cardBackground = Color(0xFFFFFAED),
                        iconColor = Color(0xFFF2B632)
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // WEEKLY STUDY HOURS

                WeeklyStudyHoursCard()

                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                // RECENT ACTIVITY

                RecentActivityCard()
            }
        }
    }
}


// =====================================================
// STATISTICS CARD
// =====================================================

@Composable
fun ProgressStatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    subtitle: String,
    cardBackground: Color,
    iconColor: Color
) {

    Box(
        modifier = modifier
            .height(102.dp)
            .background(
                color = cardBackground,
                shape = RoundedCornerShape(9.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color(0xFFE1E1E1),
                shape = RoundedCornerShape(9.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(15.dp)
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF30323D)
                )
            }

            Spacer(
                modifier = Modifier.height(9.dp)
            )

            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF252838),
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = Color(0xFF777983),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


// =====================================================
// WEEKLY STUDY HOURS
// =====================================================

@Composable
fun WeeklyStudyHoursCard() {

    val dailyMinutes = StudyProgress.getDailyStudyMinutes()

    val hours = dailyMinutes.map { minutes ->
        minutes / 60f
    }

    val days = listOf(
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat",
        "Sun"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = "Study Hours This Week",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF30323D)
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {

            Column(
                modifier = Modifier
                    .width(22.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "4h",
                    fontSize = 7.sp,
                    color = Color(0xFF777983)
                )

                Text(
                    text = "3h",
                    fontSize = 7.sp,
                    color = Color(0xFF777983)
                )

                Text(
                    text = "2h",
                    fontSize = 7.sp,
                    color = Color(0xFF777983)
                )

                Text(
                    text = "1h",
                    fontSize = 7.sp,
                    color = Color(0xFF777983)
                )

                Text(
                    text = "0",
                    fontSize = 7.sp,
                    color = Color(0xFF777983)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {

                hours.forEachIndexed { index, hour ->

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .width(18.dp)
                                .height((hour / 4f * 75f).dp)
                                .background(
                                    color = Color(0xFF4169E1),
                                    shape = RoundedCornerShape(
                                        topStart = 4.dp,
                                        topEnd = 4.dp
                                    )
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = days[index],
                            fontSize = 7.sp,
                            color = Color(0xFF777983)
                        )
                    }
                }
            }
        }
    }
}


// =====================================================
// RECENT ACTIVITY
// =====================================================

@Composable
fun RecentActivityCard() {

    val activities = StudyProgress.completedStudySessions

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Recent Activity",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF30323D)
            )

            Text(
                text = "View All",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4169E1)
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        if (activities.isEmpty()) {

            Text(
                text = "No study activity yet.",
                fontSize = 10.sp,
                color = Color(0xFF777983),
                modifier = Modifier.padding(vertical = 8.dp)
            )

        } else {

            activities.take(3).forEachIndexed { index, activity ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = Color(0xFFE5F8E8),
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Study session",
                            tint = Color(0xFF59B96A),
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(9.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = activity.title,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF30323D)
                        )

                        Spacer(
                            modifier = Modifier.height(2.dp)
                        )

                        Text(
                            text = "${activity.minutes} minutes • ${
                                formatActivityDateTime(activity.dateTime)
                            }",
                            fontSize = 8.sp,
                            color = Color(0xFF777983)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = Color(0xFF59B96A),
                        modifier = Modifier.size(14.dp)
                    )
                }

                if (index < activities.take(3).size - 1) {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
            }
        }
    }
}

private fun formatActivityDateTime(

    dateTime: java.time.LocalDateTime

): String {

    val today = java.time.LocalDate.now()
    val date = dateTime.toLocalDate()

    val time = dateTime.toLocalTime()
        .format(
            java.time.format.DateTimeFormatter.ofPattern("h:mm a")
        )

    return when {
        date == today -> "Today, $time"

        date == today.minusDays(1) -> "Yesterday, $time"

        else -> dateTime.format(
            java.time.format.DateTimeFormatter.ofPattern(
                "dd MMM, h:mm a"
            )
        )
    }
}

private fun formatStudyHours(minutes: Int): String {

    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    return if (hours > 0) {
        "$hours hr $remainingMinutes m"
    } else {
        "$remainingMinutes min"
    }
}


// =====================================================
// PREVIEW
// =====================================================

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun ProgressDashboardScreenPreview() {
    ProgressDashboardScreen()
}