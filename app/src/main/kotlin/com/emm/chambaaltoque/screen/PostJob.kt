package com.emm.chambaaltoque.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.ui.theme.ChambaAlToqueTheme

@Composable
fun PostJobScreen(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit = {},
    onPublishClick: () -> Unit = {},
) {
    val description = remember { mutableStateOf("") }
    val pickup = remember { mutableStateOf("") }
    val dropoff = remember { mutableStateOf("") }
    val offer = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Button(
                    onClick = onPublishClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Publicar Chamba Ahora",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Title
            Text(
                text = "Publicar una Chamba",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(Modifier.height(16.dp))

            // Big description field
            OutlinedTextField(
                value = description.value,
                onValueChange = { description.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("¿Qué necesitas hoy? (Ej: 'Enviar un sobre a Miraflores')") },
                minLines = 3
            )

            Spacer(Modifier.height(20.dp))

            // Route section title
            Text(
                text = "Ruta",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = pickup.value,
                        onValueChange = { pickup.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        label = { Text("Punto de Recojo (A)") }
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = dropoff.value,
                        onValueChange = { dropoff.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        label = { Text("Punto de Entrega (B)") }
                    )
                }

                // Map preview placeholder (simulate map with a Box)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp)
                        .fillMaxWidth(),
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Vista previa de mapa",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Optional add photo button
            OutlinedButton(
                onClick = onAddPhotoClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddAPhoto,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text("Añadir una foto", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(16.dp))

            // Money input with S/ prefix
            OutlinedTextField(
                value = offer.value,
                onValueChange = { offer.value = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Text(
                        text = "S/",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                placeholder = { Text("¿Cuánto ofreces por la chamba?") }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostJobScreenLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        PostJobScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PostJobScreenDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        PostJobScreen()
    }
}
