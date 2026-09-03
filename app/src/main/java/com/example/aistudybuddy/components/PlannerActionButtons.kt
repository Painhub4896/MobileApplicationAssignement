package com.example.aistudybuddy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.data.StudySession

@Composable
fun PlannerActionButtons(
    onAddSession: (String, String) -> Unit,
    onGenerateWithAI: () -> Unit,
    onViewTimetableClick: () -> Unit = {},
    existingSessions: List<StudySession> = emptyList()
) {

    var showAddSessionDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // ==================================================
        // GENERATE WITH AI + ADD SESSION
        // ==================================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    onGenerateWithAI()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4169E1)
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFF4169E1)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                )
            ) {

                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Generate with AI"
                )

                Text(
                    text = "Generate with AI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    showAddSessionDialog = true
                },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4169E1),
                    contentColor = Color.White
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Session"
                )

                Text(
                    text = "Add Session",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }




        // ==================================================
        // VIEW TIMETABLE
        // ==================================================

        Button(
            onClick = {
                onViewTimetableClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF4169E1)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFF4169E1)
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp
            )
        ) {

            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "View Timetable"
            )

            Text(
                text = "View Timetable",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }


    // ==================================================
    // ADD SESSION DIALOG
    // ==================================================

    if (showAddSessionDialog) {

        AddSessionDialog(
            onDismiss = {
                showAddSessionDialog = false
            },
            existingSessions = existingSessions,
            onAddSession = { subject, time ->

                onAddSession(
                    subject,
                    time
                )

                showAddSessionDialog = false
            }
        )
    }
}