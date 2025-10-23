package com.martinvergara_diegoboggle.pawschedule.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.martinvergara_diegoboggle.pawschedule.ui.common.CustomButton
import com.martinvergara_diegoboggle.pawschedule.ui.common.CustomTextField
import com.martinvergara_diegoboggle.pawschedule.ui.common.TopBar
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.VeterinarioViewModel

@Composable
fun VeterinarioRegisterScreen(
    viewModel: VeterinarioViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onRegistroExitoso: () -> Unit = {}
) {
    val nombre by viewModel.nombre.collectAsState()
    val apellido by viewModel.apellido.collectAsState()
    val email by viewModel.email.collectAsState()
    val telefono by viewModel.telefono.collectAsState()
    val especialidad by viewModel.especialidad.collectAsState()
    val numeroLicencia by viewModel.numeroLicencia.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmarPassword by viewModel.confirmarPassword.collectAsState()

    val nombreError by viewModel.nombreError.collectAsState()
    val apellidoError by viewModel.apellidoError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val telefonoError by viewModel.telefonoError.collectAsState()
    val especialidadError by viewModel.especialidadError.collectAsState()
    val numeroLicenciaError by viewModel.numeroLicenciaError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmarPasswordError by viewModel.confirmarPasswordError.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val registroExitoso by viewModel.registroExitoso.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmarPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(registroExitoso) {
        if (registroExitoso) {
            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
            onRegistroExitoso()
            viewModel.resetearEstados()
        }
    }

    LaunchedEffect(mensajeError) {
        mensajeError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Registro de Veterinario",
            onBackClick = onNavigateBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro Profesional",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = "Nombre",
                leadingIcon = Icons.Default.Person,
                isError = nombreError != null,
                errorMessage = nombreError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = apellido,
                onValueChange = { viewModel.onApellidoChange(it) },
                label = "Apellido",
                leadingIcon = Icons.Default.Person,
                isError = apellidoError != null,
                errorMessage = apellidoError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Email Profesional",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                isError = emailError != null,
                errorMessage = emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = telefono,
                onValueChange = { viewModel.onTelefonoChange(it) },
                label = "Teléfono",
                leadingIcon = Icons.Default.Phone,
                keyboardType = KeyboardType.Phone,
                isError = telefonoError != null,
                errorMessage = telefonoError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = especialidad,
                onValueChange = { viewModel.onEspecialidadChange(it) },
                label = "Especialidad",
                leadingIcon = Icons.Default.Favorite,
                isError = especialidadError != null,
                errorMessage = especialidadError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = numeroLicencia,
                onValueChange = { viewModel.onNumeroLicenciaChange(it) },
                label = "Número de Licencia",
                leadingIcon = Icons.Default.Badge,
                isError = numeroLicenciaError != null,
                errorMessage = numeroLicenciaError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Contraseña",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                isError = passwordError != null,
                errorMessage = passwordError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = confirmarPassword,
                onValueChange = { viewModel.onConfirmarPasswordChange(it) },
                label = "Confirmar Contraseña",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = {
                    IconButton(onClick = { confirmarPasswordVisible = !confirmarPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmarPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (confirmarPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                isError = confirmarPasswordError != null,
                errorMessage = confirmarPasswordError
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "Registrarme como Veterinario",
                onClick = { viewModel.registrarVeterinario() },
                isLoading = isLoading
            )
        }
    }
}