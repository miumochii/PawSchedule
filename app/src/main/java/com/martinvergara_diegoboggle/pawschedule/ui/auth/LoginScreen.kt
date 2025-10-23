package com.martinvergara_diegoboggle.pawschedule.ui.auth

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.LoginState
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.LoginViewModel
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.LoginViewModelFactory

// Añadimos la anotación para la API experimental
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalContext.current.applicationContext as Application)
    ),
    // --- CORRECCIÓN 1 ---
    // Añadimos 'userName' para pasarlo a la navegación
    onLoginSuccess: (userId: Int, userType: String, userName: String) -> Unit = { _, _, _ -> },
    onNavigateToRegister: () -> Unit = {}
) {
    val loginState by viewModel.loginState.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    var selectedUserType by remember { mutableStateOf("Cliente") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Manejar estados de login
    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is LoginState.Success -> {
                // --- CORRECCIÓN 2 ---
                // Pasamos los 3 parámetros (incluyendo state.userName)
                Toast.makeText(context, "¡Bienvenido ${state.userName}!", Toast.LENGTH_SHORT).show()
                onLoginSuccess(state.userId, state.userType, state.userName)
                viewModel.resetState()
            }
            is LoginState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "PawSchedule",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión en tu cuenta",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Selector de tipo de usuario
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = selectedUserType == "Cliente",
                onClick = { selectedUserType = "Cliente" },
                label = { Text("Cliente") }
            )
            FilterChip(
                selected = selectedUserType == "Veterinario",
                onClick = { selectedUserType = "Veterinario" },
                label = { Text("Veterinario") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = emailError != null,
            supportingText = emailError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Password
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = passwordError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Login
        Button(
            onClick = { viewModel.login(email, password, selectedUserType) },
            enabled = loginState !is LoginState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}