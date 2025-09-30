package com.emm.chambaaltoque.auth.presentation.login.worker

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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import com.emm.chambaaltoque.core.shared.ui.theme.ChambaAlToqueTheme

@Composable
fun LoginWorkerScreen(
    modifier: Modifier = Modifier,
    onRequestOtp: (phone: String) -> Unit = {},
    onLoginWithPhone: (phone: String, otp: String) -> Unit = { _, _ -> },
    onLoginWithEmail: (email: String, password: String) -> Unit = { _, _ -> },
) {
    val selectedTab = remember { mutableIntStateOf(0) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Inicia sesión",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            TabRow(selectedTabIndex = selectedTab.intValue) {
                Tab(
                    selected = selectedTab.intValue == 0,
                    onClick = { selectedTab.intValue = 0 },
                    text = { Text("Celular") }
                )
                Tab(
                    selected = selectedTab.intValue == 1,
                    onClick = { selectedTab.intValue = 1 },
                    text = { Text("Email") }
                )
            }

            Spacer(Modifier.height(16.dp))

            when (selectedTab.intValue) {
                0 -> PhoneOtpPane(onRequestOtp = onRequestOtp, onLogin = onLoginWithPhone)
                else -> EmailPasswordPane(onLogin = onLoginWithEmail)
            }
        }
    }
}

@Composable
private fun PhoneOtpPane(
    onRequestOtp: (String) -> Unit,
    onLogin: (String, String) -> Unit,
) {
    val phone = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    val requested = remember { mutableStateOf(false) }

    val phoneValid = phone.value.length in 7..12 && phone.value.all { it.isDigit() }
    val otpValid = otp.value.length in 4..8 && otp.value.all { it.isDigit() }
    val canLogin = phoneValid && otpValid

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
                requested.value = true
            },
            enabled = phoneValid,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) { Text(if (requested.value) "Reenviar" else "Enviar código") }
    }

    Spacer(Modifier.height(24.dp))

    Button(
        onClick = { onLogin(phone.value, otp.value) },
        enabled = canLogin,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) { Text("Entrar", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) }
}

@Composable
private fun EmailPasswordPane(
    onLogin: (String, String) -> Unit,
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val emailValid = email.value.contains("@") && email.value.contains(".")
    val passwordValid = password.value.length >= 6
    val canLogin = emailValid && passwordValid

    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text("Email") },
        shape = RoundedCornerShape(12.dp)
    )

    Spacer(Modifier.height(12.dp))

    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = { Text("Contraseña") },
        shape = RoundedCornerShape(12.dp)
    )

    Spacer(Modifier.height(24.dp))

    Button(
        onClick = { onLogin(email.value, password.value) },
        enabled = canLogin,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) { Text("Entrar", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) }
}

@Preview(showBackground = true)
@Composable
private fun LoginWorkerLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        LoginWorkerScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginWorkerDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        LoginWorkerScreen()
    }
}
