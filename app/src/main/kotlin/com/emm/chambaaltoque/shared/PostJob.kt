package com.emm.chambaaltoque.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun PostJobScreen(
    modifier: Modifier = Modifier,
    onPickPointA: () -> Unit = {},
    onPickPointB: () -> Unit = {},
    onAddPhoto: () -> Unit = {},
    onPublish: (title: String, typeIndex: Int, pointA: String, pointB: String, amount: String) -> Unit = { _, _, _, _, _ -> },
    onCancel: () -> Unit = {},
) {
    val types = listOf("Envío", "Tarea en casa", "Trámite", "Otro")
    val selectedType = remember { mutableIntStateOf(0) }

    val title = remember { mutableStateOf("") }
    val pointA = remember { mutableStateOf("") }
    val pointB = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }

    val isValid = title.value.isNotBlank() && amount.value.isNotBlank()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Publicar una Chamba",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            // Tipo de chamba (para no limitar a envíos)
            Text(
                text = "Tipo",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(6.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                types.forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = selectedType.intValue == index,
                        onClick = { selectedType.intValue = index },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = types.size)
                    ) {
                        Text(label)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ¿Qué necesitas?
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("¿Qué necesitas?") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Punto A (recojo)
            Text(
                text = "Punto A (recojo)",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(6.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (pointA.value.isBlank()) "Selecciona en el mapa" else pointA.value,
                            color = if (pointA.value.isBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    OutlinedButton(onClick = onPickPointA, shape = RoundedCornerShape(12.dp)) {
                        Text("Elegir en mapa")
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Punto B (entrega)
            Text(
                text = "Punto B (entrega)",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(6.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (pointB.value.isBlank()) "Selecciona en el mapa" else pointB.value,
                            color = if (pointB.value.isBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    OutlinedButton(onClick = onPickPointB, shape = RoundedCornerShape(12.dp)) {
                        Text("Elegir en mapa")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Foto opcional
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Foto (opcional)", style = MaterialTheme.typography.titleSmall)
                OutlinedButton(
                    onClick = onAddPhoto,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) { Text("Agregar foto") }
            }

            Spacer(Modifier.height(12.dp))

            // Monto sugerido
            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it.filter { ch -> ch.isDigit() || ch == '.' }.take(8) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Monto sugerido") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(20.dp))

            // Botones Publicar / Cancelar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) { Text("Cancelar") }

                Button(
                    onClick = { onPublish(title.value, selectedType.intValue, pointA.value, pointB.value, amount.value) },
                    enabled = isValid,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Publicar",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostJobLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        PostJobScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PostJobDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        PostJobScreen()
    }
}
