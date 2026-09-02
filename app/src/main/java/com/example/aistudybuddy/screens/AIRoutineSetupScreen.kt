package com.example.aistudybuddy.screens

import android.app.TimePickerDialog
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.TimetableEntry
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import com.example.aistudybuddy.viewmodel.AIRoutineViewModel

@Composable
fun AIRoutineSetupScreen(
    timetableEntries: List<TimetableEntry>,
    aiRoutineViewModel: AIRoutineViewModel,
    onGenerateClick: () -> Unit = {}
) {

    var availableStart by remember {
        mutableStateOf("7:00 PM")
    }

    var availableEnd by remember {
        mutableStateOf("10:00 PM")
    }

    var sessionLength by remember {
        mutableStateOf("45 minutes")
    }

    var difficulty by remember {
        mutableStateOf("Balanced")
    }

    var upcomingTest by remember {
        mutableStateOf("")
    }

    var assignment by remember {
        mutableStateOf("")
    }

    val primaryBlue = Color(0xFF4169E1)
    val purple = Color(0xFF7C3AED)
    val pageBackground = Color(0xFFF7F8FC)
    val textPrimary = Color(0xFF171A24)
    val textSecondary = Color(0xFF747984)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Planner"
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBackground)
                .padding(innerPadding)
        ) {

            AppHeader()

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
                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                // =====================================================
                // TITLE
                // =====================================================

                Text(
                    text = "Create My AI Routine",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )

                Text(
                    text = "Tell us your preferences and AIStudyBuddy will build a personalized study plan.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = textSecondary
                )


                // =====================================================
                // TIMETABLE SYNC CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F4FF)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFDDE5FF)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = primaryBlue,
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
                                text = "Timetable",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = textPrimary
                            )

                            Text(
                                text =
                                    if (timetableEntries.isEmpty()) {
                                        "No classes found"
                                    } else {
                                        "${timetableEntries.size} classes synced successfully"
                                    },
                                fontSize = 11.sp,
                                color = textSecondary
                            )
                        }

                        Icon(
                            imageVector =
                                Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF35A853),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                // =====================================================
                // UPCOMING WORK
                // =====================================================

                Text(
                    text = "Upcoming Work",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE6E8EF)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        OutlinedTextField(
                            value = upcomingTest,
                            onValueChange = {
                                upcomingTest = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = {
                                Text("Upcoming Test")
                            },
                            placeholder = {
                                Text(
                                    "e.g. Biology Test"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector =
                                        Icons.Default.School,
                                    contentDescription = null,
                                    tint = primaryBlue
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        )


                        OutlinedTextField(
                            value = assignment,
                            onValueChange = {
                                assignment = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = {
                                Text("Assignment / Project")
                            },
                            placeholder = {
                                Text(
                                    "e.g. Database Project"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector =
                                        Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = Color(0xFFFF9800)
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }


                // =====================================================
                // PREFERENCES
                // =====================================================

                Text(
                    text = "Your Preferences",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE6E8EF)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(13.dp)
                    ) {

                        // ---------------------------------------------
                        // AVAILABLE START
                        // ---------------------------------------------

                        RoutineTimeField(
                            label = "Available From",
                            selectedTime = availableStart,
                            onTimeSelected = {
                                availableStart = it
                            }
                        )


                        // ---------------------------------------------
                        // AVAILABLE END
                        // ---------------------------------------------

                        RoutineTimeField(
                            label = "Available Until",
                            selectedTime = availableEnd,
                            onTimeSelected = {
                                availableEnd = it
                            }
                        )


                        // ---------------------------------------------
                        // SESSION LENGTH
                        // ---------------------------------------------

                        RoutineDropdown(
                            label = "Session Length",
                            selected = sessionLength,
                            options = listOf(
                                "25 minutes",
                                "30 minutes",
                                "45 minutes",
                                "60 minutes",
                                "90 minutes"
                            ),
                            onSelected = {
                                sessionLength = it
                            }
                        )


                        // ---------------------------------------------
                        // DIFFICULTY
                        // ---------------------------------------------

                        RoutineDropdown(
                            label = "Difficulty",
                            selected = difficulty,
                            options = listOf(
                                "Light",
                                "Balanced",
                                "Intensive"
                            ),
                            onSelected = {
                                difficulty = it
                            }
                        )
                    }
                }


                // =====================================================
                // SMALL AI INFORMATION CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF7F3FF)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE8DCFF)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(13.dp),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = purple,
                            modifier = Modifier.size(22.dp)
                        )

                        Column(
                            modifier = Modifier.padding(
                                start = 10.dp
                            )
                        ) {

                            Text(
                                text = "Smart Scheduling",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = textPrimary
                            )

                            Text(
                                text = "Your timetable and preferences will be used to avoid class-time clashes.",
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                color = textSecondary
                            )
                        }
                    }
                }


                // =====================================================
                // GENERATE BUTTON
                // =====================================================

                Button(
                    onClick = onGenerateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purple
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(21.dp)
                    )

                    Text(
                        text = "  Generate with Gemini",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Text(
                    text = "Your timetable is only used to personalize your study routine.",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    textAlign =
                        androidx.compose.ui.text.style.TextAlign.Center,
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
// TIME PICKER
// =====================================================================

@Composable
private fun RoutineTimeField(
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {

    val context =
        LocalContext.current

    OutlinedButton(
        onClick = {

            val minutes =
                routineTimeToMinutes(
                    selectedTime
                )

            val defaultHour =
                if (minutes >= 0) {
                    minutes / 60
                } else {
                    19
                }

            val defaultMinute =
                if (minutes >= 0) {
                    minutes % 60
                } else {
                    0
                }


            TimePickerDialog(
                context,
                { _, hour, minute ->

                    onTimeSelected(
                        routineFormatTime(
                            hour,
                            minute
                        )
                    )
                },
                defaultHour,
                defaultMinute,
                false
            ).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            Color(0xFFB8BCC7)
        )
    ) {

        Icon(
            imageVector =
                Icons.Default.Schedule,
            contentDescription = null,
            tint = Color(0xFF4169E1)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            horizontalAlignment =
                Alignment.Start
        ) {

            Text(
                text = label,
                fontSize = 10.sp,
                color = Color(0xFF777B86)
            )

            Text(
                text = selectedTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF171A24)
            )
        }

        Text(
            text = "Choose",
            color = Color(0xFF4169E1),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// =====================================================================
// DROPDOWN
// =====================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutineDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            label = {
                Text(label)
            },
            trailingIcon = {

                ExposedDropdownMenuDefaults
                    .TrailingIcon(
                        expanded = expanded
                    )
            },
            shape = RoundedCornerShape(12.dp)
        )


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(option)
                    },
                    onClick = {

                        onSelected(option)

                        expanded = false
                    }
                )
            }
        }
    }
}


// =====================================================================
// FORMAT TIME
// =====================================================================

private fun routineFormatTime(
    hour: Int,
    minute: Int
): String {

    val period =
        if (hour >= 12) {
            "PM"
        } else {
            "AM"
        }


    val displayHour =
        when {

            hour == 0 ->
                12

            hour > 12 ->
                hour - 12

            else ->
                hour
        }


    return String.format(
        "%d:%02d %s",
        displayHour,
        minute,
        period
    )
}


// =====================================================================
// TIME TO MINUTES
// =====================================================================

private fun routineTimeToMinutes(
    time: String
): Int {

    return try {

        val parts =
            time.trim().split(" ")

        val clock =
            parts[0].split(":")

        var hour =
            clock[0].toInt()

        val minute =
            clock[1].toInt()

        val period =
            parts[1].uppercase()


        if (hour == 12) {
            hour = 0
        }

        if (period == "PM") {
            hour += 12
        }

        hour * 60 + minute

    } catch (e: Exception) {

        -1
    }
}

