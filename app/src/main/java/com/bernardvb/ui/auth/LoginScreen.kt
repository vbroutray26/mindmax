package com.bernardvb.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state) {
        if (state is AuthState.Success) onAuthSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo / wordmark
        Text("BERNARD", style = BernardType.DisplayLarge, color = MaterialTheme.colorScheme.onBackground)
        Text(
            "/VB",
            style = BernardType.DisplayMedium,
            color = BernardColors.AccentBlue
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "Think better. Decide clearer.",
            style = BernardType.BodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(48.dp))

        // Mode toggle
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { isSignUp = false; viewModel.clearError() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (!isSignUp) BernardColors.AccentBlue else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text("Sign in", style = BernardType.LabelSmall) }
            Text("·", style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(horizontal = 4.dp).align(Alignment.CenterVertically))
            TextButton(
                onClick = { isSignUp = true; viewModel.clearError() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isSignUp) BernardColors.AccentBlue else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text("Create account", style = BernardType.LabelSmall) }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", style = BernardType.BodySmall) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = BernardType.BodySmall) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (isSignUp) viewModel.createAccount(email, password)
                else viewModel.signInWithEmail(email, password)
            }),
            trailingIcon = {
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(if (showPassword) "Hide" else "Show", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            shape = MaterialTheme.shapes.medium
        )

        AnimatedVisibility(visible = state is AuthState.Error) {
            Text(
                (state as? AuthState.Error)?.message ?: "",
                style = BernardType.BodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (isSignUp) viewModel.createAccount(email, password)
                else viewModel.signInWithEmail(email, password)
            },
            enabled = email.isNotBlank() && password.length >= 6 && state !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = BernardColors.AccentBlue)
        ) {
            if (state is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    if (isSignUp) "Create account" else "Sign in",
                    style = BernardType.BodyMedium
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            if (isSignUp) "By creating an account you agree to our Terms of Service."
            else "Forgot password? Contact support@bernardvb.app",
            style = BernardType.LabelSmall,
            color = MaterialTheme.colorScheme.outlineVariant,
            textAlign = TextAlign.Center
        )
    }
}
