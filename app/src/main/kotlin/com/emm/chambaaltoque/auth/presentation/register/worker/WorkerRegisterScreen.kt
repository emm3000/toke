package com.emm.chambaaltoque.auth.presentation.register.worker

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emm.chambaaltoque.core.presentation.ErrorDialog
import com.emm.chambaaltoque.core.presentation.ui.theme.ChambaAlToqueTheme

@Composable
fun WorkerRegisterFlow(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onPickDniPhoto: () -> Unit = {},
    onPickSelfie: () -> Unit = {},
    state: WorkerRegisterState = WorkerRegisterState(),
    onAction: (WorkerRegisterAction) -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    onBack()
                }) { Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás") }

                Text(
                    text = "Registro de chambero",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(Modifier.size(48.dp))
            }

            Spacer(Modifier.height(4.dp))

            SinglePageForm(
                state = state,
                onAction = onAction,
                onPickDniPhoto = onPickDniPhoto,
                onPickSelfie = onPickSelfie,
            )
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
                onDismissRequest = { onAction(WorkerRegisterAction.DismissDialog) },
                error = state.error,
            )
        }
    }
}

@Composable
private fun SinglePageForm(
    state: WorkerRegisterState,
    onAction: (WorkerRegisterAction) -> Unit,
    onPickDniPhoto: () -> Unit,
    onPickSelfie: () -> Unit,
) {
    val passwordVisible = remember { mutableStateOf(false) }

    val dniValid = state.dni.length == 8 && state.dni.all { it.isDigit() }
    val phoneValid = state.phone.length in 7..12 && state.phone.all { it.isDigit() }
    val emailValid = state.email.contains("@") && state.email.contains(".")
    val birthValid = state.birth.isNotBlank()
    val addressValid = state.city.isNotBlank() && state.district.isNotBlank()

    val isValid = state.fullName.isNotBlank() &&
            dniValid &&
            birthValid &&
            phoneValid &&
            emailValid &&
            state.password.length >= 6 &&
            addressValid &&
            state.isTermsAccepted

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

        // Datos personales
        OutlinedTextField(
            value = state.fullName,
            onValueChange = { onAction(WorkerRegisterAction.SetFullName(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Nombre completo") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.dni,
            onValueChange = { onAction(WorkerRegisterAction.SetDni(it.filter { ch -> ch.isDigit() }.take(8))) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.dni.isNotEmpty() && !dniValid,
            label = { Text("DNI") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // DNI placeholder
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable { onPickDniPhoto() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                color = MaterialTheme.colorScheme.surface
            ) {
                if (state.dniPhoto != Uri.EMPTY) {
                    AsyncImage(
                        model = state.dniPhoto,
                        contentDescription = "DNI",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null)
                        Spacer(Modifier.height(6.dp))
                        Text("Foto DNI", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Selfie placeholder
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clickable { onPickSelfie() },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                color = MaterialTheme.colorScheme.surface
            ) {
                if (state.selfie != Uri.EMPTY) {
                    AsyncImage(
                        model = state.selfie,
                        contentDescription = "Selfie",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null)
                        Spacer(Modifier.height(6.dp))
                        Text("Selfie", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.birth,
            onValueChange = { onAction(WorkerRegisterAction.SetBirth(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Fecha de nacimiento (YYYY-MM-DD)") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))

        // Contacto
        OutlinedTextField(
            value = state.phone,
            onValueChange = { onAction(WorkerRegisterAction.SetPhone(it.filter { ch -> ch.isDigit() }.take(12))) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = state.phone.isNotEmpty() && !phoneValid,
            label = { Text("Número de celular") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(WorkerRegisterAction.SetEmail(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.email.isNotEmpty() && !emailValid,
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(WorkerRegisterAction.SetPassword(it)) },
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

        Spacer(Modifier.height(20.dp))

        // Ubicación y habilidades
        OutlinedTextField(
            value = state.city,
            onValueChange = { onAction(WorkerRegisterAction.SetCity(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Ciudad") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = state.district,
            onValueChange = { onAction(WorkerRegisterAction.SetDistrict(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Distrito") },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = state.skills,
            onValueChange = { onAction(WorkerRegisterAction.SetSkills(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            label = { Text("Descripción / habilidades (opcional)") },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isTermsAccepted,
                onCheckedChange = { checked -> onAction(WorkerRegisterAction.SetTermsAccepted(checked)) },
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

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onAction(WorkerRegisterAction.Submit) },
            enabled = isValid && !state.isLoading,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = if (state.isLoading) "Creando cuenta..." else "Crear cuenta",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SinglePageFormPreview() {
    ChambaAlToqueTheme {
        WorkerRegisterFlow()
    }
}