package com.emm.chambaaltoque.postjob.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun PostJobScreen(
    modifier: Modifier = Modifier,
    onAddPhoto: () -> Unit = {},
    onPublish: (title: String, typeIndex: Int, pointA: String, pointB: String, amount: String) -> Unit = { _, _, _, _, _ -> },
    onCancel: () -> Unit = {},
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val isValid = title.value.isNotBlank() && description.value.isNotBlank()

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
            // Decorative header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Publicar una Chamba",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Describe lo que necesitas de forma clara y breve.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                OutlinedButton(onClick = onAddPhoto, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.height(0.dp).padding(horizontal = 4.dp))
                    Text("Agregar foto")
                }
            }

            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            // Título del trabajo
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Título del trabajo") },
                leadingIcon = { Icon(Icons.Filled.Title, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Descripción del trabajo
            OutlinedTextField(
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 4,
                label = { Text("Descripción del trabajo") },
                leadingIcon = { Icon(Icons.Filled.Description, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Foto opcional CTA
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onAddPhoto,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.height(0.dp).padding(horizontal = 4.dp))
                    Text("Agregar foto (opcional)")
                }
            }

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
                    onClick = { onPublish(title.value, /*typeIndex*/ 0, /*pointA*/ "", /*pointB*/ "", /*amount*/ "") },
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
