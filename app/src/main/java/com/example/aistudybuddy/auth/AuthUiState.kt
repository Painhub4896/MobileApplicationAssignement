package com.example.aistudybuddy.auth

sealed interface AuthUiState {

    data object Idle : AuthUiState

    data object Loading : AuthUiState

    data object Authenticated : AuthUiState

    data object NotAuthenticated : AuthUiState

    data object EmailConfirmationRequired : AuthUiState

    data class Message(
        val message: String
    ) : AuthUiState

    data class Error(
        val message: String
    ) : AuthUiState
}