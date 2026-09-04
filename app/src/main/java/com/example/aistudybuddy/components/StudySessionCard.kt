package com.example.aistudybuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun StudySessionCard(
    title: String,
    time: String,
    room: String? = null,
    cardBackground: Color = Color.White,
    titleColor: Color = Color(0xFF30323D),
    aiSuggested: Boolean = false,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = cardBackground,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 0.3.dp,
                color = titleColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 18.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                if (!room.isNullOrBlank()) {

                    Spacer(
                        modifier =
                            Modifier.height(3.dp)
                    )

                    Text(
                        text = "📍 $room",
                        fontSize = 12.sp,
                        color = Color(0xFF767987)
                    )
                }

                if (aiSuggested) {
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "✦ AI suggested",
                        fontSize = 10.sp,
                        color = Color(0xFF7657D9)
                    )
                }
            }

            Box {
                IconButton(
                    onClick = { showMenu = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color(0xFF70727D)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = {
                        showMenu = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Edit")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        },
                        onClick = {
                            showMenu = false
                            onEdit()
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Delete")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        },
                        onClick = {
                            showMenu = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}