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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.StudyRoutine


@Composable
fun RoutineDetailsScreen(
    routine: StudyRoutine?,
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val blue =
        Color(0xFF4169E1)

    val backgroundColor =
        Color(0xFFF9FAFC)

    val darkText =
        Color(0xFF252838)

    val greyText =
        Color(0xFF767987)


    Scaffold(
        containerColor =
            backgroundColor,

        bottomBar = {

            BottomNavigationBar(
                selectedItem =
                    "Planner",

                onHomeClick =
                    onHomeClick,

                onAssignmentsClick =
                    onAssignmentsClick,

                onPlannerClick =
                    onPlannerClick,

                onProgressClick =
                    onProgressClick,

                onProfileClick =
                    onProfileClick
            )
        }
    ) { innerPadding ->


        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        backgroundColor
                    )
                    .padding(
                        innerPadding
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    12.dp
                )
        ) {


            // ==========================================
            // TOP HEADER
            // ==========================================

            item {

                Column(
                    modifier =
                        Modifier.padding(
                            start = 12.dp,
                            end = 18.dp,
                            top = 12.dp
                        )
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick =
                                onBackClick
                        ) {

                            Icon(
                                imageVector =
                                    Icons.AutoMirrored.Filled.ArrowBack,

                                contentDescription =
                                    "Back",

                                tint =
                                    darkText
                            )
                        }


                        Text(
                            text =
                                routine?.name
                                    ?: "Routine Details",

                            fontSize =
                                22.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                darkText
                        )
                    }


                    if (
                        routine != null
                    ) {

                        Text(
                            text =
                                "${routine.sessions.size} AI-generated study sessions",

                            modifier =
                                Modifier.padding(
                                    start = 48.dp
                                ),

                            fontSize =
                                13.sp,

                            color =
                                greyText
                        )
                    }
                }
            }


            // ==========================================
            // NO ROUTINE SELECTED
            // ==========================================

            if (
                routine == null
            ) {

                item {

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal =
                                        18.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        border =
                            BorderStroke(
                                1.dp,
                                Color(0xFFE2E4EA)
                            )
                    ) {

                        Text(
                            text =
                                "No routine selected.",

                            modifier =
                                Modifier.padding(
                                    20.dp
                                ),

                            fontSize =
                                14.sp,

                            color =
                                greyText
                        )
                    }
                }
            }


            // ==========================================
            // ROUTINE SESSIONS
            // ==========================================

            if (
                routine != null
            ) {

                itemsIndexed(
                    items =
                        routine.sessions
                ) { index, session ->


                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal =
                                        18.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        border =
                            BorderStroke(
                                1.dp,
                                Color(0xFFE2E4EA)
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    16.dp
                                )
                        ) {

                            Text(
                                text =
                                    "Session ${index + 1}",

                                fontSize =
                                    11.sp,

                                color =
                                    blue,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        6.dp
                                    )
                            )


                            Text(
                                text =
                                    session.subject,

                                fontSize =
                                    17.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        10.dp
                                    )
                            )


                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Schedule,

                                    contentDescription =
                                        null,

                                    tint =
                                        greyText,

                                    modifier =
                                        Modifier.size(
                                            17.dp
                                        )
                                )

                                Text(
                                    text =
                                        "  ${session.startTime} - ${session.endTime}",

                                    fontSize =
                                        12.sp,

                                    color =
                                        greyText
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        7.dp
                                    )
                            )


                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.LocationOn,

                                    contentDescription =
                                        null,

                                    tint =
                                        greyText,

                                    modifier =
                                        Modifier.size(
                                            17.dp
                                        )
                                )

                                Text(
                                    text =
                                        "  ${session.location}",

                                    fontSize =
                                        12.sp,

                                    color =
                                        greyText
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        14.dp
                                    )
                            )


                            Text(
                                text =
                                    "Task",

                                fontSize =
                                    12.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        5.dp
                                    )
                            )


                            Text(
                                text =
                                    session.task,

                                fontSize =
                                    12.sp,

                                color =
                                    greyText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        12.dp
                                    )
                            )


                            Text(
                                text =
                                    "Why this session?",

                                fontSize =
                                    12.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        5.dp
                                    )
                            )


                            Text(
                                text =
                                    session.reason,

                                fontSize =
                                    12.sp,

                                color =
                                    greyText
                            )
                        }
                    }
                }
            }


            item {
                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )
            }
        }
    }
}
