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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun ApplicantRegisterScreen(
    modifier: Modifier = Modifier,
    onRequestOtp: (phone: String) -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onOpenTerms: () -> Unit = {},
    onRegister: (phone: String, otp: String, fullName: String, email: String?, accepted: Boolean) -> Unit = { _, _, _, _, _ -> },
) {
    val phone = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val acceptedTerms = remember { mutableStateOf(false) }
    val otpRequested = remember { mutableStateOf(false) }

    val isPhoneValid = phone.value.length in 7..12 && phone.value.all { it.isDigit() }
    val isOtpValid = otp.value.length in 4..8 && otp.value.all { it.isDigit() }
    val isNameValid = fullName.value.trim().isNotEmpty()
    val canRegister = acceptedTerms.value && isPhoneValid && isOtpValid && isNameValid

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
            // Título
            Text(
                text = "Registro Solicitante",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // Celular + OTP
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        otpRequested.value = true
                    },
                    enabled = isPhoneValid,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text(if (otpRequested.value) "Reenviar" else "Enviar código")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Nombre completo
            OutlinedTextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Nombre completo") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Email opcional
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Email (opcional)") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Acceso rápido (Google / Facebook)
            Text(
                text = "o entra con",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onGoogleClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Google",
                        tint = Color(0xFF4285F4)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Google", fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onFacebookClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Facebook",
                        tint = Color(0xFF1877F2)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Facebook", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Aceptación de términos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptedTerms.value,
                    onCheckedChange = { acceptedTerms.value = it },
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
                            append("política de privacidad")
                        }
                    },
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // CTA principal
            Button(
                onClick = {
                    onRegister(
                        phone.value,
                        otp.value,
                        fullName.value,
                        email.value.ifBlank { null },
                        acceptedTerms.value
                    )
                },
                enabled = canRegister,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Registrarme",
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
private fun ApplicantRegisterLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        ApplicantRegisterScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicantRegisterDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        ApplicantRegisterScreen()
    }
}
