package com.example.aistudybuddy.data

import androidx.compose.runtime.mutableStateListOf
import java.time.DayOfWeek
import java.time.LocalDate


data class StudySession(
    val id: Long,
    val date: LocalDate,
    val subject: String,
    val startTime: String,
    val endTime: String,
    val room: String? = null,
    val repeatDays: Set<DayOfWeek>? = null,
    val colorIndex: Int
)


object StudyPlannerData {

    // All study sessions created by the user
    val sessions =
        mutableStateListOf<StudySession>()


    private var nextId = 1L


    fun generateId(): Long {

        return nextId++
    }


    // ==========================================
    // GET SESSIONS FOR A SPECIFIC DATE
    // ==========================================

    fun getSessionsForDate(
        date: LocalDate
    ): List<StudySession> {

        return sessions
            .filter { session ->

                // Normal session created on this date
                session.date == date ||

                        // Repeating session
                        (
                                session.repeatDays
                                    ?.contains(
                                        date.dayOfWeek
                                    ) == true &&

                                        // Do not show repeating session
                                        // before its original starting date
                                        !date.isBefore(
                                            session.date
                                        )
                                )
            }
            .sortedBy { session ->

                convertTimeToMinutes(
                    session.startTime
                )
            }
    }


    // ==========================================
    // CONVERT TIME TO MINUTES
    // USED FOR SORTING
    // ==========================================

    private fun convertTimeToMinutes(
        time: String
    ): Int {

        val parts =
            time
                .trim()
                .split(" ")

        val timePart =
            parts[0]

        val amPm =
            parts[1]

        val hourMinute =
            timePart
                .split(":")

        var hour =
            hourMinute[0]
                .toInt()

        val minute =
            hourMinute[1]
                .toInt()


        if (
            amPm.equals(
                "PM",
                ignoreCase = true
            ) &&
            hour != 12
        ) {

            hour += 12
        }


        if (
            amPm.equals(
                "AM",
                ignoreCase = true
            ) &&
            hour == 12
        ) {

            hour = 0
        }


        return hour * 60 + minute
    }
}