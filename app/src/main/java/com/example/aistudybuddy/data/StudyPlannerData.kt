package com.example.aistudybuddy.data

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate

data class StudySession(
    val id: Long,
    val date: LocalDate,
    val subject: String,
    val time: String,
    val colorIndex: Int
)

object StudyPlannerData {

    val sessions = mutableStateListOf(
        StudySession(
            id = 1,
            date = LocalDate.now(),
            subject = "Mathematics",
            time = "10:00 AM – 11:00 AM",
            colorIndex = 0
        ),

        StudySession(
            id = 2,
            date = LocalDate.now(),
            subject = "Physics",
            time = "12:00 PM – 1:00 PM",
            colorIndex = 1
        ),

        StudySession(
            id = 3,
            date = LocalDate.now(),
            subject = "Bahasa Melayu",
            time = "5:00 PM – 6:00 PM",
            colorIndex = 2
        ),

        StudySession(
            id = 4,
            date = LocalDate.now(),
            subject = "Biology Revision",
            time = "8:00 PM – 9:00 PM",
            colorIndex = 3
        )
    )

    private var nextId = 5L

    fun generateId(): Long {
        return nextId++
    }
}