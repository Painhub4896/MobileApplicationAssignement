package com.example.aistudybuddy.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navDeepLink
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aistudybuddy.auth.AuthUiState
import com.example.aistudybuddy.auth.AuthViewModel
import com.example.aistudybuddy.data.TimetableEntry
import com.example.aistudybuddy.screens.AIRoutineSetupScreen
import com.example.aistudybuddy.screens.AssignmentTrackerScreen
import com.example.aistudybuddy.screens.FocusTimerScreen
import com.example.aistudybuddy.screens.GeneratedRoutineScreen
import com.example.aistudybuddy.screens.HomeScreen
import com.example.aistudybuddy.screens.LoginScreen
import com.example.aistudybuddy.screens.NotificationScreen
import com.example.aistudybuddy.screens.ProfileScreen
import com.example.aistudybuddy.screens.ProgressDashboardScreen
import com.example.aistudybuddy.screens.ResetPasswordScreen
import com.example.aistudybuddy.screens.SignUpScreen
import com.example.aistudybuddy.screens.SplashScreen
import com.example.aistudybuddy.screens.StudyPlannerScreen
import com.example.aistudybuddy.screens.TimetableSetupScreen
import com.example.aistudybuddy.screens.WeeklyTimetableScreen
import com.example.aistudybuddy.viewmodel.AIRoutineViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AppNavigation(incomingDeepLink: Uri?) {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel =
        viewModel()

    // Shared Gemini ViewModel
    val aiRoutineViewModel: AIRoutineViewModel =
        viewModel()

    // Timetable data
    val timetableEntries =
        remember {
            mutableStateListOf<TimetableEntry>()
        }

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
            navController.navigate(
                Routes.ResetPassword
            ) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = initialDestination
    ) {

        // =========================================================
        // SPLASH
        // =========================================================

        composable(Routes.Splash) {

            val uiState by
            authViewModel.uiState
                .collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                delay(1500.milliseconds)
                authViewModel.checkSession()
            }

            LaunchedEffect(uiState) {

                when (uiState) {

                    AuthUiState.Authenticated -> {

                        navController.navigate(
                            Routes.Home
                        ) {
                            popUpTo(Routes.Splash) {
                                inclusive = true
                            }
                        }

                        authViewModel.resetState()
                    }

                    AuthUiState.NotAuthenticated -> {

                        navController.navigate(
                            Routes.Login
                        ) {
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

        // =========================================================
        // LOGIN
        // =========================================================

        composable(Routes.Login) {

            LoginScreen(
                authViewModel = authViewModel,

                onLoginSuccess = {

                    navController.navigate(
                        Routes.Home
                    ) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                    }
                },

                onSignUpClick = {

                    authViewModel.resetState()

                    navController.navigate(
                        Routes.SignUp
                    )
                }
            )
        }

        // =========================================================
        // SIGN UP
        // =========================================================

        composable(Routes.SignUp) {

            SignUpScreen(
                authViewModel = authViewModel,

                onSignUpSuccess = {

                    navController.navigate(
                        Routes.Home
                    ) {
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

        // =========================================================
        // RESET PASSWORD
        // =========================================================

        composable(
            route = Routes.ResetPassword,

            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "aistudybuddy://reset-password"
                }
            )
        ) {

            ResetPasswordScreen(
                authViewModel = authViewModel,

                onPasswordUpdated = {

                    authViewModel.logout(
                        onSuccess = {

                            navController.navigate(
                                Routes.Login
                            ) {

                                popUpTo(
                                    Routes.ResetPassword
                                ) {
                                    inclusive = true
                                }
                            }

                            authViewModel.resetState()
                        }
                    )
                }
            )
        }

        // =========================================================
        // HOME
        // =========================================================

        composable(Routes.Home) {

            HomeScreen(
                onHomeClick = {navController.navigate(Routes.Home){launchSingleTop} },
                onPlannerClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop}},
                onAssignmentClick = {navController.navigate(Routes.AssignmentTracker){launchSingleTop}},
                onProgressClick = {navController.navigate(Routes.ProgressDashboard){launchSingleTop} },
                onProfileClick = {navController.navigate(Routes.Profile){launchSingleTop} },
                onNotificationClick = {navController.navigate(Routes.Notification){launchSingleTop}},
                onTimerClick = {navController.navigate(Routes.FocusTimer){launchSingleTop} },
                onLogoutClick = {

                    authViewModel.logout(
                        onSuccess = {

                            navController.navigate(
                                Routes.Login
                            ) {

                                popUpTo(
                                    Routes.Home
                                ) {
                                    inclusive = true
                                }
                            }

                            authViewModel.resetState()
                        }
                    )
                }
            )
        }

        // =========================================================
        // Assignment Tracker
        // =========================================================
        composable(Routes.AssignmentTracker){
            AssignmentTrackerScreen(
                onHomeClick = {navController.navigate(Routes.Home){launchSingleTop} },
                onPlannerClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop}},
                onAssignmentClick = {navController.navigate(Routes.AssignmentTracker){launchSingleTop}},
                onProgressClick = {navController.navigate(Routes.ProgressDashboard){launchSingleTop} },
                onProfileClick = {navController.navigate(Routes.Profile){launchSingleTop} }
            )
        }

        // =========================================================
        // Study Planner
        // =========================================================

        composable(Routes.StudyPlanner) {

            StudyPlannerScreen(

                onTimetableSetupClick = {

                    navController.navigate(
                        Routes.TimetableSetup
                    )
                },

                onWeeklyTimetableClick = {

                    navController.navigate(
                        Routes.WeeklyTimetable
                    )
                },
                onHomeClick = {navController.navigate(Routes.Home){launchSingleTop} },
                onPlannerClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop}},
                onAssignmentClick = {navController.navigate(Routes.AssignmentTracker){launchSingleTop}},
                onProgressClick = {navController.navigate(Routes.ProgressDashboard){launchSingleTop} },
                onProfileClick = {navController.navigate(Routes.Profile){launchSingleTop} }
            )
        }

        // =========================================================
        // TIMETABLE SETUP
        // =========================================================

        composable(Routes.TimetableSetup) {

            TimetableSetupScreen(

                onBackClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop} },
                existingEntries =
                    timetableEntries,

                onAddClass = { entry ->

                    timetableEntries.add(
                        entry
                    )
                },

                onViewTimetable = {

                    navController.navigate(
                        Routes.WeeklyTimetable
                    )
                }
            )
        }

        // =========================================================
        // WEEKLY TIMETABLE
        // =========================================================

        composable(Routes.WeeklyTimetable) {

            WeeklyTimetableScreen(
                onBackClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop} },
                entries =
                    timetableEntries,

                onAddClassClick = {

                    navController.navigate(
                        Routes.TimetableSetup
                    )
                },

                onGenerateRoutineClick = {

                    aiRoutineViewModel
                        .clearRoutine()

                    navController.navigate(
                        Routes.AIRoutineSetup
                    )
                }
            )
        }

        // =========================================================
        // AI ROUTINE SETUP
        // =========================================================

        composable(Routes.AIRoutineSetup) {

            AIRoutineSetupScreen(

                timetableEntries =
                    timetableEntries,

                aiRoutineViewModel =
                    aiRoutineViewModel,

                onGenerateClick = {

                    navController.navigate(
                        Routes.GeneratedRoutine
                    )
                }
            )
        }

        // =========================================================
        // GEMINI GENERATED ROUTINE
        // =========================================================

        composable(Routes.GeneratedRoutine) {

            GeneratedRoutineScreen(

                sessions =
                    aiRoutineViewModel
                        .generatedSessions,

                onAcceptClick = {

                    navController.navigate(
                        Routes.StudyPlanner
                    ) {

                        popUpTo(
                            Routes.StudyPlanner
                        ) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                },

                onAdjustClick = {

                    navController.popBackStack()
                }
            )
        }

        // =========================================================
        // FOCUS TIMER
        // =========================================================

        composable(Routes.FocusTimer) {
            FocusTimerScreen(
                onBackClick = {navController.navigate(Routes.Home){launchSingleTop} }
            )
        }

        // =========================================================
        // FOCUS TIMER
        // =========================================================
        composable(Routes.Notification){
            NotificationScreen(
                onBackClick = {navController.navigate(Routes.Home){launchSingleTop} }
            )
        }

        // =========================================================
        // PROGRESS
        // =========================================================

        composable(
            Routes.ProgressDashboard
        ) {
            ProgressDashboardScreen(
                onHomeClick = {navController.navigate(Routes.Home){launchSingleTop} },
                onPlannerClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop}},
                onAssignmentClick = {navController.navigate(Routes.AssignmentTracker){launchSingleTop}},
                onProgressClick = {navController.navigate(Routes.ProgressDashboard){launchSingleTop} },
                onProfileClick = {navController.navigate(Routes.Profile){launchSingleTop} }
            )
        }

        // =========================================================
        // Profile
        // =========================================================

        composable(Routes.Profile){
            ProfileScreen(
                onHomeClick = {navController.navigate(Routes.Home){launchSingleTop} },
                onPlannerClick = {navController.navigate(Routes.StudyPlanner){launchSingleTop}},
                onAssignmentClick = {navController.navigate(Routes.AssignmentTracker){launchSingleTop}},
                onProgressClick = {navController.navigate(Routes.ProgressDashboard){launchSingleTop} },
                onProfileClick = {navController.navigate(Routes.Profile){launchSingleTop} }
            )
        }
    }
}