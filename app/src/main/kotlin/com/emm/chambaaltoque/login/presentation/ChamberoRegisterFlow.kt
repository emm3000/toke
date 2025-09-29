package com.emm.chambaaltoque.login.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun ChamberoRegisterFlow(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    onBack: () -> Unit = {},
    onPickDniPhoto: () -> Unit = {},
    onPickSelfie: () -> Unit = {},
    onRequestOtp: (phone: String) -> Unit = {},
) {
    val step = remember { mutableIntStateOf(0) } // 0..3

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Top bar minimal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    if (step.value == 0) onBack() else step.value -= 1
                }) { Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás") }

                Text(
                    text = when (step.value) {
                        0 -> "Datos personales"
                        1 -> "Contacto"
                        2 -> "Info. adicional"
                        else -> "Aceptación"
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(Modifier.size(48.dp)) // balancear el AppBar
            }

            Spacer(Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = (step.value + 1) / 4f,
                modifier = Modifier.fillMaxWidth(),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(12.dp))

            when (step.value) {
                0 -> StepPersonal(onNext = { step.value = 1 }, onPickDniPhoto = onPickDniPhoto, onPickSelfie = onPickSelfie)
                1 -> StepContact(onNext = { step.value = 2 }, onRequestOtp = onRequestOtp)
                2 -> StepExtra(onNext = { step.value = 3 })
                3 -> StepAccept(onComplete = onComplete)
            }
        }
    }
}

@Composable
private fun StepPersonal(
    onNext: () -> Unit,
    onPickDniPhoto: () -> Unit,
    onPickSelfie: () -> Unit,
) {
    val fullName = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }
    val birth = remember { mutableStateOf("") } // YYYY-MM-DD placeholder

    val valid = fullName.value.isNotBlank() && dni.value.length == 8 && dni.value.all { it.isDigit() } && birth.value.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Regístrate como Chambero",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Nombre completo") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = dni.value,
            onValueChange = { dni.value = it.filter { ch -> ch.isDigit() }.take(8) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("DNI") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onPickDniPhoto,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Image, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Foto del DNI")
            }
            OutlinedButton(
                onClick = onPickSelfie,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Image, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Selfie")
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = birth.value,
            onValueChange = { birth.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Fecha de nacimiento (YYYY-MM-DD)") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onNext,
            enabled = valid,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Continuar", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun StepContact(
    onNext: () -> Unit,
    onRequestOtp: (String) -> Unit,
) {
    val phone = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val requested = remember { mutableStateOf(false) }

    val phoneValid = phone.value.length in 7..12 && phone.value.all { it.isDigit() }
    val otpValid = otp.value.length in 4..8 && otp.value.all { it.isDigit() }
    val emailValid = email.value.contains("@") && email.value.contains(".")
    val valid = phoneValid && otpValid && emailValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Datos de contacto",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it.filter { ch -> ch.isDigit() }.take(12) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            label = { Text("Número de celular") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = otp.value,
                onValueChange = { otp.value = it.filter { ch -> ch.isDigit() }.take(6) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Código OTP") },
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedButton(
                onClick = {
                    onRequestOtp(phone.value)
                    requested.value = true
                },
                enabled = phoneValid,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(12.dp)
            ) { Text(if (requested.value) "Reenviar" else "Enviar código") }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email (obligatorio)") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onNext,
            enabled = valid,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) { Text("Continuar", style = MaterialTheme.typography.titleMedium) }
    }
}

@Composable
private fun StepExtra(
    onNext: () -> Unit,
) {
    val city = remember { mutableStateOf("") }
    val district = remember { mutableStateOf("") }
    val skills = remember { mutableStateOf("") }

    val valid = city.value.isNotBlank() && district.value.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Información adicional",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = city.value,
            onValueChange = { city.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Ciudad") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = district.value,
            onValueChange = { district.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Distrito") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = skills.value,
            onValueChange = { skills.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            label = { Text("Descripción (opcional)") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onNext,
            enabled = valid,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) { Text("Continuar", style = MaterialTheme.typography.titleMedium) }
    }
}

@Composable
private fun StepAccept(
    onComplete: () -> Unit,
) {
    val accepted = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Aceptación",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = accepted.value,
                onCheckedChange = { accepted.value = it },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = buildAnnotatedString {
                    append("Acepto ")
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
                        append("términos y condiciones")
                    }
                    append(" / ")
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
                        append("política de seguridad")
                    }
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onComplete,
            enabled = accepted.value,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(Icons.Filled.Check, contentDescription = null)
            Spacer(Modifier.size(8.dp))
            Text("Completar Registro", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChamberoRegisterFlowLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        ChamberoRegisterFlow()
    }
}

@Preview(showBackground = true)
@Composable
private fun ChamberoRegisterFlowDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        ChamberoRegisterFlow()
    }
}
