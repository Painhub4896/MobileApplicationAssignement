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
            generatedSessions = emptyList()

            try {

                val response =
                    repository.generateStudyRoutine(
                        timetableEntries =
                            timetableEntries,

                        availableStart =
                            availableStart,

                        availableEnd =
                            availableEnd,

                        sessionLength =
                            sessionLength,

                        difficulty =
                            difficulty,

                        upcomingTest =
                            upcomingTest,

                        assignment =
                            assignment
                    )


                if (response.isBlank()) {

                    throw Exception(
                        "Gemini returned an empty response."
                    )
                }


                val sessions =
                    parseSessions(
                        response
                    )


                if (sessions.isEmpty()) {

                    throw Exception(
                        "Gemini did not generate any study sessions."
                    )
                }


                generatedSessions =
                    sessions


                if (generatedSessions.isEmpty()) {

                    throw Exception(
                        "Generated routine could not be saved."
                    )
                }


                onSuccess()

            } catch (e: Exception) {

                generatedSessions =
                    emptyList()

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
                .replace(
                    "```json",
                    ""
                )
                .replace(
                    "```JSON",
                    ""
                )
                .replace(
                    "```",
                    ""
                )
                .trim()


        if (clean.isBlank()) {

            throw Exception(
                "Gemini returned an empty routine."
            )
        }


        if (
            !clean.startsWith("[") ||
            !clean.endsWith("]")
        ) {

            throw Exception(
                "Gemini returned an invalid routine format."
            )
        }


        val jsonArray =
            try {

                JSONArray(
                    clean
                )

            } catch (e: Exception) {

                throw Exception(
                    "Unable to read Gemini routine: ${e.message}"
                )
            }


        if (jsonArray.length() == 0) {

            throw Exception(
                "Gemini returned no study sessions."
            )
        }


        val sessions =
            mutableListOf<GeneratedStudySession>()


        for (
        i in 0 until jsonArray.length()
        ) {

            val item =
                jsonArray
                    .getJSONObject(i)


            val subject =
                item
                    .optString(
                        "subject"
                    )
                    .trim()


            val startTime =
                item
                    .optString(
                        "startTime"
                    )
                    .trim()


            val endTime =
                item
                    .optString(
                        "endTime"
                    )
                    .trim()


            val task =
                item
                    .optString(
                        "task"
                    )
                    .trim()


            val reason =
                item
                    .optString(
                        "reason"
                    )
                    .trim()


            val location =
                item
                    .optString(
                        "location",
                        "Study Area"
                    )
                    .trim()


            if (
                subject.isBlank() ||
                startTime.isBlank() ||
                endTime.isBlank()
            ) {

                continue
            }


            sessions.add(

                GeneratedStudySession(

                    subject =
                        subject,

                    startTime =
                        startTime,

                    endTime =
                        endTime,

                    task =
                        task.ifBlank {
                            "Review study materials"
                        },

                    reason =
                        reason.ifBlank {
                            "Selected to support your studies."
                        },

                    location =
                        location.ifBlank {
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

        errorMessage =
            null
    }
}