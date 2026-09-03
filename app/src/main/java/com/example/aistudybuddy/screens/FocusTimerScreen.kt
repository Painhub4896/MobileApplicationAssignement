package com.example.aistudybuddy.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import kotlinx.coroutines.delay

enum class TimerMode {
    FOCUS,
    BREAK
}

@SuppressLint("DefaultLocale")
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return String.format(
        "%02d:%02d",
        minutes,
        remainingSeconds
    )
}

@Composable
fun FocusTimerScreen(onBackClick: () -> Unit) {

    val blue = Color(0xFF4169E1)
    val darkText = Color(0xFF252838)
    val background = Color.White
    val breakGreen = Color(0xFF43A047)

    // ---------------- TIMER SETTINGS ----------------


    var totalSessions by remember { mutableStateOf(4) }

    var focusMinutes by remember {
        mutableStateOf(25)
    }

    var breakMinutes by remember {
        mutableStateOf(5)
    }


    // ---------------- CURRENT TIMER STATE ----------------

    var currentSession by remember {
        mutableStateOf(1)
    }

    var timerMode by remember {
        mutableStateOf(TimerMode.FOCUS)
    }

    var remainingSeconds by remember {
        mutableStateOf(focusMinutes * 60)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    var allSessionsCompleted by remember {
        mutableStateOf(false)
    }


    // ---------------- SETTINGS DIALOG ----------------

    var showSettings by remember {
        mutableStateOf(false)
    }

    var tempSessions by remember {
        mutableStateOf(totalSessions.toString())
    }

    var tempFocusMinutes by remember {
        mutableStateOf(focusMinutes.toString())
    }

    var tempBreakMinutes by remember {
        mutableStateOf(breakMinutes.toString())
    }

    val timerColor = when {
        allSessionsCompleted -> Color.Gray
        timerMode == TimerMode.FOCUS -> blue
        else -> breakGreen
    }

    val openSettings = {
        tempSessions = totalSessions.toString()
        tempFocusMinutes = focusMinutes.toString()
        tempBreakMinutes = breakMinutes.toString()

        showSettings = true
    }

    LaunchedEffect(
        isRunning,
        remainingSeconds
    ) {


        if (isRunning && remainingSeconds > 0) {

            delay(1000L)

            remainingSeconds--

        } else if (isRunning && remainingSeconds == 0) {

            isRunning = false

            when (timerMode) {

                TimerMode.FOCUS -> {

                    // Last session completed
                    if (currentSession >= totalSessions) {

                        allSessionsCompleted = true

                    } else {

                        // User selected a break
                        if (breakMinutes > 0) {

                            timerMode = TimerMode.BREAK

                            remainingSeconds =
                                breakMinutes * 60

                        } else {

                            // No break selected
                            currentSession++

                            timerMode = TimerMode.FOCUS

                            remainingSeconds =
                                focusMinutes * 60
                        }
                    }
                }


                TimerMode.BREAK -> {

                    // Break finished
                    currentSession++

                    timerMode = TimerMode.FOCUS

                    remainingSeconds =
                        focusMinutes * 60
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {



        // ---------------- MAIN CONTENT ----------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ){
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = darkText
                )
            }


            // Title
            Text(
                text = "Focus Timer",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = darkText,
                modifier = Modifier
                    .align(Alignment.Center)
                )

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(18.dp)
            )


            // ---------------- TIMER CIRCLE ----------------

            Box(
                modifier = Modifier
                    .size(190.dp)
                    .border(
                        width = 5.dp,
                        color = timerColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = formatTime(remainingSeconds),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkText
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = when {
                            allSessionsCompleted -> "Completed"
                            timerMode == TimerMode.FOCUS -> "Focus Time"
                            else -> "Break Time"
                        },
                        fontSize = 10.sp,
                        color = Color(0xFF70727D)
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // ---------------- SESSION COUNT ----------------

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E5E5),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    )
            ) {

                Text(
                    text = "Session $currentSession of $totalSessions",
                    fontSize = 9.sp,
                    color = Color(0xFF70727D)
                )
            }


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // ---------------- PROGRESS DOTS ----------------

            Row(
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                repeat(totalSessions) { index ->

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color =
                                    if (index < currentSession) {
                                        blue
                                    } else {
                                        Color(0xFFE5E5E5)
                                    },
                                shape = CircleShape
                            )
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // ---------------- START FOCUS ----------------

            Button(
                onClick = {
                    if (!allSessionsCompleted) {
                        isRunning = !isRunning
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = timerColor,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = when {

                        allSessionsCompleted ->
                            "✓  All Sessions Complete"

                        isRunning && timerMode == TimerMode.FOCUS ->
                            "Pause Focus"

                        isRunning && timerMode == TimerMode.BREAK ->
                            "Pause Break"

                        timerMode == TimerMode.FOCUS ->
                            "▶  Start Focus"

                        else ->
                            "▶  Start Break"
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // ---------------- RESET / SKIP BREAK ----------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Button(
                    onClick = {
                        isRunning = false

                        currentSession = 1

                        timerMode = TimerMode.FOCUS

                        remainingSeconds =
                            focusMinutes * 60

                        allSessionsCompleted = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = darkText
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFC8C8C8)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "Reset",
                        fontSize = 10.sp
                    )
                }


                Button(
                    onClick = {
                        openSettings()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = darkText
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFC8C8C8)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "Settings",
                        fontSize = 10.sp
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // ---------------- AI TIP ----------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF6EEFF),
                        shape = RoundedCornerShape(9.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE1CFFF),
                        shape = RoundedCornerShape(9.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 14.dp
                    )
            ) {

                Row(
                    verticalAlignment = Alignment.Top
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "✦ AI Tip",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7657D9)
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Eliminate distractions and take deep breaths. You've got this!",
                            fontSize = 10.sp,
                            color = Color(0xFF555863)
                        )
                    }

                    Text(
                        text = "›",
                        fontSize = 18.sp,
                        color = Color(0xFF7657D9)
                    )
                }
            }
        }
    }

    if (showSettings) {

        val sessionValue =
            tempSessions.toIntOrNull()

        val focusValue =
            tempFocusMinutes.toIntOrNull()

        val breakValue =
            tempBreakMinutes.toIntOrNull()


        val validSettings =
            sessionValue != null &&
                    sessionValue in 1..10 &&
                    focusValue != null &&
                    focusValue in 1..180 &&
                    breakValue != null &&
                    breakValue in 0..60


        AlertDialog(

            onDismissRequest = {
                showSettings = false
            },

            title = {
                Text(
                    text = "Timer Settings",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Column {

                    // Number of sessions

                    OutlinedTextField(
                        value = tempSessions,
                        onValueChange = {
                            tempSessions = it
                        },
                        label = {
                            Text("Number of Sessions")
                        },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            ),
                        singleLine = true
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    // Focus time

                    OutlinedTextField(
                        value = tempFocusMinutes,
                        onValueChange = {
                            tempFocusMinutes = it
                        },
                        label = {
                            Text("Focus Time (minutes)")
                        },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            ),
                        singleLine = true
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    // Break time

                    OutlinedTextField(
                        value = tempBreakMinutes,
                        onValueChange = {
                            tempBreakMinutes = it
                        },
                        label = {
                            Text(
                                "Break Time (0 = No Break)"
                            )
                        },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            ),
                        singleLine = true
                    )


                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )


                    Text(
                        text =
                            "Enter 0 for break time if you do not want a break.",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            },

            confirmButton = {

                TextButton (
                    enabled = validSettings,

                    onClick = {

                        totalSessions =
                            sessionValue!!

                        focusMinutes =
                            focusValue!!

                        breakMinutes =
                            breakValue!!


                        // Restart timer using
                        // new settings

                        currentSession = 1

                        timerMode =
                            TimerMode.FOCUS

                        remainingSeconds =
                            focusMinutes * 60

                        isRunning = false

                        allSessionsCompleted =
                            false

                        showSettings = false
                    }
                ) {

                    Text("Save")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showSettings = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}
