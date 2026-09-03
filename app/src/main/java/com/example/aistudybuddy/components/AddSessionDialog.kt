package com.example.aistudybuddy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aistudybuddy.data.StudySession

@Composable
fun AddSessionDialog(
    onDismiss: () -> Unit,
    onAddSession: (String, String) -> Unit,
    existingSubject: String = "",
    existingTime: String = "",
    existingSessions: List<StudySession> = emptyList(),
    currentSessionIndex: Int? = null,
    isEditing: Boolean = false
) {

    var subject by remember { mutableStateOf(existingSubject) }

    val existingTimes = existingTime.split(" – ")

    var startTime by remember {
        mutableStateOf(
            if (existingTimes.size == 2) existingTimes[0] else ""
        )
    }

    var endTime by remember {
        mutableStateOf(
            if (existingTimes.size == 2) existingTimes[1] else ""
        )
    }

    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                if (isEditing) {
                    "Edit Study Session"
                } else {
                    "Add Study Session"
                }
            )
        },

        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                        errorMessage = ""
                    },
                    label = {
                        Text("Subject")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = {
                        startTime = it
                        errorMessage = ""
                    },
                    label = {
                        Text("Start Time")
                    },
                    placeholder = {
                        Text("e.g. 10:00 AM")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = {
                        endTime = it
                        errorMessage = ""
                    },
                    label = {
                        Text("End Time")
                    },
                    placeholder = {
                        Text("e.g. 11:00 AM")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red
                    )
                }
            }
        },

        confirmButton = {
            Button(
                onClick = {

                    when {

                        subject.isBlank() -> {
                            errorMessage = "Please enter a subject."
                        }

                        startTime.isBlank() -> {
                            errorMessage = "Please enter a start time."
                        }

                        endTime.isBlank() -> {
                            errorMessage = "Please enter an end time."
                        }

                        !isValidTime(startTime) -> {
                            errorMessage =
                                "Invalid start time. Use format: 10:00 AM"
                        }

                        !isValidTime(endTime) -> {
                            errorMessage =
                                "Invalid end time. Use format: 11:00 AM"
                        }

                        convertTimeToMinutes(endTime) <=
                                convertTimeToMinutes(startTime) -> {

                            errorMessage =
                                "End time must be later than start time."
                        }

                        hasOverlap(
                            startTime = startTime,
                            endTime = endTime,
                            existingSessions = existingSessions,
                            currentSessionIndex = currentSessionIndex
                        ) -> {

                            errorMessage =
                                "This session overlaps with another session."
                        }

                        else -> {

                            val combinedTime =
                                "$startTime – $endTime"

                            onAddSession(
                                subject.trim(),
                                combinedTime
                            )
                        }
                    }
                }
            ) {
                Text(
                    if (isEditing) {
                        "Save"
                    } else {
                        "Add"
                    }
                )
            }
        },

        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

private fun isValidTime(time: String): Boolean {

    val regex = Regex(
        "^([1-9]|1[0-2]):[0-5][0-9] (AM|PM)$"
    )

    return regex.matches(time.trim())
}

private fun convertTimeToMinutes(time: String): Int {

    val parts = time.trim().split(" ")

    val timePart = parts[0]
    val period = parts[1]

    val hourMinute = timePart.split(":")

    var hour = hourMinute[0].toInt()
    val minute = hourMinute[1].toInt()

    if (period == "AM") {
        if (hour == 12) {
            hour = 0
        }
    } else {
        if (hour != 12) {
            hour += 12
        }
    }

    return hour * 60 + minute
}

private fun hasOverlap(
    startTime: String,
    endTime: String,
    existingSessions: List<StudySession>,
    currentSessionIndex: Int?
): Boolean {

    val newStart = convertTimeToMinutes(startTime)
    val newEnd = convertTimeToMinutes(endTime)

    existingSessions.forEachIndexed { index, session ->

        // Skip the session currently being edited
        if (index == currentSessionIndex) {
            return@forEachIndexed
        }

        val times = session.time.split(" – ")

        if (times.size != 2) {
            return@forEachIndexed
        }

        val existingStart = convertTimeToMinutes(times[0])
        val existingEnd = convertTimeToMinutes(times[1])

        // Check whether the two time ranges overlap
        if (newStart < existingEnd && newEnd > existingStart) {
            return true
        }
    }

    return false
}