package com.example.aistudybuddy.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.StudyRoutine


@Composable
fun ViewRoutineScreen(
    routines: List<StudyRoutine>,
    onViewRoutineClick: (StudyRoutine) -> Unit = {},
    onDeleteRoutineClick: (StudyRoutine) -> Unit = {},
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

    val deleteRed =
        Color(0xFFD32F2F)


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
                            "View or delete your AI-generated study routines.",

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
                routines.isEmpty()
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
            // ROUTINE LIST
            // ==========================================

            items(
                items =
                    routines,

                key = { routine ->
                    routine.id
                }
            ) { routine ->


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
                                    16.dp
                                )
                    ) {


                        // ==========================================
                        // ROUTINE INFORMATION
                        // ==========================================

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {


                            Card(
                                shape =
                                    RoundedCornerShape(
                                        10.dp
                                    ),

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            Color(
                                                0xFFF1EDFF
                                            )
                                    )
                            ) {


                                Icon(
                                    imageVector =
                                        Icons.Default.AutoAwesome,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(
                                            0xFF7C3AED
                                        ),

                                    modifier =
                                        Modifier
                                            .padding(
                                                9.dp
                                            )
                                            .size(
                                                21.dp
                                            )
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.width(
                                        12.dp
                                    )
                            )


                            Column(
                                modifier =
                                    Modifier.weight(
                                        1f
                                    )
                            ) {


                                Text(
                                    text =
                                        routine.name,

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
                                            3.dp
                                        )
                                )


                                Text(
                                    text =
                                        "${routine.sessions.size} study sessions",

                                    fontSize =
                                        12.sp,

                                    color =
                                        greyText
                                )
                            }
                        }


                        Spacer(
                            modifier =
                                Modifier.height(
                                    16.dp
                                )
                        )


                        // ==========================================
                        // VIEW / DELETE
                        // ==========================================

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    10.dp
                                ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {


                            Button(
                                onClick = {

                                    onViewRoutineClick(
                                        routine
                                    )
                                },

                                modifier =
                                    Modifier
                                        .weight(
                                            1.35f
                                        )
                                        .height(
                                            50.dp
                                        ),

                                shape =
                                    RoundedCornerShape(
                                        10.dp
                                    ),

                                contentPadding =
                                    PaddingValues(
                                        horizontal =
                                            10.dp,

                                        vertical =
                                            0.dp
                                    ),

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            blue
                                    )
                            ) {


                                Icon(
                                    imageVector =
                                        Icons.Default.Visibility,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(
                                            17.dp
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
                                        "View Routine",

                                    fontSize =
                                        12.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    maxLines =
                                        1
                                )
                            }


                            OutlinedButton(
                                onClick = {

                                    onDeleteRoutineClick(
                                        routine
                                    )
                                },

                                modifier =
                                    Modifier
                                        .weight(
                                            1f
                                        )
                                        .height(
                                            50.dp
                                        ),

                                shape =
                                    RoundedCornerShape(
                                        10.dp
                                    ),

                                contentPadding =
                                    PaddingValues(
                                        horizontal =
                                            10.dp,

                                        vertical =
                                            0.dp
                                    ),

                                border =
                                    BorderStroke(
                                        1.dp,
                                        deleteRed
                                    )
                            ) {


                                Icon(
                                    imageVector =
                                        Icons.Default.Delete,

                                    contentDescription =
                                        null,

                                    tint =
                                        deleteRed,

                                    modifier =
                                        Modifier.size(
                                            17.dp
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
                                        "Delete",

                                    fontSize =
                                        12.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        deleteRed,

                                    maxLines =
                                        1
                                )
                            }
                        }
                    }
                }
            }


            // ==========================================
            // ADD ROUTINE
            // ==========================================

            item {


                Button(
                    onClick =
                        onAddRoutineClick,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                start =
                                    18.dp,

                                end =
                                    18.dp,

                                bottom =
                                    18.dp
                            )
                            .height(
                                50.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            12.dp
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
                            null,

                        modifier =
                            Modifier.size(
                                20.dp
                            )
                    )


                    Text(
                        text =
                            "  Add Routine",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}