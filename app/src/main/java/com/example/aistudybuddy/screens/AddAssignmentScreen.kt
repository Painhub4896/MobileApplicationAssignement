package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(
    onCancelClick: () -> Unit,
    onAddClick: (String, String, String, String) -> Unit,
    initialTitle: String = "",
    initialSubject: String = "",
    initialDifficulty: String = "",
    initialDate: String = "",
    screenTitle: String = "Add Assignment",
    buttonText: String = "Add",
) {
    val blue = Color(0xFF4169E1)
    val darkText = Color(0xFF252838)
    val greyText = Color(0xFF767987)
    val backgroundColor = Color(0xFFF9FAFC)

    // Assignment Title " "
    var assignmentTitle by remember { mutableStateOf(initialTitle) }

    // SubjectListExample
    val subjectList = remember {
        mutableStateListOf(
            "Networking",
            "Database",
            "Mobile Application",
            "Problem Solving",
            "Artificial Intelligence"
        )
    }

    // 3-Difficulty level
    val difficultyList = remember {
        mutableStateListOf(
            "Easy",
            "Medium",
            "Hard"
        )
    }

    // Selected Subject
    var selectedSubject by remember { mutableStateOf(initialSubject) }

    // Dropdown
    var subjectMenuExpended by remember { mutableStateOf(false) }

    // Add New Subject
    var showNewSubjectField by remember { mutableStateOf(false) }
    var newSubject by remember { mutableStateOf("") }

    // Selected Difficulty Level
    var selectedDifficulty by remember { mutableStateOf(initialDifficulty) }

    // Dropdown
    var difficultyMenuExpended by remember { mutableStateOf(false) }

    // Selected Due Date
    var selectedDate by remember { mutableStateOf(initialDate) }

// Date Picker
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 28.dp,
                bottom = 20.dp
            )
    ) {

        // =====================================================
        // PAGE TITLE
        // =====================================================

        Text(
            text = screenTitle,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = darkText
        )

        Spacer(
            modifier = Modifier.height(34.dp)
        )


        // =====================================================
        // ASSIGNMENT TITLE
        // =====================================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF0F2FF),
                        shape = CircleShape
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Assignment",
                    tint = blue
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Assignment Title",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = assignmentTitle,
            onValueChange = { assignmentTitle = it },
            placeholder = {
                Text(
                    text = "Enter assignment title",
                    color = greyText
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = blue,
                unfocusedBorderColor = Color(0xFFD6D9E2),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // =====================================================
        // SELECT SUBJECT
        // =====================================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF0F2FF),
                        shape = CircleShape
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Subject",
                    tint = blue
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Select a Subject",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ExposedDropdownMenuBox(
            expanded = subjectMenuExpended,
            onExpandedChange = { subjectMenuExpended = !subjectMenuExpended }
        ) {
            OutlinedTextField(
                value = selectedSubject,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Select a subject",
                        color = greyText
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = subjectMenuExpended
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = blue,
                    unfocusedBorderColor = Color(0xFFD6D9E2),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = subjectMenuExpended,
                onDismissRequest = { subjectMenuExpended = false }
            ) {
                subjectList.forEach { subject ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = subject,
                                fontSize = 15.sp
                            )
                        },
                        onClick = {
                            selectedSubject = subject
                            subjectMenuExpended = false
                        }
                    )
                }

                // Add New Subject option
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "+ Add New Subject",
                            color = blue,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        showNewSubjectField = true
                    }
                )

                if (showNewSubjectField) {

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = newSubject,
                        onValueChange = { newSubject = it },
                        label = {
                            Text("New Subject")
                        },
                        placeholder = {
                            Text("Eg. Software Engineering")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = blue,
                            unfocusedBorderColor = Color(0xFFD6D9E2),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                showNewSubjectField = false
                                newSubject = ""
                            }
                        ) {
                            Text(
                                text = "Cancel",
                                color = greyText
                            )
                        }

                        TextButton(
                            enabled = newSubject.isNotBlank(),
                            onClick = {
                                val subject = newSubject.trim()

                                if (!subjectList.contains(subject)) {
                                    subjectList.add(subject)
                                }

                                selectedSubject = subject
                                newSubject = ""
                                showNewSubjectField = false
                            }
                        ) {
                            Text(
                                text = "Save Subject",
                                color = blue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // =====================================================
        // SELECT DIFFICULTY LEVEL
        // =====================================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFE8F8F0),
                        shape = CircleShape
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Difficulty",
                    tint = Color(0xFF27B56E)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Select Difficulty Level",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ExposedDropdownMenuBox(
            expanded = difficultyMenuExpended,
            onExpandedChange = { difficultyMenuExpended = !difficultyMenuExpended }
        ) {
            OutlinedTextField(
                value = selectedDifficulty,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Select difficulty level",
                        color = greyText
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = difficultyMenuExpended
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = blue,
                    unfocusedBorderColor = Color(0xFFD6D9E2),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = difficultyMenuExpended,
                onDismissRequest = { difficultyMenuExpended = false }
            ) {
                difficultyList.forEach { difficulty ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = difficulty,
                                fontSize = 15.sp
                            )
                        },
                        onClick = {
                            selectedDifficulty = difficulty
                            difficultyMenuExpended = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // =====================================================
        // SELECT DUE DATE
        // =====================================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFF3E8),
                        shape = CircleShape
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Due Date",
                    tint = Color(0xFFFF8A3D)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Select Due Date",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text(
                        text = "Select due date",
                        color = greyText
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        tint = blue
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color(0xFFD6D9E2),
                    disabledContainerColor = Color.White,
                    disabledTextColor = darkText,
                    disabledPlaceholderColor = greyText,
                    disabledTrailingIconColor = blue
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Push buttons to bottom
        Spacer(
            modifier = Modifier.weight(1f)
        )


        // =====================================================
        // CANCEL + ADD BUTTON
        // =====================================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            OutlinedButton(
                onClick = onCancelClick,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )
            }


            Button(
                onClick = {
                    onAddClick(
                        assignmentTitle.trim(),
                        selectedSubject,
                        selectedDifficulty,
                        selectedDate
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (showDatePicker) {

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },

                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { milliseconds ->

                                val formatter = SimpleDateFormat(
                                    "dd MMM yyyy",
                                    Locale.getDefault()
                                )

                                selectedDate = formatter.format(
                                    Date(milliseconds)
                                )
                            }

                            showDatePicker = false
                        }
                    ) {
                        Text(
                            text = "OK",
                            color = blue
                        )
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }

}
