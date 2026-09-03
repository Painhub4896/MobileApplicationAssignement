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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekSelector(
    onDateSelected: (LocalDate) -> Unit
) {

    val today = LocalDate.now()

    var selectedDate by remember {
        mutableStateOf(today)
    }

    var weekStart by remember {
        mutableStateOf(
            today.with(DayOfWeek.MONDAY)
        )
    }

    val weekEnd = weekStart.plusDays(6)

    val dateFormatter = DateTimeFormatter.ofPattern(
        "MMM d",
        Locale.ENGLISH
    )

    val weekText =
        if (weekStart.year == weekEnd.year) {
            "${weekStart.format(dateFormatter)} – ${weekEnd.format(dateFormatter)}, ${weekEnd.year}"
        } else {
            "${weekStart.format(dateFormatter)} ${weekStart.year} – ${weekEnd.format(dateFormatter)} ${weekEnd.year}"
        }

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
                    .clickable {

                        weekStart = weekStart.minusWeeks(1)

                        selectedDate = selectedDate.minusWeeks(1)

                        onDateSelected(selectedDate)
                    }
            )

            Text(
                text = weekText,
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
                    .clickable {

                        weekStart = weekStart.plusWeeks(1)

                        selectedDate = selectedDate.plusWeeks(1)

                        onDateSelected(selectedDate)
                    }
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // Days
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0..6) {
                val date = weekStart.plusDays(i.toLong())
                WeekDay(
                    day = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.ENGLISH
                    ).uppercase(),
                    date = date.dayOfMonth.toString(),
                    selected = date == selectedDate,
                    onClick = {
                        selectedDate = date
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}

@Composable
fun WeekDay(
    day: String,
    date: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .size(width = 36.dp, height = 48.dp)
            .clickable {
                onClick()
            },
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
                .size(32.dp)
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

@Preview(showBackground = true)
@Composable
fun WeekDayPreview() {
    WeekDay(
        day = "WED",
        date = "2",
        selected = true
    )
}