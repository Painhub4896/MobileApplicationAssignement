package com.example.aistudybuddy.data

data class GeneratedStudySession(
    val subject: String,
    val startTime: String,
    val endTime: String,
    val task: String,
    val reason: String,
    val location: String = "Study Area"
)