package com.example.aistudybuddy.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aistudybuddy.auth.AuthUiState
import com.example.aistudybuddy.auth.AuthViewModel
import com.example.aistudybuddy.screens.HomeScreen
import com.example.aistudybuddy.screens.LoginScreen
import com.example.aistudybuddy.screens.StudyPlannerScreen
import com.example.aistudybuddy.screens.SignUpScreen
import com.example.aistudybuddy.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import androidx.navigation.navDeepLink
import com.example.aistudybuddy.screens.FocusTimerScreen
import com.example.aistudybuddy.screens.ProgressDashboardScreen
import com.example.aistudybuddy.screens.ResetPasswordScreen


@Composable
fun AppNavigation(incomingDeepLink: Uri?) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val isPasswordResetLink =
        incomingDeepLink?.scheme == "aistudybuddy" &&
                incomingDeepLink.host == "reset-password"

    val initialDestination =
        if (isPasswordResetLink) {
            Routes.ResetPassword
        } else {
            Routes.Splash
        }

    LaunchedEffect(incomingDeepLink) {
        if (
            isPasswordResetLink &&
            navController.currentDestination?.route !=
            Routes.ResetPassword
        ) {
            navController.navigate(Routes.ResetPassword) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = initialDestination
    ) {
        composable(Routes.Splash) {
            val uiState by authViewModel.uiState
                .collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                delay(1500.milliseconds)
                authViewModel.checkSession()
            }

            LaunchedEffect(uiState) {
                when (uiState) {
                    AuthUiState.Authenticated -> {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Splash) {
                                inclusive = true
                            }
                        }

                        authViewModel.resetState()
                    }

                    AuthUiState.NotAuthenticated -> {
                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Splash) {
                                inclusive = true
                            }
                        }

                        authViewModel.resetState()
                    }

                    else -> Unit
                }
            }

            SplashScreen()
        }

        composable(Routes.Login) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    authViewModel.resetState()
                    navController.navigate(Routes.SignUp)
                }
            )
        }

        composable(Routes.SignUp) {
            SignUpScreen(
                authViewModel = authViewModel,
                onSignUpSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    authViewModel.resetState()
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.ResetPassword,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "aistudybuddy://reset-password"
                }
            )
        ) {
            ResetPasswordScreen(
                authViewModel = authViewModel,
                onPasswordUpdated = {
                    authViewModel.logout(
                        onSuccess = {
                            navController.navigate(Routes.Login) {
                                popUpTo(Routes.ResetPassword) {
                                    inclusive = true
                                }
                            }

                            authViewModel.resetState()
                        }
                    )
                }
            )
        }

        composable(Routes.Home) {
            HomeScreen(
                onStudyPlannerClick = {
                    navController.navigate(Routes.StudyPlanner)
                },

                onLogoutClick = {
                    authViewModel.logout(
                        onSuccess = {
                            navController.navigate(Routes.Login) {
                                popUpTo(Routes.Home) {
                                    inclusive = true
                                }
                            }

                            authViewModel.resetState()
                        }
                    )
                }
            )
        }
        composable(Routes.StudyPlanner) {
            StudyPlannerScreen()
        }
        composable(Routes.FocusTimer) {
            FocusTimerScreen()
        }

        composable(Routes.ProgressDashboard) {
            ProgressDashboardScreen()
        }
    }
}