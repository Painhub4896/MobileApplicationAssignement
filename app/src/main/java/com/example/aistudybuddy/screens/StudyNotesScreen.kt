package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
fun StudyNotesScreen() {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Notes"
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {

            // App Header
            AppHeader()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                // Screen title
                Text(
                    text = "Study Notes",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF252838)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Search bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD9D9DF),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search notes",
                            tint = Color(0xFF8A8C96),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Search notes",
                            fontSize = 12.sp,
                            color = Color(0xFF8A8C96)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // Mathematics
                NoteFolderCard(
                    title = "Mathematics",
                    notes = "10 Notes",
                    folderColor = Color(0xFFE7D5FF),
                    iconColor = Color(0xFF8D5DEB)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // Physics
                NoteFolderCard(
                    title = "Physics",
                    notes = "7 Notes",
                    folderColor = Color(0xFFD7E8FF),
                    iconColor = Color(0xFF4D8DE8)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // Biology
                NoteFolderCard(
                    title = "Biology",
                    notes = "6 Notes",
                    folderColor = Color(0xFFD9F1D2),
                    iconColor = Color(0xFF69B65D)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Add Notes button
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4169E1),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(9.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Notes",
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "Add Notes",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // AI Study Tip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F0FF),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFD9CCFF),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {

                        // Sparkle icon
                        Text(
                            text = "✦",
                            fontSize = 16.sp,
                            color = Color(0xFF7657D9)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        // Tip content
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "AI Study Tip",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7657D9)
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "Review your notes regularly to improve retention. Great job staying consistent!",
                                fontSize = 10.sp,
                                color = Color(0xFF555863),
                                lineHeight = 14.sp
                            )
                        }

                        // Arrow
                        Text(
                            text = "›",
                            fontSize = 20.sp,
                            color = Color(0xFF7657D9)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NoteFolderCard(
    title: String,
    notes: String,
    folderColor: Color,
    iconColor: Color
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(9.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color(0xFFE0E0E5),
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Folder icon
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = folderColor,
                        shape = RoundedCornerShape(7.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // Subject information
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF252838)
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = notes,
                    fontSize = 10.sp,
                    color = Color(0xFF70727D)
                )
            }

            // Three-dot menu
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color(0xFF70727D),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun StudyNotesScreenPreview() {
    StudyNotesScreen()
}