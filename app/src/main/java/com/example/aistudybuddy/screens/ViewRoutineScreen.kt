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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.aistudybuddy.data.GeneratedStudySession


@Composable
fun ViewRoutineScreen(
    sessions: List<GeneratedStudySession>,
    onAddRoutineClick: () -> Unit = {},
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
            // TITLE
            // ==========================================

            item {

                Column(
                    modifier =
                        Modifier.padding(
                            start = 18.dp,
                            end = 18.dp,
                            top = 18.dp
                        )
                ) {

                    Text(
                        text =
                            "Study Routine",

                        fontSize =
                            24.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            darkText
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                4.dp
                            )
                    )


                    Text(
                        text =
                            "View your AI-generated study routine.",

                        fontSize =
                            13.sp,

                        color =
                            greyText
                    )
                }
            }


            // ==========================================
            // EMPTY ROUTINE
            // ==========================================

            if (
                sessions.isEmpty()
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
                                Color(
                                    0xFFE2E4EA
                                )
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        24.dp
                                    ),

                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text =
                                    "No routine added yet",

                                fontSize =
                                    16.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        6.dp
                                    )
                            )


                            Text(
                                text =
                                    "Create a study routine using AI.",

                                fontSize =
                                    13.sp,

                                color =
                                    greyText
                            )
                        }
                    }
                }
            }


            // ==========================================
            // ROUTINE SESSIONS
            // ==========================================

            itemsIndexed(
                items =
                    sessions
            ) {
                    index,
                    session ->


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
                            Color(
                                0xFFE2E4EA
                            )
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(
                                16.dp
                            )
                    ) {


                        // ==========================================
                        // SESSION NUMBER
                        // ==========================================

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
                                    5.dp
                                )
                        )


                        // ==========================================
                        // SUBJECT
                        // ==========================================

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
                                    8.dp
                                )
                        )


                        // ==========================================
                        // TIME
                        // ==========================================

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Schedule,

                                contentDescription =
                                    "Time",

                                tint =
                                    greyText,

                                modifier =
                                    Modifier.size(
                                        17.dp
                                    )
                            )


                            Text(
                                text =
                                    " ${session.startTime} - ${session.endTime}",

                                fontSize =
                                    13.sp,

                                color =
                                    greyText
                            )
                        }


                        // ==========================================
                        // LOCATION
                        // ==========================================

                        if (
                            session.location.isNotBlank()
                        ) {

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        5.dp
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
                                        "Location",

                                    tint =
                                        greyText,

                                    modifier =
                                        Modifier.size(
                                            17.dp
                                        )
                                )


                                Text(
                                    text =
                                        " ${session.location}",

                                    fontSize =
                                        13.sp,

                                    color =
                                        greyText
                                )
                            }
                        }


                        Spacer(
                            modifier =
                                Modifier.height(
                                    10.dp
                                )
                        )


                        // ==========================================
                        // TASK
                        // ==========================================

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


                        Text(
                            text =
                                session.task,

                            fontSize =
                                13.sp,

                            color =
                                greyText
                        )
                    }
                }
            }


            // ==========================================
            // ADD ROUTINE BUTTON
            // ==========================================

            item {

                Button(
                    onClick = {

                        onAddRoutineClick()
                    },

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal =
                                    18.dp
                            )
                            .height(
                                52.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        ),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                blue
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            "Add Routine"
                    )


                    Text(
                        text =
                            "  Add Routine",

                        fontWeight =
                            FontWeight.Bold
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )
            }
        }
    }
}