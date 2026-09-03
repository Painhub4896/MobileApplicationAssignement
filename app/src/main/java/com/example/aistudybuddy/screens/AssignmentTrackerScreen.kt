package com.example.aistudybuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.BottomNavigationBar

val AssignPrimaryBlue = Color(0xFF4C6FFF)
val AssignLightBlue = Color(0xFFE8F0FE)
val AssignTextDark = Color(0xFF2C3E50)
val AssignTextGrey = Color(0xFF888888)
val AssignRed = Color(0xFFEF4444)
val AssignYellow = Color(0xFFF59E0B)
val AssignGreen = Color(0xFF10B981)

data class AssignmentItem(
    val icon: ImageVector,
    val subject: String,
    val dueDate: String,
    val priority: String,
    val progress: String,
    val iconColor: Color,
    val iconBg: Color
)

@Composable
fun AssignmentTrackerScreen(
    onHomeClick: () -> Unit = {},
    onAssignmentClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){
    //Sample Data
    val assignment = listOf(
        AssignmentItem(
            icon = Icons.Outlined.Code,
            subject = "Database Project",
            dueDate = "Due: 10 Sep",
            priority = "High",
            progress = "40%",
            iconColor = AssignPrimaryBlue,
            iconBg = AssignLightBlue
        ),

        AssignmentItem(
            icon = Icons.Outlined.Calculate,
            subject = "Math Quiz",
            dueDate = "Due: 7 Sep",
            priority = "Medium",
            progress = "10%",
            iconColor = AssignPrimaryBlue,
            iconBg = AssignLightBlue
        ),

        AssignmentItem(
            icon = Icons.Outlined.Science,
            subject = "Lab Report",
            dueDate = "Due: 30 Aug",
            priority = "High",
            progress = "20%",
            iconColor = AssignGreen,
            iconBg = Color(0xFFD1FAE5)
        ),

        AssignmentItem(
            icon = Icons.Outlined.Smartphone,
            subject = "Mobile Application",
            dueDate = "Due: 15 Sep",
            priority = "Low",
            progress = "50%",
            iconColor = AssignRed,
            iconBg = Color(0xFFFEE2E2)
        )
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Assignments",
                onHomeClick = onHomeClick,
                onAssignmentClick = onAssignmentClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            //Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Assignment Tracker",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AssignTextDark
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Outlined.FilterList,
                    contentDescription = "Filter",
                    tint = AssignTextDark,
                    modifier = Modifier.size(28.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = { Text("All", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AssignPrimaryBlue,
                        selectedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterChip(
                    selected = false,
                    onClick = {},
                    label = { Text("Pending", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = AssignLightBlue,
                        labelColor = AssignTextDark
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterChip(
                    selected = false,
                    onClick = {},
                    label = { Text("Completed", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = AssignLightBlue,
                        labelColor = AssignTextDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Assignment List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(assignment) { assignment ->
                    AssignmentCard(assignment)
                }
            }
        }
    }
}

@Composable
fun AssignmentCard(item: AssignmentItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(item.iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = item.iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AssignTextDark,
                    modifier = Modifier.weight(1f)
                )

                // Priority Badge
                val (badgeColor, badgeBg) = when (item.priority) {
                    "High" -> AssignRed to Color(0xFFFEE2E2)
                    "Medium" -> AssignYellow to Color(0xFFFEF3C7)
                    else -> AssignGreen to Color(0xFFD1FAE5)
                }
                Text(
                    text = item.priority,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.dueDate,
                fontSize = 12.sp,
                color = AssignTextGrey
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar (Parse String to Float)
            val progressValue = item.progress.replace("%", "").toFloatOrNull() ?: 0f

            LinearProgressIndicator(
                progress = { progressValue / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = item.iconColor,
                trackColor = AssignLightBlue
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.progress, // This is now a String like "40%" directly!
                fontSize = 12.sp,
                color = AssignTextGrey,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun AssignmentTrackerScreenPreview(){
    AssignmentTrackerScreen()
}



