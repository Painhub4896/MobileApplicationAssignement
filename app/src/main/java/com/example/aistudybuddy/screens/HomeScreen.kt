package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar

@Composable
fun HomeScreen(
    onHomeClick: () -> Unit,
    onAssignmentClick: () -> Unit,
    onPlannerClick: () -> Unit,
    onProgressClick: () -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onTimerClick: () -> Unit,
    onStudyNotesClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    Scaffold(

        // Top Header
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                AppHeader(onNotificationClick)
            }
        },

        // Bottom Navigation
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Home",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }

    ) { paddingValues ->

        // Main Body
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // AI Profile Picture
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8E8E8)),
                            tint = Color(0xFF6C63FF)
                        )

                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                "Hi Alex!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                "Let's make Today Productive👋",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // Focus Timer Button
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 16.dp),
                    onClick = onTimerClick
                ) {

                    Text(
                        text = "Focus Timer",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                            horizontal = 28.dp
                        )
                    )
                }

                // Study Notes Button
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 16.dp),
                    onClick = onStudyNotesClick
                ) {

                    Text(
                        text = "Study Notes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                            horizontal = 28.dp
                        )
                    )
                }
            }
        }
    }
}