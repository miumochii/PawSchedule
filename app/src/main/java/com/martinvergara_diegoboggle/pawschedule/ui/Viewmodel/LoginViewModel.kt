package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.martinvergara_diegoboggle.pawschedule.data.local.database.AppDatabase

// ------------------- INICIO DE LA CORRECCIÓN -------------------
// Corregimos la ruta de UsuarioRepository (le faltaba el '.local')
import com.martinvergara_diegoboggle.pawschedule.data.local.repository.UsuarioRepository
// Esta ruta (sin .local) está BIEN porque tu VeterinarioRepository está en esa carpeta
import com.martinvergara_diegoboggle.pawschedule.data.repository.VeterinarioRepository
// ------------------- FIN DE LA CORRECCIÓN -------------------

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados del Login
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userId: Int, val userType: String, val userName: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioRepository: UsuarioRepository
    private val veterinarioRepository: VeterinarioRepository

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        usuarioRepository = UsuarioRepository(database.usuarioDao())
        veterinarioRepository = VeterinarioRepository(database.veterinarioDao())
    }

    fun onEmailChange(value: String) {
        _email.value = value
        if (value.isNotEmpty()) _emailError.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        if (value.isNotEmpty()) _passwordError.value = null
    }

    fun login(email: String, password: String, userType: String) {
        // Validar campos
        if (email.isEmpty()) {
            _emailError.value = "Ingresa tu email"
            return
        }
        if (password.isEmpty()) {
            _passwordError.value = "Ingresa tu contraseña"
            return
        }

        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading

                when (userType) {
                    "Cliente" -> {
                        val usuario = usuarioRepository.login(email, password)
                        if (usuario != null) {
                            _loginState.value = LoginState.Success(
                                usuario.id,
                                "Cliente",
                                "${usuario.nombre} ${usuario.apellido}"
                            )
                        } else {
                            _loginState.value = LoginState.Error("Email o contraseña incorrectos")
                        }
                    }
                    "Veterinario" -> {
                        val veterinario = veterinarioRepository.login(email, password)
                        if (veterinario != null) {
                            _loginState.value = LoginState.Success(
                                veterinario.id,
                                "Veterinario",
                                "Dr. ${veterinario.nombre} ${veterinario.apellido}"
                            )
                        } else {
                            _loginState.value = LoginState.Error("Email o contraseña incorrectos")
                        }
                    }
                    else -> {
                        _loginState.value = LoginState.Error("Tipo de usuario inválido")
                    }
                }

            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error al iniciar sesión: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }

    fun limpiarErrores() {
        _emailError.value = null
        _passwordError.value = null
    }
}

// Factory para crear el ViewModel
class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}