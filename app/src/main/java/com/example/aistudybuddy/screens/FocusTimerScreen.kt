package com.example.aistudybuddy.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar


@Composable
fun FocusTimerScreen() {

    val blue = Color(0xFF4169E1)
    val darkText = Color(0xFF252838)
    val background = Color.White

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
                .padding(innerPadding)
                .background(background)
        ) {

            // App Header
            AppHeader()


            // ---------------- MAIN CONTENT ----------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // Title
                Text(
                    text = "Focus Timer",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // ---------------- CURRENT TASK CARD ----------------

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE1E1E1),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 13.dp
                        )
                ) {

                    Column {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Current Task",
                                fontSize = 11.sp,
                                color = Color(0xFF70727D)
                            )

                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit task",
                                tint = Color(0xFF70727D),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = "Biology",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(25.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = "Biology Revision",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF47783F)
                            )
                        }
                    }
                }


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                // ---------------- TIMER CIRCLE ----------------

                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .border(
                            width = 5.dp,
                            color = blue,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "25:00",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = darkText
                        )

                        Spacer(
                            modifier = Modifier.height(2.dp)
                        )

                        Text(
                            text = "Focus Time",
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
                        text = "Session 1 of 4",
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

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(blue, CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE5E5E5), CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE5E5E5), CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE5E5E5), CircleShape)
                    )
                }


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // ---------------- START FOCUS ----------------

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blue,
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "▶  Start Focus",
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
                        onClick = { },
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
                        onClick = { },
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
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Skip Break",
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = "Skip Break",
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
    }
}


// ---------------- PREVIEW ----------------

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun FocusTimerScreenPreview() {
    FocusTimerScreen()
}