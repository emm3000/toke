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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.core.ui.theme.ChambaAlToqueTheme

@Composable
fun LoginApplicantScreen(
    modifier: Modifier = Modifier,
    onRequestOtp: (phone: String) -> Unit = {},
    onLogin: (phone: String, otp: String) -> Unit = { _, _ -> },
) {
    val phone = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    val otpRequested = remember { mutableStateOf(false) }

    val isPhoneValid = phone.value.length in 7..12 && phone.value.all { it.isDigit() }
    val isOtpValid = otp.value.length in 4..8 && otp.value.all { it.isDigit() }
    val canLogin = isPhoneValid && isOtpValid

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
            // Title
            Text(
                text = "Inicia sesión",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // Phone
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

            // OTP + Request button
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

            Spacer(Modifier.height(24.dp))

            // CTA
            Button(
                onClick = { onLogin(phone.value, otp.value) },
                enabled = canLogin,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Entrar",
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
private fun LoginApplicantLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        LoginApplicantScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginApplicantDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        LoginApplicantScreen()
    }
}
