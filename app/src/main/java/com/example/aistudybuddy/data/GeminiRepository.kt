package com.example.aistudybuddy.data

import com.example.aistudybuddy.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class GeminiRepository {

    suspend fun generateStudyRoutine(
        timetableEntries: List<TimetableEntry>,
        availableStart: String,
        availableEnd: String,
        sessionLength: String,
        difficulty: String,
        upcomingTest: String,
        assignment: String
    ): String = withContext(Dispatchers.IO) {

        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isBlank()) {
            throw Exception("Gemini API key is missing.")
        }

        val timetableText =
            if (timetableEntries.isEmpty()) {
                "No timetable classes were provided."
            } else {
                timetableEntries.joinToString("\n") { entry ->
                    """
                    Subject: ${entry.subject}
                    Day: ${entry.day}
                    Time: ${entry.startTime} - ${entry.endTime}
                    Room: ${entry.room}
                    """.trimIndent()
                }
            }

        val prompt = """
            You are an AI study planning assistant for an Android application called AIStudyBuddy.

            Create a realistic study routine.

            SCHOOL TIMETABLE:
            $timetableText

            STUDENT PREFERENCES:
            Available study time: $availableStart to $availableEnd
            Preferred session length: $sessionLength
            Study intensity: $difficulty
            Upcoming test: ${upcomingTest.ifBlank { "None" }}
            Assignment/project: ${assignment.ifBlank { "None" }}

            Rules:
            1. Generate exactly 3 study sessions.
            2. Prefer subjects from the timetable.
            3. Give priority to upcoming tests.
            4. Give priority to assignments/projects.
            5. Keep all sessions inside the available time.
            6. Do not overlap sessions.
            7. Give each session a useful study task.
            8. Give a short reason for the session.
            9. Return ONLY JSON.

            Return:
            [
              {
                "subject": "Biology",
                "startTime": "7:00 PM",
                "endTime": "7:45 PM",
                "task": "Review Chapter 3",
                "reason": "Biology has an upcoming test.",
                "location": "Study Area"
              }
            ]

            Do not include markdown.
            Do not include explanation outside JSON.
        """.trimIndent()

        val requestBody = JSONObject().apply {
            put(
                "contents",
                JSONArray().apply {
                    put(
                        JSONObject().apply {
                            put("role", "user")

                            put(
                                "parts",
                                JSONArray().apply {
                                    put(
                                        JSONObject().apply {
                                            put("text", prompt)
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )

            put(
                "generationConfig",
                JSONObject().apply {
                    put("temperature", 0.6)
                    put("responseMimeType", "application/json")
                }
            )
        }

        val url = URL(
            "https://generativelanguage.googleapis.com/" +
                    "v1beta/models/gemini-3.5-flash-lite:generateContent"
        )

        val connection =
            url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.connectTimeout = 30000
            connection.readTimeout = 120000

            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            )

            connection.setRequestProperty(
                "x-goog-api-key",
                apiKey
            )

            connection.doInput = true
            connection.doOutput = true

            connection.outputStream.use { outputStream ->
                outputStream.write(
                    requestBody
                        .toString()
                        .toByteArray(Charsets.UTF_8)
                )
            }

            val responseCode =
                connection.responseCode

            val responseText =
                if (responseCode in 200..299) {
                    connection
                        .inputStream
                        .bufferedReader()
                        .use { it.readText() }
                } else {
                    connection
                        .errorStream
                        ?.bufferedReader()
                        ?.use { it.readText() }
                        ?: "Unknown Gemini error"
                }

            if (responseCode !in 200..299) {
                throw Exception(
                    "Gemini API error $responseCode: $responseText"
                )
            }

            val responseJson =
                JSONObject(responseText)

            val candidates =
                responseJson.optJSONArray("candidates")
                    ?: throw Exception(
                        "Gemini returned no response."
                    )

            if (candidates.length() == 0) {
                throw Exception(
                    "Gemini returned no study routine."
                )
            }

            val content =
                candidates
                    .getJSONObject(0)
                    .optJSONObject("content")
                    ?: throw Exception(
                        "Gemini returned no content."
                    )

            val parts =
                content.optJSONArray("parts")
                    ?: throw Exception(
                        "Gemini returned no text."
                    )

            if (parts.length() == 0) {
                throw Exception(
                    "Gemini response was empty."
                )
            }

            parts
                .getJSONObject(0)
                .optString("text")
                .ifBlank {
                    throw Exception(
                        "Gemini returned an empty routine."
                    )
                }

        } catch (e: SocketTimeoutException) {
            throw Exception(
                "Gemini request timed out. Please try again."
            )
        } finally {
            connection.disconnect()
        }
    }
}