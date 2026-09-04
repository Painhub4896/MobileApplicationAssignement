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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aistudybuddy.auth.AuthViewModel
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.StudyPlannerData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


// ===================== DATA CLASS =====================
data class StudySessionItem(
    val title: String,
    val time: String,
    val room: String? = null,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val iconBg: Color,
    val cardColor: Color,
    val textColor: Color
)


private val homeSessionColors =
    listOf(
        Color(0xFFFFF0F5),
        Color(0xFFF6EDFF),
        Color(0xFFECFFF0),
        Color(0xFFFFFFE8),
        Color(0xFFEAF7FF),
        Color(0xFFFFF1E6),
        Color(0xFFF0F0FF),
        Color(0xFFEFFFF7),
        Color(0xFFFFF0E8),
        Color(0xFFEFF7FF)
    )


private val homeSessionTitleColors =
    listOf(
        Color(0xFFFF3D8D),
        Color(0xFFB565F5),
        Color(0xFF45D878),
        Color(0xFFD0D000),
        Color(0xFF2997D6),
        Color(0xFFFF8A3D),
        Color(0xFF6565D8),
        Color(0xFF22AA77),
        Color(0xFFE56B4A),
        Color(0xFF4285D4)
    )


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    assignments: List<AssignmentItem>,
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

    val userName by authViewModel.userName.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {

        authViewModel.fetchUserProfile()
    }


    val displayName =
        userName ?: "User"


    // =====================================================
    // TODAY'S PLANNER SESSIONS
    // =====================================================

    val todaySessions =
        StudyPlannerData
            .getSessionsForDate(
                LocalDate.now()
            )
            .map { session ->

                val colorIndex =
                    session.colorIndex %
                            homeSessionColors.size


                StudySessionItem(
                    title =
                        session.subject,

                    time =
                        "${session.startTime} – ${session.endTime}",

                    room =
                        session.room,

                    icon =
                        Icons.Default.School,

                    iconColor =
                        homeSessionTitleColors[
                            colorIndex
                        ],

                    iconBg =
                        homeSessionTitleColors[
                            colorIndex
                        ].copy(
                            alpha = 0.12f
                        ),

                    cardColor =
                        homeSessionColors[
                            colorIndex
                        ],

                    textColor =
                        homeSessionTitleColors[
                            colorIndex
                        ]
                )
            }


    // =====================================================
    // ASSIGNMENT REMINDER
    // =====================================================

    val reminderAssignment =
        assignments
            .filter { assignment ->

                !assignment.isCompleted
            }
            .minByOrNull { assignment ->

                parseAssignmentDate(
                    assignment.dueDate
                ) ?: LocalDate.MAX
            }


    Scaffold(
        containerColor = Color(0xFFF5F5F5),

        topBar = {

            AppHeader(
                onNotificationClick
            )
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
                .background(
                    Color(0xFFF5F5F5)
                )
                .padding(
                    paddingValues
                ),

            contentPadding =
                PaddingValues(
                    12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    10.dp
                )
        ) {


            // ==================== GREETING CARD ====================

            item {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        ),

                    shape =
                        RoundedCornerShape(
                            10.dp
                        )
                ) {

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    12.dp
                                ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                "Profile Picture",

                            modifier =
                                Modifier
                                    .size(
                                        48.dp
                                    )
                                    .clip(
                                        CircleShape
                                    )
                                    .background(
                                        Color(
                                            0xFFE8E8E8
                                        )
                                    ),

                            tint =
                                Color(
                                    0xFF6C63FF
                                )
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    12.dp
                                )
                        )


                        Column(
                            verticalArrangement =
                                Arrangement.Center
                        ) {

                            Text(
                                text =
                                    "Hi, $displayName!",

                                fontSize =
                                    18.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.Black
                            )


                            Text(
                                text =
                                    "Let's make Today Productive 👋",

                                fontSize =
                                    12.sp,

                                color =
                                    Color.Gray
                            )
                        }
                    }
                }
            }


            // ==================== TODAY'S SCHEDULE ====================

            item {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        ),

                    shape =
                        RoundedCornerShape(
                            10.dp
                        )
                ) {

                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    12.dp
                                )
                    ) {


                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween,

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {


                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Today,

                                    contentDescription =
                                        "Today",

                                    tint =
                                        Color(
                                            0xFF4C6FFF
                                        ),

                                    modifier =
                                        Modifier.size(
                                            18.dp
                                        )
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(
                                            6.dp
                                        )
                                )


                                Text(
                                    text =
                                        "Today's Schedule",

                                    fontSize =
                                        14.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        Color(
                                            0xFF252838
                                        )
                                )
                            }


                            Box(
                                modifier =
                                    Modifier
                                        .background(
                                            Color(
                                                0xFF4C6FFF
                                            ),

                                            RoundedCornerShape(
                                                7.dp
                                            )
                                        )
                                        .padding(
                                            horizontal =
                                                8.dp,

                                            vertical =
                                                3.dp
                                        )
                            ) {

                                Text(
                                    text =
                                        "${todaySessions.size} sessions",

                                    fontSize =
                                        9.sp,

                                    color =
                                        Color.White,

                                    fontWeight =
                                        FontWeight.Medium
                                )
                            }
                        }


                        Spacer(
                            modifier =
                                Modifier.height(
                                    9.dp
                                )
                        )


                        if (
                            todaySessions.isEmpty()
                        ) {

                            Text(
                                text =
                                    "No study sessions scheduled for today 📚",

                                fontSize =
                                    12.sp,

                                color =
                                    Color.Gray,

                                modifier =
                                    Modifier.padding(
                                        vertical =
                                            10.dp
                                    )
                            )

                        } else {


                            todaySessions.forEach { session ->


                                StudySessionCard(
                                    session =
                                        session
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            6.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }


            // ==================== ASSIGNMENT REMINDER ====================

            if (
                reminderAssignment != null
            ) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        shape =
                            RoundedCornerShape(
                                10.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        12.dp
                                    )
                        ) {


                            Text(
                                text =
                                    "Assignment Reminder",

                                fontSize =
                                    10.sp,

                                color =
                                    Color.Gray
                            )


                            Text(
                                text =
                                    reminderAssignment.title,

                                fontSize =
                                    15.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.Black
                            )


                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {


                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {


                                    Icon(
                                        imageVector =
                                            Icons.Default.Assignment,

                                        contentDescription =
                                            "Due Date",

                                        modifier =
                                            Modifier.size(
                                                13.dp
                                            ),

                                        tint =
                                            Color.Red
                                    )


                                    Spacer(
                                        modifier =
                                            Modifier.width(
                                                4.dp
                                            )
                                    )


                                    Text(
                                        text =
                                            getAssignmentDueText(
                                                reminderAssignment.dueDate
                                            ),

                                        fontSize =
                                            11.sp,

                                        color =
                                            Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }


            // ==================== FOCUS TIMER BUTTON ====================

            item {

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical =
                                    2.dp
                            ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(
                                    0xFF4C6FFF
                                )
                        ),

                    shape =
                        RoundedCornerShape(
                            10.dp
                        ),

                    onClick =
                        onTimerClick
                ) {

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    13.dp
                                ),

                        horizontalArrangement =
                            Arrangement.Center,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Icon(
                            imageVector =
                                Icons.Default.Today,

                            contentDescription =
                                "Focus Timer",

                            tint =
                                Color.White,

                            modifier =
                                Modifier.size(
                                    18.dp
                                )
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    6.dp
                                )
                        )


                        Text(
                            text =
                                "Start Focus Timer",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )
                    }
                }
            }


            // ==================== STUDY NOTES BUTTON ====================

            item {

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical =
                                    2.dp
                            ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(
                                    0xFF7C3AED
                                )
                        ),

                    shape =
                        RoundedCornerShape(
                            10.dp
                        ),

                    onClick =
                        onStudyNotesClick
                ) {

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    13.dp
                                ),

                        horizontalArrangement =
                            Arrangement.Center,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Icon(
                            imageVector =
                                Icons.Default.School,

                            contentDescription =
                                "Study Notes",

                            tint =
                                Color.White,

                            modifier =
                                Modifier.size(
                                    18.dp
                                )
                        )


                        Spacer(
                            modifier =
                                Modifier.width(
                                    6.dp
                                )
                        )


                        Text(
                            text =
                                "Study Notes",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )
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
        modifier =
            Modifier.fillMaxWidth(),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    session.cardColor
            ),

        shape =
            RoundedCornerShape(
                8.dp
            )
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        9.dp
                    ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {


            // Icon
            Box(
                modifier =
                    Modifier
                        .size(
                            34.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                8.dp
                            )
                        )
                        .background(
                            session.iconBg
                        ),

                contentAlignment =
                    Alignment.Center
            ) {


                Icon(
                    imageVector =
                        session.icon,

                    contentDescription =
                        null,

                    tint =
                        session.iconColor,

                    modifier =
                        Modifier.size(
                            18.dp
                        )
                )
            }


            Spacer(
                modifier =
                    Modifier.width(
                        9.dp
                    )
            )


            // Content
            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {


                Text(
                    text =
                        session.title,

                    fontSize =
                        12.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        session.textColor
                )


                Text(
                    text =
                        session.time,

                    fontSize =
                        10.sp,

                    color =
                        Color.Gray
                )


                if (
                    !session.room.isNullOrBlank()
                ) {

                    Text(
                        text =
                            "Room: ${session.room}",

                        fontSize =
                            10.sp,

                        color =
                            Color.Gray
                    )
                }
            }
        }
    }
}


// =====================================================
// PARSE ASSIGNMENT DATE
// =====================================================

private fun parseAssignmentDate(
    dueDate: String
): LocalDate? {

    return try {

        val formatter =
            DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                Locale.getDefault()
            )


        LocalDate.parse(
            dueDate,
            formatter
        )

    } catch (
        e: Exception
    ) {

        null
    }
}


// =====================================================
// GET ASSIGNMENT REMINDER TEXT
// =====================================================

private fun getAssignmentDueText(
    dueDate: String
): String {

    val due =
        parseAssignmentDate(
            dueDate
        )
            ?: return "Due: $dueDate"


    val today =
        LocalDate.now()


    val daysRemaining =
        ChronoUnit.DAYS.between(
            today,
            due
        )


    return when {


        daysRemaining < 0 -> {

            val overdueDays =
                -daysRemaining


            if (
                overdueDays == 1L
            ) {

                "Overdue by 1 day - $dueDate"

            } else {

                "Overdue by $overdueDays days - $dueDate"
            }
        }


        daysRemaining == 0L -> {

            "Due today - $dueDate"
        }


        daysRemaining == 1L -> {

            "Due tomorrow - $dueDate"
        }


        else -> {

            "Due in $daysRemaining days - $dueDate"
        }
    }
}