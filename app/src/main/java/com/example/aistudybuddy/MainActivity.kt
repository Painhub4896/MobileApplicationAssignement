package com.example.aistudybuddy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.aistudybuddy.data.SupabaseProvider
import com.example.aistudybuddy.navigation.AppNavigation
import com.example.aistudybuddy.ui.theme.AIStudyBuddyTheme
import io.github.jan.supabase.auth.handleDeeplinks
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class  MainActivity : ComponentActivity() {

    private var incomingDeepLink by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        incomingDeepLink = intent.data
        SupabaseProvider.client.handleDeeplinks(intent)

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