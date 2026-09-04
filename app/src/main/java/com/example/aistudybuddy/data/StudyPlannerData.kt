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

    val sessions = mutableStateListOf(

        StudySession(
            id = 1,
            date = LocalDate.now(),
            subject = "Mathematics",
            startTime = "10:00 AM",
            endTime = "11:00 AM",
            room = "Room 101",
            repeatDays = null,
            colorIndex = 0
        ),

        StudySession(
            id = 2,
            date = LocalDate.now(),
            subject = "Physics",
            startTime = "12:00 PM",
            endTime = "1:00 PM",
            room = null,
            repeatDays = null,
            colorIndex = 1
        ),

        StudySession(
            id = 3,
            date = LocalDate.now(),
            subject = "Bahasa Melayu",
            startTime = "5:00 PM",
            endTime = "6:00 PM",
            room = null,
            repeatDays = null,
            colorIndex = 2
        ),

        StudySession(
            id = 4,
            date = LocalDate.now(),
            subject = "Biology Revision",
            startTime = "8:00 PM",
            endTime = "9:00 PM",
            room = null,
            repeatDays = null,
            colorIndex = 3
        )
    )


    private var nextId = 5L


    fun generateId(): Long {

        return nextId++
    }
}