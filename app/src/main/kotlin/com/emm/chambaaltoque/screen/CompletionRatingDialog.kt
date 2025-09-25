package com.emm.chambaaltoque.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emm.chambaaltoque.ui.theme.ChambaAlToqueTheme

@Composable
fun CompletionRatingDialog(
    isOpen: Boolean,
    chamberoName: String,
    taskSummary: String,
    onSubmit: (rating: Int, comment: String) -> Unit,
    onDismiss: () -> Unit,
) {
    if (!isOpen) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "¡Chamba Completada!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(Modifier.height(8.dp))

                // Summary
                Text(
                    text = "$chamberoName te ayudó con: $taskSummary",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                // Question
                Text(
                    text = "¿Cómo calificarías a $chamberoName?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(Modifier.height(12.dp))

                // Interactive stars
                val ratingState = remember { mutableIntStateOf(5) }
                StarRating(rating = ratingState.intValue, onRate = { ratingState.intValue = it })

                Spacer(Modifier.height(16.dp))

                // Optional comment
                val commentState = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = commentState.value,
                    onValueChange = { commentState.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Deja un comentario (opcional)") },
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { onSubmit(ratingState.intValue, commentState.value) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Enviar Calificación")
                }
            }
        }
    }
}

@Composable
private fun StarRating(rating: Int, onRate: (Int) -> Unit, max: Int = 5) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(max) { index: Int ->
            val pos: Int = index + 1
            val filled: Boolean = pos <= rating
            Icon(
                imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Calificar con $pos estrellas",
                tint = if (filled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRate(pos) }
                    .padding(2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CompletionRatingDialogPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        CompletionRatingDialog(
            isOpen = true,
            chamberoName = "Juan",
            taskSummary = "Enviar un sobre",
            onSubmit = { _, _ -> },
            onDismiss = {}
        )
    }
}
