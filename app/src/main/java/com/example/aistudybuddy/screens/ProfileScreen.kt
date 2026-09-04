package com.example.aistudybuddy.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aistudybuddy.auth.AuthViewModel
import com.example.aistudybuddy.components.BottomNavigationBar

val PrimaryBlue = Color(0xFF4C6FFF)
val LightBlueBg = Color(0xFFE8F0FE)
val TextDark = Color(0xFF2C3E50)
val TextGrey = Color(0xFF888888)
val RedColor = Color(0xFFFF4D4D)


// ===================== SCREEN STATE =====================
sealed class ProfileScreenState {

    object Main : ProfileScreenState()

    object About : ProfileScreenState()
}


@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    var currentScreen by remember {
        mutableStateOf<ProfileScreenState>(
            ProfileScreenState.Main
        )
    }


    // Get user email from AuthViewModel
    val userEmail by authViewModel.userEmail.collectAsStateWithLifecycle()


    when (currentScreen) {

        ProfileScreenState.Main -> {

            ProfileMainScreen(
                authViewModel = authViewModel,
                userEmail = userEmail,
                onNavigateToLogin = onNavigateToLogin,

                onNavigateToAbout = {
                    currentScreen = ProfileScreenState.About
                },

                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentsClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }


        ProfileScreenState.About -> {

            AboutScreen(
                onBackClick = {
                    currentScreen = ProfileScreenState.Main
                }
            )
        }
    }
}


// ===================== PROFILE MAIN SCREEN =====================
@Composable
fun ProfileMainScreen(
    authViewModel: AuthViewModel,
    userEmail: String?,
    onNavigateToLogin: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences(
        "UserProfile",
        Context.MODE_PRIVATE
    )


    // Name is still from SharedPreferences (editable)
    var userName by remember {

        mutableStateOf(
            sharedPref.getString(
                "name",
                "Alex Lee"
            ) ?: "Alex Lee"
        )
    }


    // Email comes from Supabase Auth (read-only)
    val displayEmail =
        userEmail ?: sharedPref.getString(
            "email",
            "alex.lee@student.com"
        )


    var showEditDialog by remember {
        mutableStateOf(false)
    }


    var isNotificationOn by remember {

        mutableStateOf(
            sharedPref.getBoolean(
                "notification_enabled",
                true
            )
        )
    }


    Scaffold(
        containerColor = Color.White,

        bottomBar = {

            BottomNavigationBar(
                selectedItem = "Profile",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentsClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {


            // =====================================================
            // HEADER
            // =====================================================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                PrimaryBlue,
                                Color(0xFF6A8DFF)
                            )
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 60.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Box {

                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(100.dp)
                        ) {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = userName
                                        .take(2)
                                        .uppercase(),
                                    fontSize = 32.sp,
                                    color = PrimaryBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }


                        // Pencil Icon - Only allows editing name, not email
                        Box(
                            modifier = Modifier
                                .align(
                                    Alignment.BottomEnd
                                )
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue)
                                .border(
                                    2.dp,
                                    Color.White,
                                    CircleShape
                                )
                                .clickable {
                                    showEditDialog = true
                                },
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit Name",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )


                    // Email from Supabase (read-only)
                    Text(
                        text = displayEmail ?: "No email",
                        color = Color.White.copy(
                            alpha = 0.8f
                        ),
                        fontSize = 14.sp
                    )
                }
            }


            // =====================================================
            // MENU LIST
            // =====================================================

            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 30.dp
                    )
            ) {


                // Edit Profile
                MenuItem(
                    icon = Icons.Outlined.Person,
                    title = "Edit Profile",
                    onClick = {
                        showEditDialog = true
                    }
                )


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                // REMOVED: Study Preferences
                // REMOVED: AI Recommendation


                // Notification Toggle
                ToggleMenuItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Notification",
                    isChecked = isNotificationOn,

                    onCheckedChange = { newValue ->

                        isNotificationOn = newValue

                        sharedPref.edit()
                            .putBoolean(
                                "notification_enabled",
                                newValue
                            )
                            .apply()
                    }
                )


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                // About AIStudyBuddy
                MenuItem(
                    icon = Icons.Outlined.Info,
                    title = "About AIStudyBuddy",
                    onClick = onNavigateToAbout
                )


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                HorizontalDivider(
                    color = Color.LightGray.copy(
                        alpha = 0.5f
                    ),
                    thickness = 1.dp
                )


                Spacer(
                    modifier = Modifier.height(20.dp)
                )


                // Log Out
                MenuItem(
                    icon = Icons.Outlined.Logout,
                    title = "Log Out",
                    textColor = RedColor,
                    iconBg = RedColor.copy(
                        alpha = 0.1f
                    ),
                    iconTint = RedColor,

                    onClick = {

                        authViewModel.logout(

                            onSuccess = {

                                onNavigateToLogin()
                            }
                        )
                    }
                )
            }
        }
    }


    // ===================== EDIT PROFILE DIALOG =====================
    if (showEditDialog) {

        EditProfileDialog(
            currentName = userName,
            currentEmail = displayEmail ?: "",

            onDismiss = {
                showEditDialog = false
            },

            onConfirm = { newName ->

                userName = newName

                showEditDialog = false


                // Save name to SharedPreferences
                sharedPref.edit()
                    .putString(
                        "name",
                        newName
                    )
                    .apply()
            }
        )
    }
}


