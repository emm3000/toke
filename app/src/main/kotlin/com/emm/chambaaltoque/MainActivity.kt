package com.emm.chambaaltoque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.emm.chambaaltoque.ui.theme.ChambaAlToqueTheme
import com.emm.chambaaltoque.screen.WelcomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChambaAlToqueTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    WelcomeScreen(
                        modifier = Modifier.padding(it),
                        onNeedJobClick = { /* TODO: navigate to requester flow */ },
                        onWantWorkClick = { /* TODO: navigate to worker flow */ },
                        onSignInClick = { /* TODO: navigate to sign-in */ }
                    )
                }
            }
        }
    }
}