package com.example.aistudybuddy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import java.time.DayOfWeek


@Composable
fun PlannerActionButtons(
    onAddSession: (
        String,
        String,
        String,
        String?,
        Set<DayOfWeek>?
    ) -> Unit,
    onViewRoutineClick: () -> Unit,
    existingSessions: List<StudySession> = emptyList()
) {

    var showAddSessionDialog by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(8.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {


            // ==========================================
            // VIEW ROUTINE
            // ==========================================

            Button(
                onClick = {
                    onViewRoutineClick()
                },
                modifier =
                    Modifier
                        .weight(1f)
                        .height(46.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color.White,
                        contentColor =
                            Color(0xFF4169E1)
                    ),
                border =
                    BorderStroke(
                        width = 1.dp,
                        color =
                            Color(0xFF4169E1)
                    ),
                elevation =
                    ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
            ) {

                Text(
                    text = "View Routine",
                    fontSize = 12.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }


            // ==========================================
            // ADD SESSION
            // ==========================================

            Button(
                onClick = {
                    showAddSessionDialog = true
                },
                modifier =
                    Modifier
                        .weight(1f)
                        .height(46.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF4169E1),
                        contentColor =
                            Color.White
                    )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Add,
                    contentDescription =
                        "Add Session"
                )

                Text(
                    text = "Add Session",
                    fontSize = 12.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }


    if (
        showAddSessionDialog
    ) {

        AddSessionDialog(
            onDismiss = {
                showAddSessionDialog = false
            },

            existingSessions =
                existingSessions,

            onAddSession = {
                    subject,
                    startTime,
                    endTime,
                    room,
                    repeatDays ->

                onAddSession(
                    subject,
                    startTime,
                    endTime,
                    room,
                    repeatDays
                )

                showAddSessionDialog = false
            }
        )
    }
}