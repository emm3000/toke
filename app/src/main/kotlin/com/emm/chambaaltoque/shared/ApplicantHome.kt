package com.emm.chambaaltoque.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

private data class QuickJob(val id: String, val title: String, val status: String)

@Composable
fun ApplicantHomeScreen(
    modifier: Modifier = Modifier,
    onPublishClick: () -> Unit = {},
    onOpenJob: (String) -> Unit = {},
    onOpenRatings: () -> Unit = {},
) {
    val pending = listOf(
        QuickJob("1", "Enviar documentos a Lince", "Pendiente"),
    )
    val inProgress = listOf(
        QuickJob("2", "Mudanza pequeña", "En curso"),
    )
    val done = listOf(
        QuickJob("3", "Compra en supermercado", "Completada"),
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Inicio",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Button(
                    onClick = onPublishClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Publicar una Chamba", style = MaterialTheme.typography.titleSmall)
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            // Historial rápido
            Text(
                text = "Historial rápido",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (pending.isNotEmpty()) {
                    item { SectionTitle("Pendientes") }
                    items(pending) { job -> QuickJobCard(job) { onOpenJob(job.id) } }
                }
                if (inProgress.isNotEmpty()) {
                    item { SectionTitle("En curso") }
                    items(inProgress) { job -> QuickJobCard(job) { onOpenJob(job.id) } }
                }
                if (done.isNotEmpty()) {
                    item { SectionTitle("Completadas") }
                    items(done) { job -> QuickJobCard(job) { onOpenJob(job.id) } }
                }

                item { Spacer(Modifier.height(8.dp)) }
                item {
                    Button(
                        onClick = onOpenRatings,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) { Text("Ver calificaciones", style = MaterialTheme.typography.titleSmall) }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun QuickJobCard(job: QuickJob, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(job.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(job.status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicantHomeLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        ApplicantHomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicantHomeDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        ApplicantHomeScreen()
    }
}
