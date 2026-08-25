package com.example.aistudybuddy.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aistudybuddy.data.SupabaseProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import android.util.Patterns

class AuthViewModel : ViewModel() {

    private val supabase = SupabaseProvider.client

    private val _uiState =
        MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    val uiState = _uiState.asStateFlow()

    fun checkSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                val currentSession =
                    supabase.auth.currentSessionOrNull()

                _uiState.value =
                    if (currentSession != null) {
                        AuthUiState.Authenticated
                    } else {
                        AuthUiState.NotAuthenticated
                    }
            } catch (exception: Exception) {
                _uiState.value =
                    AuthUiState.NotAuthenticated
            }
        }
    }
    fun login(
        email: String,
        password: String
    ) {
        when {
            email.isBlank() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter your email.")
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter a valid email address.")
                return
            }

            password.isBlank() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter your password.")
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                supabase.auth.signInWith(Email) {
                    this.email = email.trim()
                    this.password = password
                }

                _uiState.value = AuthUiState.Authenticated
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    "Incorrect email or password."
                )
            }
        }
    }

    fun signUp(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val passwordError = passwordValidationMessage(password)
        when {
            fullName.isBlank() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter your full name.")
                return
            }

            email.isBlank() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter your email.")
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                _uiState.value =
                    AuthUiState.Error("Please enter a valid email address.")
                return
            }

            passwordError != null -> {
                _uiState.value =
                    AuthUiState.Error(passwordError)
                return
            }

            password != confirmPassword -> {
                _uiState.value =
                    AuthUiState.Error("Passwords do not match.")
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email.trim()
                    this.password = password

                    data = buildJsonObject {
                        put("full_name", fullName.trim())
                    }
                }

                _uiState.value =
                    if (supabase.auth.currentSessionOrNull() != null) {
                        AuthUiState.Authenticated
                    } else {
                        AuthUiState.EmailConfirmationRequired
                    }
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    "Unable to update your password. Please request a new reset link."
                )
            }
        }
    }

    fun sendPasswordReset(
        email: String
    ) {
        if (email.isBlank()) {
            _uiState.value =
                AuthUiState.Error("Please enter your email first.")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _uiState.value =
                AuthUiState.Error("Please enter a valid email address.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                supabase.auth.resetPasswordForEmail(
                    email = email.trim(),
                    redirectUrl = "aistudybuddy://reset-password"
                )

                _uiState.value = AuthUiState.Message(
                    "If an account uses this email, a password-reset link has been sent."
                )
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    exception.message
                        ?: "Unable to send the password-reset email."
                )
            }
        }
    }

    fun updatePassword(
        newPassword: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        val passwordError = passwordValidationMessage(newPassword)
        when {
            passwordError != null -> {
                _uiState.value =
                    AuthUiState.Error(passwordError)
                return
            }

            newPassword != confirmPassword -> {
                _uiState.value =
                    AuthUiState.Error("Passwords do not match.")
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                supabase.auth.updateUser {
                    password = newPassword
                }

                _uiState.value = AuthUiState.Message(
                    "Password updated successfully."
                )

                onSuccess()
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    exception.message
                        ?: "Unable to update the password."
                )
            }
        }
    }
    fun logout(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                supabase.auth.signOut()

                _uiState.value = AuthUiState.NotAuthenticated
                onSuccess()
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    exception.message ?: "Unable to log out."
                )
            }
        }
    }

    private fun passwordValidationMessage(
        password: String
    ): String? {
        return when {
            password.length < 8 ->
                "Password must contain at least 8 characters."

            password.none { it.isUpperCase() } ->
                "Password must contain an uppercase letter."

            password.none { it.isLowerCase() } ->
                "Password must contain a lowercase letter."

            password.none { it.isDigit() } ->
                "Password must contain a number."

            else -> null
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}