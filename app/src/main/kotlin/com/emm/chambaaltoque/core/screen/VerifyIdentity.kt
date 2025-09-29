package com.emm.chambaaltoque.core.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun VerifyIdentityScreen(
    modifier: Modifier = Modifier,
    onContinueClick: (dni: String, names: String, paternal: String, maternal: String) -> Unit = { _, _, _, _ -> },
) {
    val dniState = remember { mutableStateOf("") }
    val namesState = remember { mutableStateOf("") }
    val paternalState = remember { mutableStateOf("") }
    val maternalState = remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Progress indicator text
            Text(
                text = "Paso 2 de 2",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(8.dp))

            // Title
            Text(
                text = "Verifiquemos tu identidad",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            // Support text
            Text(
                text = "Para la seguridad de toda nuestra comunidad",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            // Manual form fields
            OutlinedTextField(
                value = dniState.value,
                onValueChange = { dniState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Número de DNI") },
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = namesState.value,
                onValueChange = { namesState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombres") },
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = paternalState.value,
                onValueChange = { paternalState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Apellido Paterno") },
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = maternalState.value,
                onValueChange = { maternalState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Apellido Materno") },
                singleLine = true
            )

            Spacer(Modifier.height(32.dp))

            // Primary CTA: Continue
            Button(
                onClick = {
                    onContinueClick(
                        dniState.value,
                        namesState.value,
                        paternalState.value,
                        maternalState.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Continuar",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifyIdentityLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        VerifyIdentityScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifyIdentityDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        VerifyIdentityScreen()
    }
}
