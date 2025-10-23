package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martinvergara_diegoboggle.pawschedule.data.local.database.AppDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.VeterinarioEntity
import com.martinvergara_diegoboggle.pawschedule.data.repository.VeterinarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VeterinarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VeterinarioRepository

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _apellido = MutableStateFlow("")
    val apellido: StateFlow<String> = _apellido.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _especialidad = MutableStateFlow("")
    val especialidad: StateFlow<String> = _especialidad.asStateFlow()

    private val _numeroLicencia = MutableStateFlow("")
    val numeroLicencia: StateFlow<String> = _numeroLicencia.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmarPassword = MutableStateFlow("")
    val confirmarPassword: StateFlow<String> = _confirmarPassword.asStateFlow()

    private val _horaInicio = MutableStateFlow("09:00")
    val horaInicio: StateFlow<String> = _horaInicio.asStateFlow()

    private val _horaFin = MutableStateFlow("18:00")
    val horaFin: StateFlow<String> = _horaFin.asStateFlow()

    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _apellidoError = MutableStateFlow<String?>(null)
    val apellidoError: StateFlow<String?> = _apellidoError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _telefonoError = MutableStateFlow<String?>(null)
    val telefonoError: StateFlow<String?> = _telefonoError.asStateFlow()

    private val _especialidadError = MutableStateFlow<String?>(null)
    val especialidadError: StateFlow<String?> = _especialidadError.asStateFlow()

    private val _numeroLicenciaError = MutableStateFlow<String?>(null)
    val numeroLicenciaError: StateFlow<String?> = _numeroLicenciaError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmarPasswordError = MutableStateFlow<String?>(null)
    val confirmarPasswordError: StateFlow<String?> = _confirmarPasswordError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    private val _veterinarios = MutableStateFlow<List<VeterinarioEntity>>(emptyList())
    val veterinarios: StateFlow<List<VeterinarioEntity>> = _veterinarios.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).veterinarioDao()
        repository = VeterinarioRepository(dao)
        cargarVeterinarios()
    }

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

    fun onEspecialidadChange(value: String) {
        _especialidad.value = value
        if (value.isNotEmpty()) _especialidadError.value = null
    }

    fun onNumeroLicenciaChange(value: String) {
        _numeroLicencia.value = value
        if (value.isNotEmpty()) _numeroLicenciaError.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        if (value.isNotEmpty()) _passwordError.value = null
    }

    fun onConfirmarPasswordChange(value: String) {
        _confirmarPassword.value = value
        if (value.isNotEmpty()) _confirmarPasswordError.value = null
    }

    fun onHoraInicioChange(value: String) {
        _horaInicio.value = value
    }

    fun onHoraFinChange(value: String) {
        _horaFin.value = value
    }

    private fun validarFormulario(): Boolean {
        var isValid = true

        if (_nombre.value.isEmpty()) {
            _nombreError.value = "El nombre es obligatorio"
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
        }

        if (_especialidad.value.isEmpty()) {
            _especialidadError.value = "La especialidad es obligatoria"
            isValid = false
        }

        if (_numeroLicencia.value.isEmpty()) {
            _numeroLicenciaError.value = "El número de licencia es obligatorio"
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

    fun registrarVeterinario() {
        if (!validarFormulario()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val veterinarioExistente = repository.getVeterinarioByEmail(_email.value)
                if (veterinarioExistente != null) {
                    _emailError.value = "Este email ya está registrado"
                    _isLoading.value = false
                    return@launch
                }

                val veterinario = VeterinarioEntity(
                    nombre = _nombre.value,
                    apellido = _apellido.value,
                    email = _email.value,
                    telefono = _telefono.value,
                    especialidad = _especialidad.value,
                    numeroLicencia = _numeroLicencia.value,
                    password = _password.value,
                    horaInicio = _horaInicio.value,
                    horaFin = _horaFin.value
                )

                repository.insert(veterinario)

                _registroExitoso.value = true
                _isLoading.value = false

                limpiarFormulario()

            } catch (e: Exception) {
                _mensajeError.value = "Error al registrar: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun cargarVeterinarios() {
        viewModelScope.launch {
            repository.allVeterinarios.collect { lista ->
                _veterinarios.value = lista
            }
        }
    }

    fun limpiarFormulario() {
        _nombre.value = ""
        _apellido.value = ""
        _email.value = ""
        _telefono.value = ""
        _especialidad.value = ""
        _numeroLicencia.value = ""
        _password.value = ""
        _confirmarPassword.value = ""
        _horaInicio.value = "09:00"
        _horaFin.value = "18:00"
        limpiarErrores()
    }

    fun limpiarErrores() {
        _nombreError.value = null
        _apellidoError.value = null
        _emailError.value = null
        _telefonoError.value = null
        _especialidadError.value = null
        _numeroLicenciaError.value = null
        _passwordError.value = null
        _confirmarPasswordError.value = null
        _mensajeError.value = null
    }

    fun resetearEstados() {
        _registroExitoso.value = false
    }
}
