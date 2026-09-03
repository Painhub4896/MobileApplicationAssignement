package com.example.aistudybuddy.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.BottomNavigationBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


val AssignPrimaryBlue = Color(0xFF4169E1)
val AssignLightBlue = Color(0xFFEFF3FF)
val AssignTextDark = Color(0xFF18213A)
val AssignTextGrey = Color(0xFF747B8C)

val AssignRed = Color(0xFFEF4444)
val AssignYellow = Color(0xFFF59E0B)
val AssignGreen = Color(0xFF10B981)


data class AssignmentItem(
    val title: String,
    val subject: String,
    val dueDate: String,
    val difficulty: String
)


@Composable
fun AssignmentTrackerScreen(
    assignments: List<AssignmentItem>,
    onHomeClick: () -> Unit = {},
    onAssignmentClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddClick: () -> Unit,
    onEditClick: (AssignmentItem) -> Unit,
    onDeleteClick: (AssignmentItem) -> Unit
) {

    // Selected Assignment
    var selectedAssignment by remember { mutableStateOf<AssignmentItem?>(null) }

    // Count all assignments
    val taskCount = assignments.size

    // Count assignments due within 5 days
    val dueSoonCount = assignments.count { assignment ->
        isDueSoon(assignment.dueDate)
    }


    Scaffold(
        containerColor = Color(0xFFF9FAFC),

        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Assignments",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentClick,
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


            // =====================================================
            // TOP HEADER
            // =====================================================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 26.dp,
                        bottom = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Assignment Tracker",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = AssignTextDark
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )


                // Add Assignment Button
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(AssignPrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {

                    IconButton(
                        onClick = onAddClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Assignment",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }


            // =====================================================
            // SUMMARY CARDS
            // =====================================================

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                // Tasks Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(96.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFE4E8F0)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = Color(0xFFEFF3FF),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = "Tasks",
                                tint = AssignPrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = taskCount.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = AssignPrimaryBlue
                            )

                            Text(
                                text = "Tasks",
                                fontSize = 13.sp,
                                color = AssignTextGrey
                            )
                        }
                    }
                }


                // Due Soon Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(96.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFE4E8F0)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = Color(0xFFFFF3E3),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Due Soon",
                                tint = Color(0xFFFF8500),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = dueSoonCount.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF8500)
                            )

                            Text(
                                text = "Due Soon",
                                fontSize = 13.sp,
                                color = AssignTextGrey
                            )
                        }
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // =====================================================
            // ASSIGNMENT LIST
            // =====================================================

            if (assignments.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                color = AssignLightBlue,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = "No Assignment",
                            tint = AssignPrimaryBlue,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "No assignments yet",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AssignTextDark
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Tap + to create your first assignment",
                        fontSize = 14.sp,
                        color = AssignTextGrey
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(assignments) { assignment ->

                        AssignmentCard(
                            item = assignment,
                            onClick = { selectedAssignment = assignment }
                        )
                    }
                }
            }
        }
    }


    // =====================================================
    // EDIT / REMOVE DIALOG
    // =====================================================

    selectedAssignment?.let { assignment ->

        AlertDialog(
            onDismissRequest = { selectedAssignment = null },

            title = {
                Text(
                    text = assignment.title,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    text = "What would you like to do with this assignment?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        selectedAssignment = null
                        onEditClick(assignment)
                    }
                ) {
                    Text(
                        text = "Edit",
                        color = AssignPrimaryBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        selectedAssignment = null
                        onDeleteClick(assignment)
                    }
                ) {
                    Text(
                        text = "Remove",
                        color = AssignRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}


@Composable
fun AssignmentCard(
    item: AssignmentItem,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE5E9F1)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            // =====================================================
            // ASSIGNMENT ICON
            // =====================================================

            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AssignLightBlue),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Assignment",
                    tint = AssignPrimaryBlue,
                    modifier = Modifier.size(32.dp)
                )
            }


            Spacer(
                modifier = Modifier.width(18.dp)
            )


            // =====================================================
            // ASSIGNMENT INFORMATION
            // =====================================================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AssignTextDark,
                        modifier = Modifier.weight(1f)
                    )


                    // Difficulty Badge
                    val badgeColor: Color
                    val badgeBg: Color

                    when (item.difficulty) {

                        "Hard" -> {
                            badgeColor = AssignRed
                            badgeBg = Color(0xFFFFE4E6)
                        }

                        "Medium" -> {
                            badgeColor = AssignYellow
                            badgeBg = Color(0xFFFFF3D7)
                        }

                        else -> {
                            badgeColor = AssignGreen
                            badgeBg = Color(0xFFE0F8EC)
                        }
                    }


                    Text(
                        text = item.difficulty,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(badgeBg)
                            .padding(
                                horizontal = 8.dp,
                                vertical = 6.dp
                            )
                    )
                }


                Spacer(
                    modifier = Modifier.height(6.dp)
                )


                // Subject
                Text(
                    text = item.subject,
                    fontSize = 13.sp,
                    color = AssignPrimaryBlue
                )


                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // Due Date
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Due Date",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Due: ${item.dueDate}",
                        fontSize = 14.sp,
                        color = AssignTextGrey
                    )
                }
            }
        }
    }
}


// =====================================================
// CHECK WHETHER ASSIGNMENT IS DUE WITHIN 5 DAYS
// =====================================================

fun isDueSoon(dueDate: String): Boolean {

    return try {

        val formatter = SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        )

        formatter.isLenient = false

        val dueDateValue = formatter.parse(dueDate) ?: return false


        val today = Calendar.getInstance()

        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)


        val due = Calendar.getInstance()

        due.time = dueDateValue

        due.set(Calendar.HOUR_OF_DAY, 0)
        due.set(Calendar.MINUTE, 0)
        due.set(Calendar.SECOND, 0)
        due.set(Calendar.MILLISECOND, 0)


        val difference = due.timeInMillis - today.timeInMillis

        val fiveDays = 5L * 24 * 60 * 60 * 1000


        difference >= 0 && difference <= fiveDays

    } catch (e: Exception) {

        false
    }
}