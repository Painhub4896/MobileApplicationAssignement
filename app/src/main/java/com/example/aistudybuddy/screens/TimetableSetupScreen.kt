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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.UploadFile
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.TimetableEntry
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableSetupScreen(
    existingEntries: List<TimetableEntry> = emptyList(),
    onAddClass: (TimetableEntry) -> Unit,
    onViewTimetable: () -> Unit
) {

    val subjects = listOf(
        "Biology",
        "Mathematics",
        "Physics",
        "Chemistry",
        "English",
        "Computer Science"
    )

    val days = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday"
    )

    var subject by remember {
        mutableStateOf("Biology")
    }

    var day by remember {
        mutableStateOf("Monday")
    }

    var startTime by remember {
        mutableStateOf("9:00 AM")
    }

    var endTime by remember {
        mutableStateOf("10:00 AM")
    }

    // Let user enter any room
    var room by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val primaryBlue = Color(0xFF4169E1)
    val darkText = Color(0xFF191B23)
    val greyText = Color(0xFF757985)
    val pageBackground = Color(0xFFF7F8FC)
    val softBlue = Color(0xFFF0F4FF)
    val borderGrey = Color(0xFFE0E3EB)

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

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

            // Header stays at the top
            AppHeader()

            // Everything under header can scroll
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
                    text = "Set Up Timetable",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )

                Text(
                    text = "Add your weekly classes and let AIStudyBuddy plan your study time around them.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = greyText
                )


                // =====================================================
                // INFORMATION CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = softBlue
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
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
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = primaryBlue,
                                modifier = Modifier
                                    .padding(9.dp)
                                    .size(22.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                        ) {

                            Text(
                                text = "Add your class schedule",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = darkText
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "You can add multiple classes to your timetable.",
                                fontSize = 11.sp,
                                color = greyText
                            )
                        }
                    }
                }


                // =====================================================
                // CLASS DETAILS CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFEAECF1)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {

                        Text(
                            text = "Class Details",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = darkText
                        )


                        // Subject
                        SelectionField(
                            label = "Subject",
                            selected = subject,
                            options = subjects,
                            icon = Icons.Default.MenuBook,
                            onSelected = {
                                subject = it
                            }
                        )


                        // Day
                        SelectionField(
                            label = "Day",
                            selected = day,
                            options = days,
                            icon = Icons.Default.CalendarMonth,
                            onSelected = {
                                day = it
                            }
                        )


                        // Any Start Time
                        TimeSelectionField(
                            label = "Start Time",
                            selectedTime = startTime,
                            onTimeSelected = {
                                startTime = it
                            }
                        )


                        // Any End Time
                        TimeSelectionField(
                            label = "End Time",
                            selectedTime = endTime,
                            onTimeSelected = {
                                endTime = it
                            }
                        )


                        // User enters room manually
                        OutlinedTextField(
                            value = room,
                            onValueChange = {
                                room = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = {
                                Text("Room / Location")
                            },
                            placeholder = {
                                Text(
                                    text = "e.g. B203, Lab 5, DK A"
                                )
                            },
                            leadingIcon = {

                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = primaryBlue
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }


                // =====================================================
                // ADD CLASS BUTTON
                // =====================================================

                Button(
                    onClick = {

                        if (room.isBlank()) {

                            scope.launch {

                                snackbarHostState.showSnackbar(
                                    "Please enter the room or location"
                                )
                            }

                        } else {

                            val newStart =
                                timeToMinutes(startTime)

                            val newEnd =
                                timeToMinutes(endTime)


                            if (
                                newStart == -1 ||
                                newEnd == -1
                            ) {

                                scope.launch {

                                    snackbarHostState.showSnackbar(
                                        "Invalid time selected"
                                    )
                                }

                            } else if (
                                newEnd <= newStart
                            ) {

                                scope.launch {

                                    snackbarHostState.showSnackbar(
                                        "End time must be later than start time"
                                    )
                                }

                            } else {

                                // Check timetable clash
                                val hasClash =
                                    existingEntries.any { entry ->

                                        if (
                                            entry.day != day
                                        ) {

                                            false

                                        } else {

                                            val oldStart =
                                                timeToMinutes(
                                                    entry.startTime
                                                )

                                            val oldEnd =
                                                timeToMinutes(
                                                    entry.endTime
                                                )

                                            oldStart < newEnd &&
                                                    newStart < oldEnd
                                        }
                                    }


                                if (hasClash) {

                                    scope.launch {

                                        snackbarHostState.showSnackbar(
                                            "This class clashes with another $day class"
                                        )
                                    }

                                } else {

                                    onAddClass(
                                        TimetableEntry(
                                            subject = subject,
                                            day = day,
                                            startTime = startTime,
                                            endTime = endTime,
                                            room = room.trim()
                                        )
                                    )


                                    scope.launch {

                                        snackbarHostState.showSnackbar(
                                            "$subject added successfully"
                                        )
                                    }


                                    // Clear room after successful add
                                    room = ""
                                }
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "  Add Class",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }


                // =====================================================
                // QUICK IMPORT SECTION
                // =====================================================

                Text(
                    text = "Quick Import",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFEAECF1)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = "Already have a timetable?",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = darkText
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Import your existing class schedule using an image or PDF.",
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = greyText
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {


                            // IMAGE BUTTON
                            OutlinedButton(
                                onClick = {

                                    scope.launch {

                                        snackbarHostState.showSnackbar(
                                            "Image import will be connected next"
                                        )
                                    }
                                },

                                modifier = Modifier
                                    .weight(1f)
                                    .height(54.dp),

                                shape = RoundedCornerShape(12.dp),

                                border = BorderStroke(
                                    width = 1.dp,
                                    color = primaryBlue
                                )
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Import Image",
                                    tint = primaryBlue,
                                    modifier = Modifier.size(19.dp)
                                )

                                Text(
                                    text = "  Image",
                                    color = primaryBlue,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }


                            // PDF BUTTON
                            OutlinedButton(
                                onClick = {

                                    scope.launch {

                                        snackbarHostState.showSnackbar(
                                            "PDF upload will be connected next"
                                        )
                                    }
                                },

                                modifier = Modifier
                                    .weight(1f)
                                    .height(54.dp),

                                shape = RoundedCornerShape(12.dp),

                                border = BorderStroke(
                                    width = 1.dp,
                                    color = primaryBlue
                                )
                            ) {

                                Icon(
                                    imageVector = Icons.Default.UploadFile,
                                    contentDescription = "Upload PDF",
                                    tint = primaryBlue,
                                    modifier = Modifier.size(19.dp)
                                )

                                Text(
                                    text = "  PDF",
                                    color = primaryBlue,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }


                // =====================================================
                // WEEKLY TIMETABLE BUTTON
                // =====================================================

                OutlinedButton(
                    onClick = onViewTimetable,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                    shape = RoundedCornerShape(14.dp),

                    border = BorderStroke(
                        width = 1.dp,
                        color = primaryBlue
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = primaryBlue
                    )

                    Text(
                        text = "  View Weekly Timetable",
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }


                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}


// =============================================================
// SUBJECT AND DAY DROPDOWN
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectionField(
    label: String,
    selected: String,
    options: List<String>,
    icon: ImageVector,
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

            leadingIcon = {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF4169E1)
                )
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


// =============================================================
// TIME PICKER
// =============================================================

@Composable
private fun TimeSelectionField(
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {

    val context =
        LocalContext.current

    OutlinedButton(
        onClick = {

            val currentMinutes =
                timeToMinutes(selectedTime)


            var defaultHour = 9

            var defaultMinute = 0


            if (
                currentMinutes >= 0
            ) {

                defaultHour =
                    currentMinutes / 60

                defaultMinute =
                    currentMinutes % 60
            }


            TimePickerDialog(
                context,

                { _, hourOfDay, minute ->

                    onTimeSelected(
                        formatTime(
                            hourOfDay,
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
            .height(58.dp),

        shape = RoundedCornerShape(12.dp),

        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFB8BCC7)
        )
    ) {

        Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = Color(0xFF4169E1),
            modifier = Modifier.size(19.dp)
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
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF171A24)
            )
        }


        Text(
            text = "Choose",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4169E1)
        )
    }
}


// =============================================================
// FORMAT TIME
// =============================================================

private fun formatTime(
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


// =============================================================
// CONVERT TIME INTO MINUTES
// =============================================================

private fun timeToMinutes(
    time: String
): Int {

    return try {

        val parts =
            time
                .trim()
                .split(" ")


        if (
            parts.size != 2
        ) {

            return -1
        }


        val clock =
            parts[0]
                .split(":")


        var hour =
            clock[0]
                .toInt()


        val minute =
            clock[1]
                .toInt()


        val period =
            parts[1]
                .uppercase()


        if (
            hour == 12
        ) {

            hour = 0
        }


        if (
            period == "PM"
        ) {

            hour += 12
        }


        hour * 60 + minute

    } catch (e: Exception) {

        -1
    }
}


// =============================================================
// PREVIEW
// =============================================================

@Preview(
    showBackground = true,
    widthDp = 393,
    heightDp = 852
)
@Composable
fun TimetableSetupScreenPreview() {

    AIStudyBuddyTheme {

        TimetableSetupScreen(
            existingEntries = emptyList(),
            onAddClass = {},
            onViewTimetable = {}
        )
    }
}