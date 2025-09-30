package com.emm.chambaaltoque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.emm.chambaaltoque.core.presentation.navigation.AppNav
import com.emm.chambaaltoque.core.presentation.ui.theme.ChambaAlToqueTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChambaAlToqueTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNav(modifier = Modifier.padding(it))
                }
            }
        }
    }
}