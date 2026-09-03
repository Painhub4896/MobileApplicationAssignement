package com.example.aistudybuddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.aistudybuddy.R

@Composable
fun AppHeader(
    onClickNotification: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                start = 16.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            // AI robot icon
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),                contentDescription = "AI StudyBuddy",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "AI",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4169E1),
                modifier = Modifier.padding(start = 3.dp)
            )

            Text(
                text = "StudyBuddy",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF252838)
            )
        }

        IconButton(
            onClick = onClickNotification
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification",
                tint = Color(0xFF353744)
            )
        }
    }
}