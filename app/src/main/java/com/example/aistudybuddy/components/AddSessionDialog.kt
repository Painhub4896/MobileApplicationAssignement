package com.example.aistudybuddy.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.data.StudySession
import java.time.DayOfWeek
import java.util.Calendar


@Composable
fun AddSessionDialog(
    onDismiss: () -> Unit,
    existingSubject: String = "",
    existingStartTime: String = "",
    existingEndTime: String = "",
    existingRoom: String? = null,
    existingRepeatDays: Set<DayOfWeek>? = null,
    existingSessions: List<StudySession> = emptyList(),
    currentSessionIndex: Int? = null,
    isEditing: Boolean = false,
    onAddSession: (
        String,
        String,
        String,
        String?,
        Set<DayOfWeek>?
    ) -> Unit
) {

    val context = LocalContext.current

    val blue = Color(0xFF4169E1)
    val darkText = Color(0xFF252838)

    var subject by remember {
        mutableStateOf(existingSubject)
    }

    var startTime by remember {
        mutableStateOf(existingStartTime)
    }

    var endTime by remember {
        mutableStateOf(existingEndTime)
    }

    var room by remember {
        mutableStateOf(existingRoom ?: "")
    }

    var selectedDays by remember {
        mutableStateOf(
            existingRepeatDays ?: emptySet()
        )
    }

    var errorMessage by remember {
        mutableStateOf("")
    }


    fun openStartTimePicker() {

        val calendar = Calendar.getInstance()

        TimePickerDialog(
            context,
            { _, hour, minute ->

                startTime =
                    formatSelectedTime(
                        hour,
                        minute
                    )

                errorMessage = ""
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }


    fun openEndTimePicker() {

        val calendar = Calendar.getInstance()

        TimePickerDialog(
            context,
            { _, hour, minute ->

                endTime =
                    formatSelectedTime(
                        hour,
                        minute
                    )

                errorMessage = ""
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }


    AlertDialog(
        onDismissRequest = onDismiss,

        title = {

            Text(
                text =
                    if (isEditing) {
                        "Edit Study Session"
                    } else {
                        "Add Study Session"
                    },
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        },

        text = {

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                // ==========================================
                // SUBJECT
                // ==========================================

                Text(
                    text = "Subject",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkText
                )

                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                        errorMessage = ""
                    },
                    placeholder = {
                        Text(
                            "Enter subject"
                        )
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape =
                        RoundedCornerShape(10.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = blue
                        )
                )


                // ==========================================
                // REPEAT ON
                // ==========================================

                Text(
                    text = "Repeat On (Optional)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkText
                )

                FlowRow(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(6.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(4.dp)
                ) {

                    listOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY,
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                    ).forEach { day ->

                        FilterChip(
                            selected = day in selectedDays,

                            onClick = {

                                selectedDays =
                                    if (day in selectedDays) {

                                        selectedDays - day

                                    } else {

                                        selectedDays + day
                                    }
                            },

                            modifier =
                                Modifier.width(62.dp),

                            label = {

                                Text(
                                    text =
                                        getDayLabel(
                                            day
                                        ),
                                    maxLines = 1
                                )
                            }
                        )
                    }
                }


                // ==========================================
                // START TIME
                // ==========================================

                Text(
                    text = "Start Time",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkText
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Select start time"
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                openStartTimePicker()
                            },
                    enabled = false,
                    shape =
                        RoundedCornerShape(10.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            disabledTextColor =
                                darkText,
                            disabledBorderColor =
                                Color(0xFFD5D7DE),
                            disabledPlaceholderColor =
                                Color.Gray
                        )
                )


                // ==========================================
                // END TIME
                // ==========================================

                Text(
                    text = "End Time",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkText
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Select end time"
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                openEndTimePicker()
                            },
                    enabled = false,
                    shape =
                        RoundedCornerShape(10.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            disabledTextColor =
                                darkText,
                            disabledBorderColor =
                                Color(0xFFD5D7DE),
                            disabledPlaceholderColor =
                                Color.Gray
                        )
                )


                // ==========================================
                // ROOM
                // ==========================================

                Text(
                    text = "Room (Optional)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkText
                )

                OutlinedTextField(
                    value = room,
                    onValueChange = {
                        room = it
                    },
                    placeholder = {
                        Text(
                            "Enter room"
                        )
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape =
                        RoundedCornerShape(10.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = blue
                        )
                )


                // ==========================================
                // ERROR MESSAGE
                // ==========================================

                if (
                    errorMessage.isNotBlank()
                ) {

                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        },

        confirmButton = {

            Button(
                onClick = {

                    // ==========================================
                    // SUBJECT VALIDATION
                    // ==========================================

                    if (
                        subject.isBlank()
                    ) {

                        errorMessage =
                            "Please enter a subject."

                        return@Button
                    }




                    // ==========================================
                    // START TIME VALIDATION
                    // ==========================================

                    if (
                        startTime.isBlank()
                    ) {

                        errorMessage =
                            "Please select a start time."

                        return@Button
                    }


                    // ==========================================
                    // END TIME VALIDATION
                    // ==========================================

                    if (
                        endTime.isBlank()
                    ) {

                        errorMessage =
                            "Please select an end time."

                        return@Button
                    }


                    // ==========================================
                    // END TIME CANNOT BE EARLIER
                    // ==========================================

                    val startMinutes =
                        convertTimeToMinutes(
                            startTime
                        )

                    val endMinutes =
                        convertTimeToMinutes(
                            endTime
                        )


                    if (
                        endMinutes <= startMinutes
                    ) {

                        errorMessage =
                            "End time must be later than start time."

                        return@Button
                    }

                    // ==========================================
// CHECK FOR OVERLAPPING SESSIONS
// ==========================================

                    val hasTimeConflict =
                        existingSessions
                            .filterIndexed { index, _ ->
                                index != currentSessionIndex
                            }
                            .any { existingSession ->

                                val existingStart =
                                    convertTimeToMinutes(
                                        existingSession.startTime
                                    )

                                val existingEnd =
                                    convertTimeToMinutes(
                                        existingSession.endTime
                                    )

                                startMinutes < existingEnd &&
                                        endMinutes > existingStart
                            }


                    if (hasTimeConflict) {

                        errorMessage =
                            "This time overlaps with another study session."

                        return@Button
                    }


                    // ==========================================
                    // OPTIONAL ROOM
                    // ==========================================

                    val finalRoom =
                        room
                            .trim()
                            .ifBlank {
                                null
                            }


                    // ==========================================
                    // OPTIONAL REPEAT DAYS
                    // ==========================================

                    val finalRepeatDays =
                        selectedDays
                            .ifEmpty {
                                null
                            }


                    // ==========================================
                    // SAVE SESSION
                    // ==========================================

                    onAddSession(
                        subject.trim(),
                        startTime,
                        endTime,
                        finalRoom,
                        finalRepeatDays
                    )
                },

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = blue
                    )
            ) {

                Text(
                    text =
                        if (isEditing) {
                            "Save"
                        } else {
                            "Add Session"
                        }
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Cancel",
                    color = blue
                )
            }
        }
    )
}


// ==========================================
// FORMAT TIME
// ==========================================

private fun formatSelectedTime(
    hour: Int,
    minute: Int
): String {

    val amPm =
        if (hour < 12) {
            "AM"
        } else {
            "PM"
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
        amPm
    )
}


// ==========================================
// CONVERT TIME TO MINUTES
// USED FOR TIME VALIDATION
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


// ==========================================
// DAY LABEL
// ==========================================

private fun getDayLabel(
    day: DayOfWeek
): String {

    return when (day) {

        DayOfWeek.MONDAY ->
            "Mon"

        DayOfWeek.TUESDAY ->
            "Tue"

        DayOfWeek.WEDNESDAY ->
            "Wed"

        DayOfWeek.THURSDAY ->
            "Thu"

        DayOfWeek.FRIDAY ->
            "Fri"

        DayOfWeek.SATURDAY ->
            "Sat"

        DayOfWeek.SUNDAY ->
            "Sun"
    }
}