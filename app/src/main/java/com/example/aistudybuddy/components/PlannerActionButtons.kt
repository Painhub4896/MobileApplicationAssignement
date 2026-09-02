package com.example.aistudybuddy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlannerActionButtons(
    onGenerateWithAiClick: () -> Unit = {},
    onAddSessionClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Generate with AI
        Button(
            onClick = onGenerateWithAiClick,
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
                contentDescription = "Generate with AI",
                modifier = Modifier.size(17.dp)
            )

            Text(
                text = "Generate with AI",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Add Session
        Button(
            onClick = onAddSessionClick,
            modifier = Modifier
                .weight(1f)
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4169E1),
                contentColor = Color.White
            )
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Session",
                modifier = Modifier.size(17.dp)
            )

            Text(
                text = "Add Session",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}