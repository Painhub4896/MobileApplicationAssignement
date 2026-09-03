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

// Authentication with Supabase before updating UI
class AuthViewModel : ViewModel() {

    //Get the shared Supabase client for authentication operations
    private val supabase = SupabaseProvider.client

    // Stores the current authentication state that can be updated by the ViewModel
    private val _uiState =
        MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    // Expose the authentication state to the UI as read-only
    val uiState = _uiState.asStateFlow()

    // Check whether the user already has an active Supabase login session (User logged in before)
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
                        AuthUiState.NotAuthenticated    // If return null, no user is logged in
                    }
            } catch (exception: Exception) {
                _uiState.value =
                    AuthUiState.NotAuthenticated    // Treat user as logout if failed
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
                // Authenticate the user with Supabase using email and password
                supabase.auth.signInWith(Email) {
                    this.email = email.trim()
                    this.password = password
                }
                // Login successfully, so update the application authentication state
                _uiState.value = AuthUiState.Authenticated
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    "Incorrect email or password."
                )
            }
        }
    }

    // Create a new Supabase account after validating the registration details
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
                // Register a new user using Supabase email authentication
                supabase.auth.signUpWith(Email) {
                    this.email = email.trim()
                    this.password = password

                    // Store the user's full name as Supabase user metadata
                    data = buildJsonObject {
                        put("full_name", fullName.trim())
                    }
                }

                // ** Check the purpose of this
                // Check whether Supabase logged the user in immediately or requires email confirmation
                _uiState.value =
                    if (supabase.auth.currentSessionOrNull() != null) {
                        AuthUiState.Authenticated
                    } else {
                        AuthUiState.EmailConfirmationRequired
                    }
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    "Unable to Sign Up. Please try again."
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
                // Send a password recovery email through Supabase
                supabase.auth.resetPasswordForEmail(
                    email = email.trim(),
                    // Redirect the user back to the app after opening the reset link
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
                // Update the authenticated user's password after password recovery
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
                // Sign out the current user and remove the active Supabase session
                supabase.auth.signOut()

                // Update the UI to shows currently no user is logged in
                _uiState.value = AuthUiState.NotAuthenticated
                onSuccess()
            } catch (exception: Exception) {
                _uiState.value = AuthUiState.Error(
                    exception.message ?: "Unable to log out."
                )
            }
        }
    }

    // Password Requirement:
    // 1. Must at least 8 char
    // 2. Must 1 upper and 1 lower
    // 3. Must contain a number
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