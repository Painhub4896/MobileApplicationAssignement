package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ===================== DATA CLASS =====================
data class StudySessionItem(
    val title: String,
    val time: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val iconBg: Color,
    val cardColor: Color,
    val textColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onHomeClick: () -> Unit,
    onAssignmentClick: () -> Unit,
    onPlannerClick: () -> Unit,
    onProgressClick: () -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onTimerClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onStudyNotesClick: () -> Unit
) {

    // Sample study sessions - these would come from your planner data
    val todaySessions = listOf(
        StudySessionItem(
            title = "Mathematics",
            time = "10:00 AM – 11:00 AM",
            icon = Icons.Default.Functions,
            iconColor = Color(0xFFFF3D8D),
            iconBg = Color(0xFFFFDCEB),
            cardColor = Color(0xFFFFF0F5),
            textColor = Color(0xFFFF3D8D)
        ),
        StudySessionItem(
            title = "Physics",
            time = "12:00 PM – 1:00 PM",
            icon = Icons.Default.Science,
            iconColor = Color(0xFFB565F5),
            iconBg = Color(0xFFE8D7FF),
            cardColor = Color(0xFFF6EDFF),
            textColor = Color(0xFFB565F5)
        ),
        StudySessionItem(
            title = "Bahasa Melayu",
            time = "5:00 PM – 6:00 PM",
            icon = Icons.Default.ChatBubble,
            iconColor = Color(0xFF45D878),
            iconBg = Color(0xFFD5F7DF),
            cardColor = Color(0xFFECFFF0),
            textColor = Color(0xFF45D878)
        ),
        StudySessionItem(
            title = "Biology Revision",
            time = "8:00 PM – 9:00 PM",
            icon = Icons.Default.Eco,
            iconColor = Color(0xFFD9D900),
            iconBg = Color(0xFFF2F2B8),
            cardColor = Color(0xFFFFFFE8),
            textColor = Color(0xFFD9D900)
        )
    )

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            AppHeader(onNotificationClick)
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Home",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // ==================== GREETING CARD ====================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8E8E8)),
                            tint = Color(0xFF6C63FF)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Hi Alex!",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                "Let's make Today Productive 👋",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }


            // ==================== TODAY'S SCHEDULE ====================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Today,
                                    contentDescription = "Today",
                                    tint = Color(0xFF4C6FFF),
                                    modifier = Modifier.size(18.dp)
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "Today's Schedule",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF252838)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFF4C6FFF),
                                        RoundedCornerShape(7.dp)
                                    )
                                    .padding(
                                        horizontal = 8.dp,
                                        vertical = 3.dp
                                    )
                            ) {
                                Text(
                                    text = "${todaySessions.size} classes",
                                    fontSize = 9.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(9.dp))

                        if (todaySessions.isEmpty()) {

                            Text(
                                "No classes scheduled for today 📚",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                        } else {

                            todaySessions.forEach { session ->

                                StudySessionCard(session = session)

                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }



            // ==================== UPCOMING ASSIGNMENT ====================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        Text(
                            "Upcoming Assignment",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )

                        Text(
                            "Database Project",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    Icons.Default.Assignment,
                                    contentDescription = "Due Date",
                                    modifier = Modifier.size(13.dp),
                                    tint = Color.Red
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    "Due in 3 days - May 25, 2025",
                                    fontSize = 11.sp,
                                    color = Color.Red
                                )
                            }

                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = "View",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }


            // ==================== EXAM COUNTDOWN & ASSIGNMENT REMINDER ====================
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    // Exam Countdown
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                            Text(
                                "Exam Countdown",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )

                            Text(
                                "Midterm Exams",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                "Jun 5 - Jun 12, 2025",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "12 days left",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        }
                    }


                    // Assignment Reminder
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                            Text(
                                "Assignment Reminder",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )

                            Text(
                                "Database Project is due in 3 days",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                maxLines = 2
                            )

                            Text(
                                "May 25, 2025",
                                fontSize = 10.sp,
                                color = Color(0xFFFF9800)
                            )
                        }
                    }
                }
            }


            // ==================== FOCUS TIMER BUTTON ====================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4C6FFF)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = onTimerClick
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(13.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.Today,
                            contentDescription = "Focus Timer",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Start Focus Timer",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }


            // ==================== STUDY NOTES BUTTON ====================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF7C3AED)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = onStudyNotesClick
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(13.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.School,
                            contentDescription = "Study Notes",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            "Study Notes",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }


            // ==================== WEEKLY PROGRESS ====================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.StackedBarChart,
                            contentDescription = "Progress",
                            tint = Color(0xFF4C6FFF),
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text(
                                "Weekly Progress",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )

                            Text(
                                "You've improved 20% in problem solving this week! 🎉",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}


// ==================== STUDY SESSION CARD ====================
@Composable
fun StudySessionCard(
    session: StudySessionItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = session.cardColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(session.iconBg),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = session.icon,
                    contentDescription = null,
                    tint = session.iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(9.dp))


            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = session.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = session.textColor
                )

                Text(
                    text = session.time,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }


            // Arrow indicator
            Text(
                text = "→",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}