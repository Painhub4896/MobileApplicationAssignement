package com.example.aistudybuddy.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

data class CompletedStudySession(
    val title: String,
    val minutes: Int,
    val dateTime: LocalDateTime
)

object StudyProgress {

    var completedSessions = mutableStateOf(0)

    var completedMinutes = mutableStateOf(0)

    val completedStudySessions =
        mutableStateListOf<CompletedStudySession>()

    private val malaysiaZone = ZoneId.of("Asia/Kuala_Lumpur")

    fun addCompletedSession(
        minutes: Int,
        title: String = "Focus Study Session"
    ) {
        completedSessions.value++
        completedMinutes.value += minutes

        completedStudySessions.add(
            0,
            CompletedStudySession(
                title = title,
                minutes = minutes,
                dateTime = LocalDateTime.now(malaysiaZone)
            )
        )

        if (completedStudySessions.size > 10) {
            completedStudySessions.removeAt(
                completedStudySessions.size - 1
            )
        }
    }

    fun getThisWeekMinutes(): Int {

        val today = LocalDate.now(malaysiaZone)

        val startOfWeek =
            today.minusDays(
                today.dayOfWeek.value.toLong() - 1
            )

        return completedStudySessions
            .filter { session ->
                val sessionDate = session.dateTime.toLocalDate()

                !sessionDate.isBefore(startOfWeek) &&
                        !sessionDate.isAfter(today)
            }
            .sumOf { session ->
                session.minutes
            }
    }

    fun getDailyStudyMinutes(): List<Int> {

        val today = LocalDate.now()

        val startOfWeek =
            today.minusDays(
                today.dayOfWeek.value.toLong() - 1
            )

        return (0..6).map { dayOffset ->

            val date = startOfWeek.plusDays(dayOffset.toLong())

            completedStudySessions
                .filter { session ->
                    session.dateTime.toLocalDate() == date
                }
                .sumOf { session ->
                    session.minutes
                }
        }
    }

    fun getStudyStreak(): Int {

        if (completedStudySessions.isEmpty()) {
            return 0
        }

        val studyDates =
            completedStudySessions
                .map { it.dateTime.toLocalDate() }
                .distinct()
                .sortedDescending()

        val today = LocalDate.now(malaysiaZone)

        // If the user has not studied today,
        // start checking from yesterday.
        var checkDate =
            if (studyDates.contains(today)) {
                today
            } else {
                today.minusDays(1)
            }

        var streak = 0

        for (date in studyDates) {

            if (date == checkDate) {
                streak++
                checkDate = checkDate.minusDays(1)
            } else if (date.isBefore(checkDate)) {
                break
            }
        }

        return streak
    }

    fun getStudyHoursText(): String {

        val minutes = completedMinutes.value

        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return if (hours > 0) {
            "$hours hr $remainingMinutes m"
        } else {
            "$remainingMinutes min"
        }
    }
}