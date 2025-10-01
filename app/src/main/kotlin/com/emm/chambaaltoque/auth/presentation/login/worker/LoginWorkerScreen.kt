package com.emm.chambaaltoque.auth.presentation.login.worker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.auth.presentation.login.LoginAction
import com.emm.chambaaltoque.auth.presentation.login.LoginState
import com.emm.chambaaltoque.core.presentation.ErrorDialog
import com.emm.chambaaltoque.core.presentation.ui.theme.ChambaAlToqueTheme

@Composable
fun LoginWorkerScreen(
    modifier: Modifier = Modifier,
    state: LoginState = LoginState(),
    onAction: (LoginAction) -> Unit = {},
) {

    val passwordVisible = remember { mutableStateOf(false) }

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
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { onAction(LoginAction.EmailChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Contraseña (mínimo 6 caracteres)") },
                trailingIcon = {
                    val icon = if (passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onAction(LoginAction.Login) },
                enabled = state.isValidFields && !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Entrar",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .5f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        if (state.error != null) {
            ErrorDialog(
                onDismissRequest = { onAction(LoginAction.DismissError) },
                error = state.error,
            )
        }
    }
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
