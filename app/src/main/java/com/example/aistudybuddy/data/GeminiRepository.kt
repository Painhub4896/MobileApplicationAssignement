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

        val apiKey =
            BuildConfig.GEMINI_API_KEY


        if (apiKey.isBlank()) {

            throw Exception(
                "Gemini API key is missing."
            )
        }


        val timetableText =
            if (timetableEntries.isEmpty()) {

                """
                No timetable classes were provided.

                Use the upcoming test or assignment subjects if available.
                If none are provided, create general study sessions such as:
                Mathematics, Revision, Reading or Assignment Work.
                """.trimIndent()

            } else {

                timetableEntries
                    .joinToString("\n") { entry ->

                        """
                        Subject: ${entry.subject}
                        Day: ${entry.day}
                        Time: ${entry.startTime} - ${entry.endTime}
                        Room: ${entry.room}
                        """.trimIndent()
                    }
            }


        val prompt =
            """
            You are an AI study planning assistant for an Android application called AIStudyBuddy.

            Create a realistic study routine for the student.

            SCHOOL TIMETABLE:
            $timetableText

            STUDENT PREFERENCES:
            Available study time: $availableStart to $availableEnd
            Preferred session length: $sessionLength
            Study intensity: $difficulty
            Upcoming test: ${upcomingTest.ifBlank { "None" }}
            Assignment/project: ${assignment.ifBlank { "None" }}

            RULES:

            1. Generate exactly 3 study sessions.

            2. Prefer subjects from the school timetable when timetable subjects exist.

            3. If the timetable is empty, still generate 3 useful study sessions.

            4. Give higher priority to an upcoming test when one is provided.

            5. Give higher priority to an assignment or project when one is provided.

            6. Every study session must be inside:
               $availableStart to $availableEnd

            7. Try to follow the preferred session length:
               $sessionLength

            8. Do not overlap sessions.

            9. Every session must include:
               subject
               startTime
               endTime
               task
               reason
               location

            10. Times must use this format:
                7:00 PM
                8:30 PM

            11. Return ONLY a JSON array.

            REQUIRED JSON FORMAT:

            [
              {
                "subject": "Biology",
                "startTime": "7:00 PM",
                "endTime": "7:45 PM",
                "task": "Review Chapter 3",
                "reason": "Biology is prioritised because the student has an upcoming test.",
                "location": "Study Area"
              },
              {
                "subject": "Mathematics",
                "startTime": "8:00 PM",
                "endTime": "8:45 PM",
                "task": "Complete algebra practice questions",
                "reason": "Regular mathematics practice supports problem solving skills.",
                "location": "Study Area"
              },
              {
                "subject": "Computer Science",
                "startTime": "9:00 PM",
                "endTime": "9:45 PM",
                "task": "Review programming concepts",
                "reason": "This session balances the student's study routine.",
                "location": "Study Area"
              }
            ]

            IMPORTANT:

            Do not return ```json.

            Do not return markdown.

            Do not return any explanation before the JSON.

            Do not return any explanation after the JSON.

            Return only the JSON array.
            """.trimIndent()


        val requestBody =
            JSONObject().apply {

                put(
                    "contents",
                    JSONArray().apply {

                        put(
                            JSONObject().apply {

                                put(
                                    "role",
                                    "user"
                                )

                                put(
                                    "parts",
                                    JSONArray().apply {

                                        put(
                                            JSONObject().apply {

                                                put(
                                                    "text",
                                                    prompt
                                                )
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

                        put(
                            "responseMimeType",
                            "application/json"
                        )
                    }
                )
            }


        val url =
            URL(
                "https://generativelanguage.googleapis.com/" +
                        "v1beta/models/" +
                        "gemini-3.5-flash-lite:" +
                        "generateContent"
            )


        val connection =
            url.openConnection()
                    as HttpURLConnection


        try {

            connection.requestMethod =
                "POST"

            connection.connectTimeout =
                30000

            connection.readTimeout =
                120000


            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            )


            connection.setRequestProperty(
                "x-goog-api-key",
                apiKey
            )


            connection.doInput =
                true

            connection.doOutput =
                true


            connection
                .outputStream
                .use { outputStream ->

                    outputStream.write(
                        requestBody
                            .toString()
                            .toByteArray(
                                Charsets.UTF_8
                            )
                    )
                }


            val responseCode =
                connection.responseCode


            val responseText =
                if (
                    responseCode in 200..299
                ) {

                    connection
                        .inputStream
                        .bufferedReader()
                        .use {
                            it.readText()
                        }

                } else {

                    connection
                        .errorStream
                        ?.bufferedReader()
                        ?.use {
                            it.readText()
                        }
                        ?: "Unknown Gemini error"
                }


            if (
                responseCode !in 200..299
            ) {

                throw Exception(
                    getReadableGeminiError(
                        responseCode =
                            responseCode,

                        responseBody =
                            responseText
                    )
                )
            }


            val responseJson =
                JSONObject(
                    responseText
                )


            val candidates =
                responseJson
                    .optJSONArray(
                        "candidates"
                    )
                    ?: throw Exception(
                        "Gemini returned no response."
                    )


            if (
                candidates.length() == 0
            ) {

                throw Exception(
                    "Gemini returned no study routine."
                )
            }


            val candidate =
                candidates
                    .getJSONObject(0)


            val content =
                candidate
                    .optJSONObject(
                        "content"
                    )
                    ?: throw Exception(
                        "Gemini returned no content."
                    )


            val parts =
                content
                    .optJSONArray(
                        "parts"
                    )
                    ?: throw Exception(
                        "Gemini returned no text."
                    )


            if (
                parts.length() == 0
            ) {

                throw Exception(
                    "Gemini response was empty."
                )
            }


            val generatedText =
                parts
                    .getJSONObject(0)
                    .optString(
                        "text"
                    )
                    .trim()


            if (
                generatedText.isBlank()
            ) {

                throw Exception(
                    "Gemini returned an empty routine."
                )
            }


            generatedText

        } catch (
            e: SocketTimeoutException
        ) {

            throw Exception(
                "Gemini request timed out. Please try again."
            )

        } catch (
            e: Exception
        ) {

            throw e

        } finally {

            connection.disconnect()
        }
    }


    private fun getReadableGeminiError(
        responseCode: Int,
        responseBody: String
    ): String {

        return try {

            val json =
                JSONObject(
                    responseBody
                )


            val message =
                json
                    .optJSONObject(
                        "error"
                    )
                    ?.optString(
                        "message"
                    )
                    .orEmpty()


            when {

                responseCode == 400 ->

                    "Gemini request error: $message"


                responseCode == 401 ->

                    "Gemini API key is invalid."


                responseCode == 403 ->

                    "Gemini API access was denied. Check your API key."


                responseCode == 404 ->

                    "Gemini model was not found."


                responseCode == 429 ->

                    "Gemini usage limit has been reached. Please try again later."


                responseCode >= 500 ->

                    "Gemini server is temporarily unavailable. Please try again."


                message.isNotBlank() ->

                    message


                else ->

                    "Gemini API error: HTTP $responseCode"
            }

        } catch (
            _: Exception
        ) {

            "Gemini API error: HTTP $responseCode"
        }
    }
}