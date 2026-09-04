package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AddSessionDialog
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.components.PlannerActionButtons
import com.example.aistudybuddy.components.StudySessionCard
import com.example.aistudybuddy.components.WeekSelector
import com.example.aistudybuddy.data.StudyPlannerData
import com.example.aistudybuddy.data.StudySession
import java.time.LocalDate


@Composable
fun StudyPlannerScreen(
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onTimetableSetupClick: () -> Unit = {},
    onViewRoutineClick: () -> Unit = {},
    onFocusTimerClick: () -> Unit = {}
) {

    var editingIndex by remember {
        mutableStateOf<Int?>(null)
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }


    // ==========================================
    // GET SESSION FOR SELECTED DATE
    // AND ARRANGE BY START TIME
    // ==========================================

    val selectedDaySessions =
        StudyPlannerData.sessions
            .filter { session ->

                session.date ==
                        selectedDate ||

                        session.repeatDays
                            ?.contains(
                                selectedDate.dayOfWeek
                            ) == true
            }
            .sortedBy { session ->

                convertTimeToMinutes(
                    session.startTime
                )
            }


    Scaffold(
        containerColor = Color.White,

        bottomBar = {

            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onAssignmentsClick =
                    onAssignmentsClick,
                onPlannerClick =
                    onPlannerClick,
                onProgressClick =
                    onProgressClick,
                onProfileClick =
                    onProfileClick,
            )
        }

    ) { innerPadding ->


        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Color.White
                    )
                    .padding(
                        innerPadding
                    ),

            contentPadding =
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    8.dp
                )
        ) {


            // ==========================================
            // TOP HEADER
            // ==========================================

            item {

                Text(
                    text =
                        "Study Planner",
                    fontSize = 20.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(
                            0xFF252838
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            6.dp
                        )
                )
            }


            // ==========================================
            // WEEK SELECTOR
            // ==========================================

            item {

                WeekSelector(
                    onDateSelected = {
                            date ->

                        selectedDate =
                            date
                    }
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            14.dp
                        )
                )
            }


            // ==========================================
            // STUDY SESSIONS
            // ==========================================

            items(
                items =
                    selectedDaySessions,

                key = {
                        session ->

                    session.id
                }

            ) { session ->


                val index =
                    StudyPlannerData
                        .sessions
                        .indexOf(
                            session
                        )


                StudySessionCard(
                    title =
                        session.subject,

                    time =
                        "${session.startTime} - ${session.endTime}",

                    room =
                        session.room,

                    cardBackground =
                        sessionColors[
                            session.colorIndex
                        ],

                    titleColor =
                        sessionTitleColors[
                            session.colorIndex
                        ],

                    aiSuggested =
                        false,

                    onEdit = {

                        editingIndex =
                            index
                    },

                    onDelete = {

                        StudyPlannerData
                            .sessions
                            .removeAt(
                                index
                            )
                    }
                )
            }


            // ==========================================
            // PLANNER ACTION BUTTONS
            // ==========================================

            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            9.dp
                        )
                )


                PlannerActionButtons(

                    onAddSession = {
                            subject,
                            startTime,
                            endTime,
                            room,
                            repeatDays ->


                        StudyPlannerData
                            .sessions
                            .add(

                                StudySession(
                                    id =
                                        StudyPlannerData
                                            .generateId(),

                                    date =
                                        selectedDate,

                                    subject =
                                        subject,

                                    startTime =
                                        startTime,

                                    endTime =
                                        endTime,

                                    room =
                                        room,

                                    repeatDays =
                                        repeatDays,

                                    colorIndex =
                                        getNextAvailableColor(
                                            selectedDate
                                        )
                                )
                            )
                    },


                    onViewRoutineClick = {

                        onViewRoutineClick()
                    },


                    existingSessions =
                        selectedDaySessions
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            80.dp
                        )
                )
            }
        }
    }


    // ==========================================
    // EDIT SESSION DIALOG
    // ==========================================

    editingIndex
        ?.let { index ->


            val session =
                StudyPlannerData
                    .sessions[
                    index
                ]


            AddSessionDialog(

                onDismiss = {

                    editingIndex =
                        null
                },


                existingSubject =
                    session.subject,


                existingStartTime =
                    session.startTime,


                existingEndTime =
                    session.endTime,


                existingRoom =
                    session.room,


                existingRepeatDays =
                    session.repeatDays,


                existingSessions =
                    selectedDaySessions,


                currentSessionIndex =
                    selectedDaySessions
                        .indexOf(
                            session
                        ),


                isEditing =
                    true,


                onAddSession = {
                        subject,
                        startTime,
                        endTime,
                        room,
                        repeatDays ->


                    StudyPlannerData
                        .sessions[
                        index
                    ] =

                        StudySession(
                            id =
                                session.id,

                            date =
                                session.date,

                            subject =
                                subject,

                            startTime =
                                startTime,

                            endTime =
                                endTime,

                            room =
                                room,

                            repeatDays =
                                repeatDays,

                            colorIndex =
                                session.colorIndex
                        )


                    editingIndex =
                        null
                }
            )
        }
}



// ==========================================
// SESSION CARD BACKGROUND COLORS
// ==========================================

private val sessionColors =
    listOf(

        Color(
            0xFFFFF0F5
        ),

        Color(
            0xFFF6EDFF
        ),

        Color(
            0xFFECFFF0
        ),

        Color(
            0xFFFFFFE8
        ),

        Color(
            0xFFEAF7FF
        ),

        Color(
            0xFFFFF1E6
        ),

        Color(
            0xFFF0F0FF
        ),

        Color(
            0xFFEFFFF7
        ),

        Color(
            0xFFFFF0E8
        ),

        Color(
            0xFFEFF7FF
        )
    )



// ==========================================
// SESSION TITLE COLORS
// ==========================================

private val sessionTitleColors =
    listOf(

        Color(
            0xFFFF3D8D
        ),

        Color(
            0xFFB565F5
        ),

        Color(
            0xFF45D878
        ),

        Color(
            0xFFD0D000
        ),

        Color(
            0xFF2997D6
        ),

        Color(
            0xFFFF8A3D
        ),

        Color(
            0xFF6565D8
        ),

        Color(
            0xFF22AA77
        ),

        Color(
            0xFFE56B4A
        ),

        Color(
            0xFF4285D4
        )
    )



// ==========================================
// GET NEXT AVAILABLE COLOR
// ==========================================

private fun getNextAvailableColor(
    date: LocalDate
): Int {

    val usedColors =
        StudyPlannerData
            .sessions
            .filter {

                it.date ==
                        date
            }
            .map {

                it.colorIndex
            }
            .toSet()


    return sessionColors
        .indices
        .firstOrNull {

            it !in
                    usedColors

        } ?: 0
}



// ==========================================
// CONVERT TIME TO MINUTES
// USED TO SORT STUDY SESSIONS
// ==========================================

private fun convertTimeToMinutes(
    time: String
): Int {

    val parts =
        time
            .trim()
            .split(" ")


    val timePart =
        parts[0]


    val amPm =
        parts[1]


    val hourMinute =
        timePart
            .split(":")


    var hour =
        hourMinute[0]
            .toInt()


    val minute =
        hourMinute[1]
            .toInt()


    if (
        amPm.equals(
            "PM",
            ignoreCase = true
        ) &&
        hour != 12
    ) {

        hour += 12
    }


    if (
        amPm.equals(
            "AM",
            ignoreCase = true
        ) &&
        hour == 12
    ) {

        hour = 0
    }


    return hour * 60 + minute
}