// ===================== EDIT PROFILE DIALOG =====================
@Composable
fun EditProfileDialog(
    currentName: String,
    currentEmail: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var nameInput by remember {
        mutableStateOf(currentName)
    }


    AlertDialog(
        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "Edit Profile"
            )
        },

        text = {

            Column {

                // Name field - Editable
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = {
                        nameInput = it
                    },
                    label = {
                        Text(
                            text = "Name"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // Email field - Read-only (from Supabase)
                OutlinedTextField(
                    value = currentEmail,
                    onValueChange = {},
                    label = {
                        Text(
                            text = "Email (from Supabase)"
                        )
                    },
                    singleLine = true,
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = {
                    onConfirm(nameInput)
                }
            ) {

                Text(
                    text = "Save"
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Cancel"
                )
            }
        }
    )
}


// ===================== ABOUT SCREEN =====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {

    Scaffold(
        containerColor = Color.White,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "About AIStudyBuddy",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 20.dp,
                    vertical = 30.dp
                )
                .verticalScroll(
                    rememberScrollState()
                )
        ) {


            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "AI",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            Text(
                text = "AIStudyBuddy",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Version 1.0.0",
                fontSize = 14.sp,
                color = TextGrey,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )


            Spacer(
                modifier = Modifier.height(32.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LightBlueBg
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        text = "About",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    Text(
                        text = "AIStudyBuddy is an application that helps users manage their time effectively. They can also add their tasks inside the application to receive reminder notifications.",
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = TextDark
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color.LightGray.copy(
                        alpha = 0.3f
                    )
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        text = "Features",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    FeatureItem(
                        text = "📚 Study Planner"
                    )

                    FeatureItem(
                        text = "📝 Assignment Tracker"
                    )

                    FeatureItem(
                        text = "🤖 AI Smart Routine"
                    )

                    FeatureItem(
                        text = "⏰ Focus Timer"
                    )

                    FeatureItem(
                        text = "📊 Progress Dashboard"
                    )

                    FeatureItem(
                        text = "🔔 Reminder Notifications"
                    )
                }
            }
        }
    }
}


// ===================== FEATURE ITEM =====================
@Composable
fun FeatureItem(
    text: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            fontSize = 15.sp,
            color = TextDark
        )
    }
}


// ===================== TOGGLE MENU ITEM =====================
@Composable
fun ToggleMenuItem(
    icon: ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(
                    !isChecked
                )
            }
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(LightBlueBg),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(24.dp)
            )
        }


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Text(
            text = title,
            color = TextDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )


        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}


// ===================== MENU ITEM =====================
@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    trailingText: String? = null,
    textColor: Color = TextDark,
    iconBg: Color = LightBlueBg,
    iconTint: Color = PrimaryBlue,
    onClick: () -> Unit = {}
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Text(
            text = title,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )


        if (trailingText != null) {

            Text(
                text = trailingText,
                color = TextGrey,
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    end = 8.dp
                )
            )
        }


        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey
        )
    }
}