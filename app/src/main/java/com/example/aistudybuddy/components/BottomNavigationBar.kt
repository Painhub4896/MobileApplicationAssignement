package com.example.aistudybuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomNavigationBar(
    selectedItem: String = "Planner",
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val blue = Color(0xFF4169E1)
    val grey = Color(0xFF555863)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Home
        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = "Home",
            selected = selectedItem == "Home",
            selectedColor = blue,
            unselectedColor = grey,
            onClick = onHomeClick
        )

        // Assignments
        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Assignments"
                )
            },
            label = "Assignments",
            selected = selectedItem == "Assignments",
            selectedColor = blue,
            unselectedColor = grey,
            onClick = onAssignmentsClick
        )

        // Planner
        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Planner"
                )
            },
            label = "Planner",
            selected = selectedItem == "Planner",
            selectedColor = blue,
            unselectedColor = grey,
            onClick = onPlannerClick
        )

        // Progress
        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Progress"
                )
            },
            label = "Progress",
            selected = selectedItem == "Progress",
            selectedColor = blue,
            unselectedColor = grey,
            onClick = onProgressClick
        )

        // Profile
        BottomNavItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = "Profile",
            selected = selectedItem == "Profile",
            selectedColor = blue,
            unselectedColor = grey,
            onClick = onProfileClick
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: @Composable () -> Unit,
    label: String,
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit
) {

    val color =
        if (selected) selectedColor
        else unselectedColor

    Column(
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight()
            .clickable {
                onClick()
            }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icon
        CompositionLocalProvider(
            LocalContentColor provides color
        ) {
            icon()
        }

        // Label
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight =
                if (selected)
                    FontWeight.Bold
                else
                    FontWeight.Normal,
            color = color
        )
    }
}