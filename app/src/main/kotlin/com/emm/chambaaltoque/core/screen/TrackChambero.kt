package com.emm.chambaaltoque.core.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun TrackChamberoScreen(
    modifier: Modifier = Modifier,
    chamberoName: String = "Juan Pérez",
    chamberoRating: Float = 4.7f,
    onCallClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Top map area (80% height)
        MapPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .heightFractionOfParent(0.8f)
        )

        // Bottom sheet panel
        BottomInfoPanel(
            name = chamberoName,
            rating = chamberoRating,
            onCallClick = onCallClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        // Floating chat action
        FloatingActionButton(
            onClick = onChatClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 120.dp) // keep above the bottom sheet
        ) {
            Icon(imageVector = Icons.Filled.Chat, contentDescription = "Chat")
        }
    }
}

@Composable
private fun MapPlaceholder(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        // Simple illustrative map with route path and a moving icon
        Box(Modifier.fillMaxSize()) {
            // Background fake blocks
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
            )

            val ff = MaterialTheme.colorScheme

            // Route path drawn on canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    moveTo(width * 0.1f, height * 0.2f) // Start (A)
                    quadraticTo(
                        width * 0.5f, height * 0.1f,
                        width * 0.7f, height * 0.4f
                    )
                    quadraticTo(
                        width * 0.9f, height * 0.7f,
                        width * 0.8f, height * 0.9f // End (B)
                    )
                }

                drawPath(
                    path = path,
                    color = ff.primary.copy(alpha = 0.7f),
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Marker A
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            // Marker B
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
            )

            // Chambero current position icon
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PedalBike,
                    contentDescription = "Chambero en camino",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun BottomInfoPanel(
    name: String,
    rating: Float,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar placeholder
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "$name está en camino",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    StarsRow(rating)
                }
                Button(
                    onClick = onCallClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Phone, contentDescription = "Llamar")
                    Spacer(Modifier.size(8.dp))
                    Text("Llamar")
                }
            }
        }
    }
}

@Composable
private fun StarsRow(rating: Float, max: Int = 5) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val full = rating.toInt().coerceIn(0, max)
        val hasHalf = (rating - full) >= 0.5f
        repeat(full) { Star(filled = true) }
        if (hasHalf && full < max) Star(filled = false, highlight = true)
        repeat(max - full - if (hasHalf) 1 else 0) { Star(filled = false) }
        Spacer(Modifier.size(6.dp))
        Text(text = String.format("%.1f", rating), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
    }
}

@Composable
private fun Star(filled: Boolean, highlight: Boolean = false) {
    val color = if (filled || highlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Box(
        modifier = Modifier
            .size(14.dp)
            .clip(CircleShape)
            .background(color)
    ) {}
}

private fun Modifier.heightFractionOfParent(fraction: Float): Modifier = this.then(
    object : Modifier.Element {}
).height((fraction * 700).dp) // fallback for preview; actual layout is managed by Box alignment

@Preview(showBackground = true)
@Composable
private fun TrackChamberoLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        TrackChamberoScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackChamberoDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        TrackChamberoScreen()
    }
}
