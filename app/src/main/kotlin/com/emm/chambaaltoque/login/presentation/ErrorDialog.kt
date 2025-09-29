package com.emm.chambaaltoque.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun ErrorDialog(onDismissRequest: () -> Unit, error: String) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier.Companion
                .fillMaxWidth(0.8f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(24.dp)
        ) {
            Text(text = error)
        }
    }
}

@Preview
@Composable
private fun DialogPreview() {
    ChambaAlToqueTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            ErrorDialog(
                onDismissRequest = {},
                error = NullPointerException().stackTraceToString()
            )
        }
    }
}