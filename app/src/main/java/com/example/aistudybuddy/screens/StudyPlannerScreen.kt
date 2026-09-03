package com.example.aistudybuddy.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.components.PlannerActionButtons
import com.example.aistudybuddy.components.StudySessionCard
import com.example.aistudybuddy.components.WeekSelector

@Composable
fun StudyPlannerScreen(
    onTimetableSetupClick: () -> Unit = {},
    onWeeklyTimetableClick: () -> Unit = {},

    onHomeClick: () -> Unit = {},
    onAssignmentClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Planner",

                onHomeClick = onHomeClick,
                onAssignmentClick = onAssignmentClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {

                item {

                    Text(
                        text = "Study Planner",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF252838)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    WeekSelector()

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    StudySessionCard(
                        title = "Mathematics",
                        time = "10:00 AM – 11:00 AM",
                        icon = Icons.Default.Functions,
                        iconBackground = Color(0xFFFFDCEB),
                        iconColor = Color(0xFFFF3D8D),
                        cardBackground = Color(0xFFFFF0F5),
                        titleColor = Color(0xFFFF3D8D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StudySessionCard(
                        title = "Physics",
                        time = "12:00 PM – 1:00 PM",
                        icon = Icons.Default.Science,
                        iconBackground = Color(0xFFE8D7FF),
                        iconColor = Color(0xFFB565F5),
                        cardBackground = Color(0xFFF6EDFF),
                        titleColor = Color(0xFFB565F5)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StudySessionCard(
                        title = "Bahasa Melayu",
                        time = "5:00 PM – 6:00 PM",
                        icon = Icons.Default.ChatBubble,
                        iconBackground = Color(0xFFD5F7DF),
                        iconColor = Color(0xFF45D878),
                        cardBackground = Color(0xFFECFFF0),
                        titleColor = Color(0xFF45D878)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StudySessionCard(
                        title = "Biology Revision",
                        time = "8:00 PM – 9:00 PM",
                        icon = Icons.Default.Eco,
                        iconBackground = Color(0xFFF2F2B8),
                        iconColor = Color(0xFFD9D900),
                        cardBackground = Color(0xFFFFFFE8),
                        titleColor = Color(0xFFD9D900),
                        aiSuggested = true
                    )

                    Spacer(
                        modifier = Modifier.height(17.dp)
                    )

                    PlannerActionButtons(
                        onGenerateWithAiClick = onWeeklyTimetableClick,
                        onAddSessionClick = onTimetableSetupClick
                    )
                }
            }

        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun StudyPlannerScreenPreview() {
    StudyPlannerScreen()
}