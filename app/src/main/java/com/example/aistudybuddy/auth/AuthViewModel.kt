package com.example.aistudybuddy.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aistudybuddy.data.SupabaseProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthViewModel : ViewModel() {

    private val supabase = SupabaseProvider.client

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    // Current User Profile State
    private val _userName = MutableStateFlow<String?>(null)
    val userName = _userName.asStateFlow()

    // Current User Email State
    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail = _userEmail.asStateFlow()

    // SESSION
    fun checkSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val session = supabase.auth.currentSessionOrNull()
                _uiState.value = if (session != null) {
                    fetchUserProfile()
                    AuthUiState.Authenticated
                } else {
                    AuthUiState.NotAuthenticated
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.NotAuthenticated
            }
        }
    }

    // ===================== FETCH USER EMAIL =====================
    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val currentUser = supabase.auth.currentUserOrNull()

                _userEmail.value = currentUser?.email

                _userName.value =
                    (currentUser?.userMetadata?.get("full_name") as? JsonPrimitive)?.content

            } catch (e: Exception) {
                // Silent fail - keep existing user data
            }
        }
    }

    // ===================== UPDATE USER NAME =====================
    fun updateUserName(newName: String) {

        if (newName.isBlank()) {
            _uiState.value =
                AuthUiState.Error("Name cannot be empty.")
            return
        }

        viewModelScope.launch {

            try {

                supabase.auth.updateUser {

                    data {
                        put(
                            "full_name",
                            newName.trim()
                        )
                    }
                }

                _userName.value =
                    newName.trim()

            } catch (e: Exception) {

                _uiState.value =
                    AuthUiState.Error(
                        "Unable to update name."
                    )
            }
        }
    }

    // ===================== LOGIN =====================
    fun login(email: String, password: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your email.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _uiState.value = AuthUiState.Error("Please enter a valid email address.")
            return
        }
        if (password.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your password.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                supabase.auth.signInWith(Email) {
                    this.email = email.trim()
                    this.password = password
                }
                _uiState.value = AuthUiState.Authenticated
                fetchUserProfile()
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error("Incorrect email or password.")
            }
        }
    }

    // ===================== SIGN UP =====================
    fun signUp(fullName: String, email: String, password: String, confirmPassword: String) {
        val passwordError = validatePassword(password)

        if (fullName.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your full name.")
            return
        }
        if (email.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your email.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _uiState.value = AuthUiState.Error("Please enter a valid email address.")
            return
        }
        if (passwordError != null) {
            _uiState.value = AuthUiState.Error(passwordError)
            return
        }
        if (password != confirmPassword) {
            _uiState.value = AuthUiState.Error("Passwords do not match.")
            return
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

                if (supabase.auth.currentSessionOrNull() != null) {
                    _uiState.value = AuthUiState.Authenticated
                    fetchUserProfile()
                } else {
                    _uiState.value = AuthUiState.EmailConfirmationRequired
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error("Unable to sign up. Please try again.")
            }
        }
    }

    // ===================== LOGOUT =====================
    fun logout(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                supabase.auth.signOut()
                _userName.value = null  // Clear name after logout
                _userEmail.value = null  // Clear email after logout
                _uiState.value = AuthUiState.NotAuthenticated
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Unable to log out.")
            }
        }
    }

    // ===================== RESET PASSWORD =====================
    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your email.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _uiState.value = AuthUiState.Error("Please enter a valid email address.")
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
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Unable to send reset email.")
            }
        }
    }

    // ===================== UPDATE PASSWORD =====================
    fun updatePassword(newPassword: String, confirmPassword: String, onSuccess: () -> Unit) {
        val error = validatePassword(newPassword)

        if (error != null) {
            _uiState.value = AuthUiState.Error(error)
            return
        }
        if (newPassword != confirmPassword) {
            _uiState.value = AuthUiState.Error("Passwords do not match.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                supabase.auth.updateUser {
                    password = newPassword
                }
                _uiState.value = AuthUiState.Message("Password updated successfully.")
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Unable to update password.")
            }
        }
    }

    // ===================== VALIDATE PASSWORD =====================
    private fun validatePassword(password: String): String? {
        return when {
            password.length < 8 -> "Password must contain at least 8 characters."
            password.none { it.isUpperCase() } -> "Password must contain an uppercase letter."
            password.none { it.isLowerCase() } -> "Password must contain a lowercase letter."
            password.none { it.isDigit() } -> "Password must contain a number."
            else -> null
        }
    }

    // ===================== RESET STATE =====================
    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}