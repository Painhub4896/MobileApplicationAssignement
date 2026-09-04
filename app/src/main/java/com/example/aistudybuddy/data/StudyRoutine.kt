package com.example.aistudybuddy.data

data class StudyRoutine(
    val id: Long,
    val name: String,
    val sessions: List<GeneratedStudySession>
)
