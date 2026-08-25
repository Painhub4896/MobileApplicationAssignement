package com.example.aistudybuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AI",
            color = Color(0xFF5267F7),
            fontSize = 52.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "AIStudyBuddy",
            color = Color(0xFF5267F7),
            fontSize = 34.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Learn. Plan. Achieve Smarter.",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        CircularProgressIndicator(
            color = Color(0xFF5267F7)
        )
    }
}