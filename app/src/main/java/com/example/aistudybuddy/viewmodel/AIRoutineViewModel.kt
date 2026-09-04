package com.example.aistudybuddy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aistudybuddy.data.GeneratedStudySession
import com.example.aistudybuddy.data.GeminiRepository
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
        availableStart: String,
        availableEnd: String,
        sessionLength: String,
        difficulty: String,
        upcomingTest: String,
        assignment: String,
        onSuccess: () -> Unit
    ) {

        if (
            isLoading
        ) {

            return
        }


        viewModelScope.launch {

            isLoading =
                true


            errorMessage =
                null


            generatedSessions =
                emptyList()


            try {

                val response =
                    repository.generateStudyRoutine(

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


                if (
                    response.isBlank()
                ) {

                    throw Exception(
                        "Gemini returned an empty response."
                    )
                }


                val sessions =
                    parseSessions(
                        response
                    )


                if (
                    sessions.isEmpty()
                ) {

                    throw Exception(
                        "Gemini did not generate any study sessions."
                    )
                }


                generatedSessions =
                    sessions


                if (
                    generatedSessions.isEmpty()
                ) {

                    throw Exception(
                        "Generated routine could not be saved."
                    )
                }


                onSuccess()


            } catch (
                e: Exception
            ) {

                generatedSessions =
                    emptyList()


                errorMessage =
                    e.message
                        ?: "Unable to generate study routine."


            } finally {

                isLoading =
                    false
            }
        }
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


    private fun parseSessions(
        response: String
    ): List<GeneratedStudySession> {

        val cleanedResponse =
            response
                .replace(
                    "```json",
                    ""
                )
                .replace(
                    "```",
                    ""
                )
                .trim()


        if (
            !cleanedResponse.startsWith("[")
        ) {

            throw Exception(
                "Gemini returned an invalid routine format."
            )
        }


        val jsonArray =
            JSONArray(
                cleanedResponse
            )


        val sessions =
            mutableListOf<GeneratedStudySession>()


        for (
        index in 0 until jsonArray.length()
        ) {

            val item =
                jsonArray
                    .getJSONObject(
                        index
                    )


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
                        task,

                    reason =
                        reason,

                    location =
                        if (
                            location.isBlank()
                        ) {

                            "Study Area"

                        } else {

                            location
                        }
                )
            )
        }


        return sessions
    }
}