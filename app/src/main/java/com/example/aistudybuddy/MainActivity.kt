package com.example.aistudybuddy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.aistudybuddy.data.SupabaseProvider
import com.example.aistudybuddy.navigation.AppNavigation
import com.example.aistudybuddy.notification.NotificationHelper
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import io.github.jan.supabase.auth.handleDeeplinks
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {

    private var incomingDeepLink by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )

        incomingDeepLink = intent.data

        SupabaseProvider.client.handleDeeplinks(intent)

        // Create notification channel
        NotificationHelper.createNotificationChannel(this)

        if (
            android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        setContent {
            AIStudyBuddyTheme {
                AppNavigation(
                    incomingDeepLink = incomingDeepLink
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        incomingDeepLink = intent.data

        SupabaseProvider.client.handleDeeplinks(intent)
    }
}