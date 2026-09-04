package com.example.aistudybuddy.data

import com.example.aistudybuddy.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class GeminiRepository {

    suspend fun generateStudyRoutine(
        availableStart: String,
        availableEnd: String,
        sessionLength: String,
        difficulty: String,
        upcomingTest: String,
        assignment: String
    ): String = withContext(Dispatchers.IO) {


        val apiKey =
            BuildConfig.GEMINI_API_KEY


        if (
            apiKey.isBlank()
        ) {

            throw Exception(
                "Gemini API key is missing."
            )
        }


        // =====================================================
        // VALIDATE UPCOMING WORK
        // =====================================================

        if (
            upcomingTest.isBlank() &&
            assignment.isBlank()
        ) {

            throw Exception(
                "Please enter either an Upcoming Test or an Assignment / Project."
            )
        }


        if (
            upcomingTest.isNotBlank() &&
            assignment.isNotBlank()
        ) {

            throw Exception(
                "Please enter only one: Upcoming Test OR Assignment / Project."
            )
        }


        // =====================================================
        // UPCOMING WORK
        // =====================================================

        val upcomingWork =
            if (
                upcomingTest.isNotBlank()
            ) {

                """
                Type: Upcoming Test
                Details: $upcomingTest
                """.trimIndent()

            } else {

                """
                Type: Assignment / Project
                Details: $assignment
                """.trimIndent()
            }


        // =====================================================
        // PROMPT
        // =====================================================

        val prompt =
            """
            You are an AI study planning assistant for an Android application called AIStudyBuddy.

            Create a realistic study routine for the student.

            UPCOMING WORK:
            $upcomingWork

            STUDENT PREFERENCES:
            Available study time: $availableStart to $availableEnd
            Preferred session length: $sessionLength
            Study intensity: $difficulty

            RULES:

            1. Generate exactly 3 study sessions.

            2. The study routine must focus on the student's upcoming work.

            3. If the upcoming work is a test, create useful revision sessions for the test.

            4. If the upcoming work is an assignment or project, create useful working sessions for the assignment or project.

            5. Every study session must be inside:
               $availableStart to $availableEnd

            6. Try to follow the preferred session length:
               $sessionLength

            7. Adjust the study activities according to this intensity:
               $difficulty

            8. Do not overlap study sessions.

            9. Every session must contain:
               subject
               startTime
               endTime
               task
               reason
               location

            10. Times must use 12-hour format such as:
                7:00 PM
                8:30 PM

            11. Use "Study Area" as the default location.

            12. Return ONLY a JSON array.

            REQUIRED JSON FORMAT:

            [
              {
                "subject": "Biology",
                "startTime": "7:00 PM",
                "endTime": "7:45 PM",
                "task": "Review Chapter 3",
                "reason": "This session helps prepare for the upcoming Biology test.",
                "location": "Study Area"
              },
              {
                "subject": "Biology",
                "startTime": "8:00 PM",
                "endTime": "8:45 PM",
                "task": "Complete practice questions",
                "reason": "Practice questions help reinforce important Biology concepts.",
                "location": "Study Area"
              },
              {
                "subject": "Biology",
                "startTime": "9:00 PM",
                "endTime": "9:45 PM",
                "task": "Review difficult topics",
                "reason": "Reviewing difficult topics helps prepare for the upcoming test.",
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


        // =====================================================
        // REQUEST BODY
        // =====================================================

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


        // =====================================================
        // AUTOMATIC RETRY
        // =====================================================

        var attempt =
            0


        val maximumAttempts =
            3


        var retryDelay =
            2000L


        while (
            attempt < maximumAttempts
        ) {

            attempt++


            try {

                return@withContext sendGeminiRequest(
                    apiKey =
                        apiKey,

                    requestBody =
                        requestBody
                )


            } catch (
                e: GeminiServerException
            ) {


                if (
                    attempt >= maximumAttempts
                ) {

                    throw Exception(
                        "Gemini is busy right now. Please try again in a moment."
                    )
                }


                delay(
                    retryDelay
                )


                retryDelay *=
                    2
            }
        }


        throw Exception(
            "Unable to generate study routine."
        )
    }


    // =====================================================
    // SEND GEMINI REQUEST
    // =====================================================

    private fun sendGeminiRequest(
        apiKey: String,
        requestBody: JSONObject
    ): String {

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


            // =====================================================
            // TEMPORARY SERVER ERROR
            // =====================================================

            if (
                responseCode >= 500
            ) {

                throw GeminiServerException(
                    responseCode
                )
            }


            // =====================================================
            // OTHER ERRORS
            // =====================================================

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


            // =====================================================
            // READ RESPONSE
            // =====================================================

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
                    .getJSONObject(
                        0
                    )


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
                    .getJSONObject(
                        0
                    )
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


            return generatedText


        } catch (
            e: SocketTimeoutException
        ) {

            throw Exception(
                "Gemini request timed out. Please try again."
            )


        } finally {

            connection.disconnect()
        }
    }


    // =====================================================
    // READABLE GEMINI ERROR
    // =====================================================

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


    // =====================================================
    // GEMINI SERVER ERROR
    // =====================================================

    private class GeminiServerException(
        val responseCode: Int
    ) : Exception(
        "Gemini server error: HTTP $responseCode"
    )
}