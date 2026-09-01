package com.example.aistudybuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
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
fun WeekSelector() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {

        // Week navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous week",
                tint = Color(0xFF555863),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { }
            )

            Text(
                text = "May 12 – May 18, 2025",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next week",
                tint = Color(0xFF555863),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { }
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // Days of the week
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            WeekDay(
                day = "MON",
                date = "12",
                selected = true
            )

            WeekDay(day = "TUE", date = "13")
            WeekDay(day = "WED", date = "14")
            WeekDay(day = "THU", date = "15")
            WeekDay(day = "FRI", date = "16")
            WeekDay(day = "SAT", date = "17")
            WeekDay(day = "SUN", date = "18")
        }
    }
}

@Composable
fun WeekDay(
    day: String,
    date: String,
    selected: Boolean = false
) {

    Column(
        modifier = Modifier
            .size(width = 38.dp, height = 48.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = day,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Box(
            modifier = Modifier
                .size(27.dp)
                .background(
                    color = if (selected)
                        Color(0xFF4169E1)
                    else
                        Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = date,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected)
                    Color.White
                else
                    Color(0xFF30323D)
            )
        }
    }
}