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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StackedBarChart
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.aistudybuddy.components.BottomNavigationBar

val PrimaryBlue = Color(0xFF4C6FFF)
val LightBlueBg = Color(0xFFE8F0FE)
val TextDark = Color(0xFF2C3E50)
val TextGrey = Color(0xFF888888)
val RedColor = Color(0xFFFF4D4D)


@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit = {},
    onAssignmentClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){

    //Get context for sharedPreference
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("UserProfile",Context.MODE_PRIVATE)

    //User input,
    var userName by remember { mutableStateOf(sharedPref.getString("name","Alex Lee") ?: "Alex Lee")}
    var userEmail by remember { mutableStateOf(sharedPref.getString("email","alex.lee@student.com") ?:"alex.lee@student.com" )}

    var showEditDialog by remember { mutableStateOf(false) }


    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "Profile",
                onHomeClick = onHomeClick,
                onAssignmentsClick = onAssignmentClick,
                onPlannerClick = onPlannerClick,
                onProgressClick = onProgressClick,
                onProfileClick = onProfileClick
            )
        }
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(PrimaryBlue, Color(0xFF6A8DFF))
                        ),
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                    )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Avatar and Edit Icon
                    Box{
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(100.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center)
                            {
                                Text(
                                    text = userName.take(2).uppercase(),
                                    fontSize = 32.sp,
                                    color = PrimaryBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        //Pencil Icon
                        Box(modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue)
                            .border(2.dp, Color.White,CircleShape)
                            .clickable{showEditDialog = true},
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = userEmail,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }

            //Bottom List
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp,
                        vertical = 30.dp)
            ) {
                MenuItem(icon = Icons.Outlined.Person,title = "Edit Profile", onClick = {showEditDialog = true})
                Spacer(modifier = Modifier.height(24.dp))

                MenuItem(icon = Icons.Outlined.Tune,title = "Study Preferences")
                Spacer(modifier = Modifier.height(24.dp))

                MenuItem(icon = Icons.Outlined.AutoAwesome,title = "AI Recommendation")
                Spacer(modifier = Modifier.height(24.dp))

                MenuItem(icon = Icons.Outlined.Notifications,title = "Notification Settings")
                Spacer(modifier = Modifier.height(24.dp))

                MenuItem(icon = Icons.Outlined.Language,title = "Language", trailingText = "English")
                Spacer(modifier = Modifier.height(24.dp))

                MenuItem(icon = Icons.Outlined.Info,title = "About AIStudyBuddy")
                Spacer(modifier = Modifier.height(24.dp))


                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))

                //Log Out
                MenuItem(
                    icon = Icons.Outlined.Logout,
                    title = "Log Out",
                    textColor = RedColor,
                    iconBg = RedColor.copy(alpha = 0.1f),
                    iconTint = RedColor
                )
            }
        }
    }

    if (showEditDialog){
        EditProfileDialog(
            currentName = userName,
            currentEmail = userEmail,
            onDismiss = {showEditDialog = false},
            onConfirm = {newName, newEmail ->
                userName = newName
                userEmail = newEmail
                showEditDialog = false


                sharedPref.edit()
                    .putString("name",newName)
                    .putString("email",newEmail)
                    .apply()
            }
        )
    }
}

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
            .clickable {onClick()}
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
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

        Spacer(modifier = Modifier.width(16.dp))


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
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey
        )
    }
}

@Composable
fun EditProfileDialog(
    currentName: String,
    currentEmail: String,
    onDismiss: () -> Unit,
    onConfirm: (String,String) -> Unit
){
    var nameInput by remember { mutableStateOf(currentName) }
    var emailInput by remember { mutableStateOf(currentEmail) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Edit Profile")},
        text = {
            Column {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = {nameInput = it},
                    label = {Text("Name")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = emailInput,
                    onValueChange = {emailInput = it },
                    label = {Text("Email")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {onConfirm(nameInput,emailInput)}) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}