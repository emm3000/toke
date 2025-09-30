package com.emm.chambaaltoque.postjob.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.chambaaltoque.auth.presentation.ErrorDialog
import com.emm.chambaaltoque.core.presentation.ui.theme.ChambaAlToqueTheme

@Composable
fun PostJobScreen(
    modifier: Modifier = Modifier,
    state: PostJobState = PostJobState(),
    onAction: (PostJobAction) -> Unit = {},
    onCancel: () -> Unit = {},
) {

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
                OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier
                        .height(0.dp)
                        .padding(horizontal = 4.dp))
                    Text("Agregar foto")
                }
            }

            Spacer(Modifier.height(8.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.title,
                onValueChange = { onAction(PostJobAction.TitleChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Título del trabajo") },
                leadingIcon = { Icon(Icons.Filled.Title, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.budget.toString(),
                onValueChange = {
                    if (it.isNotBlank() && it.last().isDigit()) onAction(PostJobAction.BudgetChanged(it.toDouble()))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text("Monto") },
                leadingIcon = { Icon(Icons.Filled.Money, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { onAction(PostJobAction.DescriptionChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 4,
                label = { Text("Descripción del trabajo") },
                leadingIcon = { Icon(Icons.Filled.Description, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

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
                    onClick = { onAction(PostJobAction.Publish) },
                    enabled = state.isValid && !state.isLoading,
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
                onDismissRequest = { onAction(PostJobAction.DismissError) },
                error = state.error,
            )
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
