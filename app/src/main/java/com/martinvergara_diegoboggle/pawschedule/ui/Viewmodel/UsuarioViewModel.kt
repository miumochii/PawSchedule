package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martinvergara_diegoboggle.pawschedule.data.local.database.AppDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.UsuarioEntity
import com.martinvergara_diegoboggle.pawschedule.data.local.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository

    // --- Estados del formulario ---
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _apellido = MutableStateFlow("")
    val apellido: StateFlow<String> = _apellido.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmarPassword = MutableStateFlow("")
    val confirmarPassword: StateFlow<String> = _confirmarPassword.asStateFlow()

    // --- Estados de error del formulario ---
    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _apellidoError = MutableStateFlow<String?>(null)
    val apellidoError: StateFlow<String?> = _apellidoError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _telefonoError = MutableStateFlow<String?>(null)
    val telefonoError: StateFlow<String?> = _telefonoError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmarPasswordError = MutableStateFlow<String?>(null)
    val confirmarPasswordError: StateFlow<String?> = _confirmarPasswordError.asStateFlow()

    // --- Estados de la UI ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).usuarioDao()
        repository = UsuarioRepository(dao)
    }

    // --- Funciones para actualizar el formulario ---
    fun onNombreChange(value: String) {
        _nombre.value = value
        if (value.isNotEmpty()) _nombreError.value = null
    }

    fun onApellidoChange(value: String) {
        _apellido.value = value
        if (value.isNotEmpty()) _apellidoError.value = null
    }

    fun onEmailChange(value: String) {
        _email.value = value
        if (value.isNotEmpty()) _emailError.value = null
    }

    fun onTelefonoChange(value: String) {
        _telefono.value = value
        if (value.isNotEmpty()) _telefonoError.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        if (value.isNotEmpty()) _passwordError.value = null
    }

    fun onConfirmarPasswordChange(value: String) {
        _confirmarPassword.value = value
        if (value.isNotEmpty()) _confirmarPasswordError.value = null
    }

    // --- Lógica de validación ---
    private fun validarFormulario(): Boolean {
        var isValid = true

        if (_nombre.value.isEmpty()) {
            _nombreError.value = "El nombre es obligatorio"
            isValid = false
        } else if (_nombre.value.length < 2) {
            _nombreError.value = "Mínimo 2 caracteres"
            isValid = false
        }

        if (_apellido.value.isEmpty()) {
            _apellidoError.value = "El apellido es obligatorio"
            isValid = false
        }

        if (_email.value.isEmpty()) {
            _emailError.value = "El email es obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _emailError.value = "Email inválido"
            isValid = false
        }

        if (_telefono.value.isEmpty()) {
            _telefonoError.value = "El teléfono es obligatorio"
            isValid = false
        } else if (_telefono.value.length < 9) {
            _telefonoError.value = "Mínimo 9 dígitos"
            isValid = false
        }

        if (_password.value.isEmpty()) {
            _passwordError.value = "La contraseña es obligatoria"
            isValid = false
        } else if (_password.value.length < 6) {
            _passwordError.value = "Mínimo 6 caracteres"
            isValid = false
        }

        if (_confirmarPassword.value.isEmpty()) {
            _confirmarPasswordError.value = "Confirma tu contraseña"
            isValid = false
        } else if (_password.value != _confirmarPassword.value) {
            _confirmarPasswordError.value = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    // --- Funciones de Lógica de Negocio ---
    fun registrarUsuario() {
        if (!validarFormulario()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val usuarioExistente = repository.obtenerPorEmail(_email.value)
                if (usuarioExistente != null) {
                    _emailError.value = "Este email ya está registrado"
                    _isLoading.value = false
                    return@launch
                }

                val usuario = UsuarioEntity(
                    nombre = _nombre.value,
                    apellido = _apellido.value,
                    email = _email.value,
                    password = _password.value, // NOTA: Deberías encriptar esto
                    telefono = _telefono.value,
                    rol = "CLIENTE"
                )

                repository.registrar(usuario) // Tu repositorio usa 'registrar'

                _registroExitoso.value = true
                _isLoading.value = false

                limpiarFormulario()

            } catch (e: Exception) {
                _mensajeError.value = "Error al registrar: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    // --- Funciones de limpieza ---
    fun limpiarFormulario() {
        _nombre.value = ""
        _apellido.value = ""
        _email.value = ""
        _telefono.value = ""
        _password.value = ""
        _confirmarPassword.value = ""
        limpiarErrores()
    }

    fun limpiarErrores() {
        _nombreError.value = null
        _apellidoError.value = null
        _emailError.value = null
        _telefonoError.value = null
        _passwordError.value = null
        _confirmarPasswordError.value = null
        _mensajeError.value = null
    }

    fun resetearEstados() {
        _registroExitoso.value = false
    }
}