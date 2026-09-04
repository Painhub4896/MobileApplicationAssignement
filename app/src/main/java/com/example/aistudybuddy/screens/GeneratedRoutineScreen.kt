package com.example.aistudybuddy.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import com.example.aistudybuddy.data.GeneratedStudySession

@Composable
fun GeneratedRoutineScreen(
    sessions: List<GeneratedStudySession> = sampleGeneratedSessions(),
    onAcceptClick: () -> Unit = {},
    onAdjustClick: () -> Unit = {}
) {

    val primaryBlue = Color(0xFF4169E1)
    val purple = Color(0xFF7C3AED)
    val pageBackground = Color(0xFFF7F8FC)
    val textPrimary = Color(0xFF171A24)
    val textSecondary = Color(0xFF747984)

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBackground)
                .padding(innerPadding)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 18.dp,
                        vertical = 16.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // =====================================================
                // TITLE
                // =====================================================

                Text(
                    text = "Your Smart Routine",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )

                Text(
                    text = "AIStudyBuddy created a study routine based on your timetable and preferences.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = textSecondary
                )


                // =====================================================
                // AI SUCCESS CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3EEFF)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE3D7FF)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = purple,
                                modifier = Modifier
                                    .padding(9.dp)
                                    .size(22.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        ) {

                            Text(
                                text = "AI Routine Ready",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textPrimary
                            )

                            Text(
                                text = "${sessions.size} study sessions recommended",
                                fontSize = 11.sp,
                                color = textSecondary
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF34A853),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                // =====================================================
                // SECTION TITLE
                // =====================================================

                Text(
                    text = "Recommended Sessions",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )


                // =====================================================
                // SESSION CARDS
                // =====================================================

                sessions.forEachIndexed { index, session ->

                    GeneratedSessionCard(
                        session = session,
                        number = index + 1
                    )
                }


                // =====================================================
                // ACCEPT BUTTON
                // =====================================================

                Button(
                    onClick = onAcceptClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "  Accept Routine",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                // =====================================================
                // ADJUST BUTTON
                // =====================================================

                OutlinedButton(
                    onClick = onAdjustClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        1.dp,
                        primaryBlue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = primaryBlue,
                        modifier = Modifier.size(19.dp)
                    )

                    Text(
                        text = "  Adjust Routine",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryBlue
                    )
                }


                Text(
                    text = "You can adjust your preferences and generate a new routine anytime.",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    color = textSecondary
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}


// =====================================================================
// SESSION CARD
// =====================================================================

@Composable
private fun GeneratedSessionCard(
    session: GeneratedStudySession,
    number: Int
) {

    val textPrimary = Color(0xFF20232D)
    val textSecondary = Color(0xFF666B78)

    val subjectColor =
        generatedSubjectColor(
            session.subject
        )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            1.dp,
            Color(0xFFE5E7EE)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // =========================================================
            // TOP ROW
            // =========================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = subjectColor
                    )
                ) {

                    Text(
                        text = number.toString(),
                        modifier = Modifier.padding(
                            horizontal = 11.dp,
                            vertical = 7.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 11.dp)
                ) {

                    Text(
                        text = session.subject,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )

                    Text(
                        text = session.task,
                        fontSize = 12.sp,
                        color = textSecondary
                    )
                }

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3EEFF)
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 5.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF7C3AED),
                            modifier = Modifier.size(13.dp)
                        )

                        Text(
                            text = " AI Pick",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7C3AED)
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // =========================================================
            // TIME
            // =========================================================

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color(0xFF4169E1),
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "  ${session.startTime} - ${session.endTime}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary
                )
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // =========================================================
            // LOCATION
            // =========================================================

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF747984),
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "  ${session.location}",
                    fontSize = 12.sp,
                    color = textSecondary
                )
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // =========================================================
            // WHY THIS SESSION
            // =========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(13.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF8E8)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFF5E5BB)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {

                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = Color(0xFFE0A100),
                        modifier = Modifier.size(19.dp)
                    )

                    Column(
                        modifier = Modifier.padding(
                            start = 9.dp
                        )
                    ) {

                        Text(
                            text = "Why this session?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = session.reason,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = textSecondary
                        )
                    }
                }
            }
        }
    }
}


// =====================================================================
// SUBJECT COLORS
// =====================================================================

private fun generatedSubjectColor(
    subject: String
): Color {

    return when (
        subject.lowercase()
    ) {

        "biology" ->
            Color(0xFFCFEFD8)

        "mathematics" ->
            Color(0xFFE8DDFB)

        "physics" ->
            Color(0xFFFFE7A8)

        "chemistry" ->
            Color(0xFFFFD5E0)

        "english" ->
            Color(0xFFD6E5FF)

        "computer science" ->
            Color(0xFFD7F2F3)

        else ->
            Color(0xFFE5E8F4)
    }
}


// =====================================================================
// SAMPLE DATA
// =====================================================================

private fun sampleGeneratedSessions():
        List<GeneratedStudySession> {

    return listOf(

        GeneratedStudySession(
            subject = "Biology",
            startTime = "7:00 PM",
            endTime = "7:45 PM",
            task = "Review Chapter 6",
            reason = "Your Biology test is coming soon, so this session gives Biology higher priority while avoiding your class timetable.",
            location = "Study Area"
        ),

        GeneratedStudySession(
            subject = "Mathematics",
            startTime = "8:00 PM",
            endTime = "8:45 PM",
            task = "Practice Algebra Questions",
            reason = "A problem-solving session is placed after Biology to balance memory-based and calculation-based study.",
            location = "Home"
        ),

        GeneratedStudySession(
            subject = "Computer Science",
            startTime = "9:00 PM",
            endTime = "9:45 PM",
            task = "Database Project",
            reason = "Your project needs steady progress, so AIStudyBuddy reserves a focused session before the end of your available study time.",
            location = "Study Area"
        )
    )
}


// =====================================================================
// PREVIEW
// =====================================================================

@Preview(
    showBackground = true,
    widthDp = 393,
    heightDp = 852
)
@Composable
fun GeneratedRoutineScreenPreview() {

    AIStudyBuddyTheme {

        GeneratedRoutineScreen(
            sessions = sampleGeneratedSessions(),
            onAcceptClick = {},
            onAdjustClick = {}
        )
    }
}