package com.emm.chambaaltoque.auth.presentation.register.aplicant

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.emm.chambaaltoque.core.presentation.ErrorDialog
import com.emm.chambaaltoque.core.presentation.ui.theme.ChambaAlToqueTheme

@Composable
fun ApplicantRegisterScreen(
    modifier: Modifier = Modifier,
    state: ApplicantRegisterState = ApplicantRegisterState(),
    onAction: (ApplicantRegisterAction) -> Unit = {},
) {

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
            Text(
                text = "Registro Solicitante",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.phone,
                onValueChange = { onAction(ApplicantRegisterAction.PhoneChange(it)) },
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
                    value = "otp.value",
                    onValueChange = { },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Código OTP") },
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedButton(
                    onClick = {
                    },
                    enabled = false,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text("Enviar código")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Nombre completo
            OutlinedTextField(
                value = state.fullName,
                onValueChange = { onAction(ApplicantRegisterAction.FullNameChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Nombre completo") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { onAction(ApplicantRegisterAction.EmailChange(it)) },
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
                    onClick = {},
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
                    onClick = {},
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.acceptedTerms,
                    onCheckedChange = { onAction(ApplicantRegisterAction.AcceptedTermsChange(it)) },
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

            Button(
                onClick = {
                    onAction(ApplicantRegisterAction.Register)
                },
                enabled = state.isFieldValid && !state.isLoading,
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
                onDismissRequest = { onAction(ApplicantRegisterAction.DismissDialog) },
                error = state.error,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicantRegisterLightPreview() {
    ChambaAlToqueTheme(darkTheme = false, dynamicColor = false) {
        ApplicantRegisterScreen(
            state = ApplicantRegisterState(
                error = "random name"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicantRegisterDarkPreview() {
    ChambaAlToqueTheme(darkTheme = true, dynamicColor = false) {
        ApplicantRegisterScreen(
            state = ApplicantRegisterState(
                error = "random name"
            )
        )
    }
}
