package com.example.aistudybuddy.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.AppHeader
import com.example.aistudybuddy.components.BottomNavigationBar
import com.example.aistudybuddy.data.TimetableEntry
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min


@Composable
fun WeeklyTimetableScreen(
    onBackClick: () -> Unit,
    entries: List<TimetableEntry>,
    onAddClassClick: () -> Unit,
    onGenerateRoutineClick: () -> Unit = {}
) {

    val primaryBlue = Color(0xFF4169E1)
    val pageBackground = Color(0xFFF7F8FC)
    val textPrimary = Color(0xFF171A24)
    val textSecondary = Color(0xFF747984)

    Scaffold(

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBackground)
                .padding(innerPadding)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 14.dp
                    )
            ) {

                // =====================================================
                // TITLE
                // =====================================================

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ){
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    // Title
                    Text(
                        text = "School Timetable",
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Your weekly class schedule",
                    fontSize = 13.sp,
                    color = textSecondary
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // =====================================================
                // SUMMARY CARD
                // =====================================================

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F4FF)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(13.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = primaryBlue,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(21.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {

                            Text(
                                text =
                                    if (entries.isEmpty()) {
                                        "No classes added"
                                    } else {
                                        "${entries.size} classes this week"
                                    },
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = textPrimary
                            )

                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )

                            Text(
                                text = "Your classes are arranged automatically by time.",
                                fontSize = 11.sp,
                                color = textSecondary
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // =====================================================
                // TIMETABLE GRID
                // =====================================================

                TimetableGrid(
                    entries = entries
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                // =====================================================
                // ADD CLASS
                // =====================================================

                OutlinedButton(
                    onClick = onAddClassClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(13.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = primaryBlue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = primaryBlue
                    )

                    Text(
                        text = "  Add Another Class",
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                // =====================================================
                // GENERATE ROUTINE
                // =====================================================

                Button(
                    onClick = onGenerateRoutineClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "  Generate Study Routine",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}


// =====================================================================
// TIMETABLE GRID
// =====================================================================

@Composable
private fun TimetableGrid(
    entries: List<TimetableEntry>
) {

    val days = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday"
    )

    val validStarts =
        entries.mapNotNull {

            val value =
                timeToMinutes(it.startTime)

            if (value >= 0) {
                value
            } else {
                null
            }
        }

    val validEnds =
        entries.mapNotNull {

            val value =
                timeToMinutes(it.endTime)

            if (value >= 0) {
                value
            } else {
                null
            }
        }


    // Default timetable starts 8 AM
    val earliestClassHour =
        if (validStarts.isEmpty()) {

            8

        } else {

            validStarts.minOrNull()!! / 60
        }


    // Default timetable ends at 3 PM
    val latestClassHour =
        if (validEnds.isEmpty()) {

            15

        } else {

            ceil(
                validEnds.maxOrNull()!! / 60.0
            ).toInt()
        }


    val startHour =
        max(
            0,
            min(
                8,
                earliestClassHour
            )
        )


    val endHour =
        min(
            24,
            max(
                15,
                latestClassHour
            )
        )


    // Bigger than old version
    val hourHeight = 76.dp

    // Bigger time column
    val timeColumnWidth = 52.dp

    val totalHours =
        endHour - startHour

    val gridHeight =
        hourHeight * totalHours


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE2E5EC)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column {

            // =========================================================
            // DAY HEADER
            // =========================================================

            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {

                val availableWidth =
                    maxWidth - timeColumnWidth

                val dayWidth =
                    availableWidth / 5


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {

                    // Top-left empty box
                    Box(
                        modifier = Modifier
                            .width(timeColumnWidth)
                            .height(46.dp)
                            .background(
                                Color(0xFFF8F9FC)
                            )
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFFE5E7ED)
                            )
                    )


                    days.forEach { day ->

                        Box(
                            modifier = Modifier
                                .width(dayWidth)
                                .height(46.dp)
                                .background(
                                    Color(0xFFF8F9FC)
                                )
                                .border(
                                    width = 0.5.dp,
                                    color = Color(0xFFE5E7ED)
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = day.take(3),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF252936),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }


            // =========================================================
            // TIMETABLE BODY
            // =========================================================

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight)
            ) {

                val availableWidth =
                    maxWidth - timeColumnWidth

                val dayWidth =
                    availableWidth / 5


                // =====================================================
                // GRID BACKGROUND
                // =====================================================

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // -------------------------------------------------
                    // TIME LABEL COLUMN
                    // -------------------------------------------------

                    Column(
                        modifier = Modifier
                            .width(timeColumnWidth)
                    ) {

                        for (
                        hour in startHour until endHour
                        ) {

                            Box(
                                modifier = Modifier
                                    .width(timeColumnWidth)
                                    .height(hourHeight)
                                    .background(
                                        Color(0xFFFBFBFD)
                                    )
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFFE7E9EF)
                                    ),
                                contentAlignment =
                                    Alignment.TopCenter
                            ) {

                                Text(
                                    text = formatHourLabel(hour),
                                    modifier = Modifier.padding(
                                        top = 7.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp,
                                    lineHeight = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF555B68)
                                )
                            }
                        }
                    }


                    // -------------------------------------------------
                    // MONDAY - FRIDAY GRID
                    // -------------------------------------------------

                    days.forEach {

                        Column(
                            modifier = Modifier
                                .width(dayWidth)
                        ) {

                            repeat(totalHours) {

                                Box(
                                    modifier = Modifier
                                        .width(dayWidth)
                                        .height(hourHeight)
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFFE7E9EF)
                                        )
                                )
                            }
                        }
                    }
                }


                // =====================================================
                // CLASS BLOCKS
                // =====================================================

                entries.forEach { entry ->

                    val dayIndex =
                        days.indexOf(
                            entry.day
                        )


                    if (dayIndex >= 0) {

                        val startMinutes =
                            timeToMinutes(
                                entry.startTime
                            )

                        val endMinutes =
                            timeToMinutes(
                                entry.endTime
                            )


                        if (
                            startMinutes >= 0 &&
                            endMinutes > startMinutes
                        ) {

                            val timetableStartMinutes =
                                startHour * 60

                            val relativeStart =
                                startMinutes -
                                        timetableStartMinutes

                            val duration =
                                endMinutes -
                                        startMinutes


                            // Actual vertical position
                            val yOffset =
                                hourHeight *
                                        (
                                                relativeStart / 60f
                                                )


                            // Actual duration height
                            val calculatedHeight =
                                hourHeight *
                                        (
                                                duration / 60f
                                                )


                            // Minimum block height
                            val classHeight =
                                if (
                                    calculatedHeight < 48.dp
                                ) {

                                    48.dp

                                } else {

                                    calculatedHeight
                                }


                            val xOffset =
                                timeColumnWidth +
                                        (
                                                dayWidth *
                                                        dayIndex
                                                )


                            TimetableClassBlock(
                                entry = entry,
                                modifier = Modifier
                                    .offset(
                                        x = xOffset + 2.dp,
                                        y = yOffset + 2.dp
                                    )
                                    .width(
                                        dayWidth - 4.dp
                                    )
                                    .height(
                                        classHeight - 4.dp
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}


// =====================================================================
// CLASS BLOCK
// =====================================================================

@Composable
private fun TimetableClassBlock(
    entry: TimetableEntry,
    modifier: Modifier = Modifier
) {

    val backgroundColor =
        getSubjectColor(
            entry.subject
        )


    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(7.dp)
            )
            .background(
                backgroundColor
            )
            .border(
                width = 1.dp,
                color = backgroundColor.copy(
                    alpha = 0.9f
                ),
                shape = RoundedCornerShape(7.dp)
            )
            .padding(
                horizontal = 3.dp,
                vertical = 4.dp
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {

            // =========================================================
            // SUBJECT
            // =========================================================

            Text(
                text = entry.subject,
                fontSize = 10.sp,
                lineHeight = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF20232D),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


            Spacer(
                modifier = Modifier.height(3.dp)
            )


            // =========================================================
            // START TIME
            // =========================================================

            Text(
                text = entry.startTime,
                fontSize = 9.sp,
                lineHeight = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3F4552),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            // =========================================================
            // ROOM
            // =========================================================

            if (
                entry.room.isNotBlank()
            ) {

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = entry.room,
                    fontSize = 9.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3F4552),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


// =====================================================================
// SUBJECT COLORS
// =====================================================================

private fun getSubjectColor(
    subject: String
): Color {

    return when (
        subject.lowercase()
    ) {

        "biology" ->
            Color(0xFFCFEFD8)

        "mathematics" ->
            Color(0xFFE8DDFB)

        "physics" ->
            Color(0xFFFFE7A8)

        "chemistry" ->
            Color(0xFFFFD5E0)

        "english" ->
            Color(0xFFD6E5FF)

        "computer science" ->
            Color(0xFFD7F2F3)

        else ->
            Color(0xFFE5E8F4)
    }
}


// =====================================================================
// FORMAT TIME LABEL
// =====================================================================

private fun formatHourLabel(
    hour: Int
): String {

    val displayHour =
        when {

            hour == 0 ->
                12

            hour > 12 ->
                hour - 12

            else ->
                hour
        }


    val period =
        if (
            hour >= 12
        ) {

            "PM"

        } else {

            "AM"
        }


    return "$displayHour:00\n$period"
}


// =====================================================================
// CONVERT TIME TO MINUTES
// =====================================================================

private fun timeToMinutes(
    time: String
): Int {

    return try {

        val parts =
            time
                .trim()
                .split(" ")


        if (
            parts.size != 2
        ) {

            return -1
        }


        val clock =
            parts[0]
                .split(":")


        if (
            clock.size != 2
        ) {

            return -1
        }


        var hour =
            clock[0]
                .toInt()

        val minute =
            clock[1]
                .toInt()

        val period =
            parts[1]
                .uppercase()


        if (
            hour == 12
        ) {

            hour = 0
        }


        if (
            period == "PM"
        ) {

            hour += 12
        }


        hour * 60 +
                minute

    } catch (
        e: Exception
    ) {

        -1
    }
}


