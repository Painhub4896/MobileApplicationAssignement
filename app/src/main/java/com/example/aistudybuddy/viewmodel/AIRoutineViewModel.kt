package com.example.aistudybuddy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aistudybuddy.data.GeneratedStudySession
import com.example.aistudybuddy.data.GeminiRepository
import com.example.aistudybuddy.data.TimetableEntry
import kotlinx.coroutines.launch
import org.json.JSONArray

class AIRoutineViewModel : ViewModel() {

    private val repository =
        GeminiRepository()

    var generatedSessions by
    mutableStateOf<List<GeneratedStudySession>>(
        emptyList()
    )
        private set

    var isLoading by
    mutableStateOf(false)
        private set

    var errorMessage by
    mutableStateOf<String?>(null)
        private set

    fun generateRoutine(
        timetableEntries: List<TimetableEntry>,
        availableStart: String,
        availableEnd: String,
        sessionLength: String,
        difficulty: String,
        upcomingTest: String,
        assignment: String,
        onSuccess: () -> Unit
    ) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response =
                    repository.generateStudyRoutine(
                        timetableEntries,
                        availableStart,
                        availableEnd,
                        sessionLength,
                        difficulty,
                        upcomingTest,
                        assignment
                    )

                val sessions =
                    parseSessions(response)

                if (sessions.isEmpty()) {
                    throw Exception(
                        "Gemini did not generate any study sessions."
                    )
                }

                generatedSessions =
                    sessions

                onSuccess()

            } catch (e: Exception) {
                errorMessage =
                    e.message
                        ?: "Unable to generate study routine."
            } finally {
                isLoading = false
            }
        }
    }

    private fun parseSessions(
        response: String
    ): List<GeneratedStudySession> {

        val clean =
            response
                .replace("```json", "")
                .replace("```JSON", "")
                .replace("```", "")
                .trim()

        val jsonArray =
            JSONArray(clean)

        val sessions =
            mutableListOf<GeneratedStudySession>()

        for (i in 0 until jsonArray.length()) {
            val item =
                jsonArray.getJSONObject(i)

            sessions.add(
                GeneratedStudySession(
                    subject =
                        item.optString("subject")
                            .ifBlank {
                                "Study Session"
                            },

                    startTime =
                        item.optString("startTime"),

                    endTime =
                        item.optString("endTime"),

                    task =
                        item.optString("task")
                            .ifBlank {
                                "Review study materials"
                            },

                    reason =
                        item.optString("reason")
                            .ifBlank {
                                "Selected to support your studies."
                            },

                    location =
                        item.optString(
                            "location",
                            "Study Area"
                        ).ifBlank {
                            "Study Area"
                        }
                )
            )
        }

        return sessions
    }

    fun clearRoutine() {
        generatedSessions =
            emptyList()

        errorMessage =
            null
    }

    fun clearError() {
        errorMessage = null
    }
}