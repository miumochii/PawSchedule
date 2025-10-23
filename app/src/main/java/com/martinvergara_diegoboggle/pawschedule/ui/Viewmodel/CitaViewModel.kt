package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martinvergara_diegoboggle.pawschedule.data.local.database.AppDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.CitaEntity

// ------------------- INICIO DE LA CORRECCIÓN -------------------
// Esta es la ruta correcta que coincide con la ubicación de tu CitaRepository
import com.martinvergara_diegoboggle.pawschedule.data.local.repository.CitaRepository
// ------------------- FIN DE LA CORRECCIÓN -------------------

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CitaRepository

    private val _veterinarioId = MutableStateFlow(0)
    val veterinarioId: StateFlow<Int> = _veterinarioId.asStateFlow()

    private val _mascotaId = MutableStateFlow(0)
    val mascotaId: StateFlow<Int> = _mascotaId.asStateFlow()

    private val _fecha = MutableStateFlow(0L)
    val fecha: StateFlow<Long> = _fecha.asStateFlow()

    private val _horaInicio = MutableStateFlow("")
    val horaInicio: StateFlow<String> = _horaInicio.asStateFlow()

    private val _horaFin = MutableStateFlow("")
    val horaFin: StateFlow<String> = _horaFin.asStateFlow()

    private val _motivo = MutableStateFlow("")
    val motivo: StateFlow<String> = _motivo.asStateFlow()

    private val _observaciones = MutableStateFlow("")
    val observaciones: StateFlow<String> = _observaciones.asStateFlow()

    private val _veterinarioError = MutableStateFlow<String?>(null)
    val veterinarioError: StateFlow<String?> = _veterinarioError.asStateFlow()

    private val _mascotaError = MutableStateFlow<String?>(null)
    val mascotaError: StateFlow<String?> = _mascotaError.asStateFlow()

    private val _fechaError = MutableStateFlow<String?>(null)
    val fechaError: StateFlow<String?> = _fechaError.asStateFlow()

    private val _horaError = MutableStateFlow<String?>(null)
    val horaError: StateFlow<String?> = _horaError.asStateFlow()

    private val _motivoError = MutableStateFlow<String?>(null)
    val motivoError: StateFlow<String?> = _motivoError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _citaCreada = MutableStateFlow(false)
    val citaCreada: StateFlow<Boolean> = _citaCreada.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    private val _citasCliente = MutableStateFlow<List<CitaEntity>>(emptyList())
    val citasCliente: StateFlow<List<CitaEntity>> = _citasCliente.asStateFlow()

    private val _citasVeterinario = MutableStateFlow<List<CitaEntity>>(emptyList())
    val citasVeterinario: StateFlow<List<CitaEntity>> = _citasVeterinario.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).citaDao()
        repository = CitaRepository(dao)
    }

    fun onVeterinarioChange(id: Int) {
        _veterinarioId.value = id
        if (id > 0) _veterinarioError.value = null
    }

    fun onMascotaChange(id: Int) {
        _mascotaId.value = id
        if (id > 0) _mascotaError.value = null
    }

    fun onFechaChange(timestamp: Long) {
        _fecha.value = timestamp
        if (timestamp > 0) _fechaError.value = null
    }

    fun onHoraInicioChange(hora: String) {
        _horaInicio.value = hora
        if (hora.isNotEmpty()) _horaError.value = null
    }

    fun onHoraFinChange(hora: String) {
        _horaFin.value = hora
    }

    fun onMotivoChange(value: String) {
        _motivo.value = value
        if (value.isNotEmpty()) _motivoError.value = null
    }

    fun onObservacionesChange(value: String) {
        _observaciones.value = value
    }

    private fun validarFormulario(): Boolean {
        var isValid = true

        if (_veterinarioId.value == 0) {
            _veterinarioError.value = "Selecciona un veterinario"
            isValid = false
        }

        if (_mascotaId.value == 0) {
            _mascotaError.value = "Selecciona una mascota"
            isValid = false
        }

        if (_fecha.value == 0L) {
            _fechaError.value = "Selecciona una fecha"
            isValid = false
        }

        if (_horaInicio.value.isEmpty()) {
            _horaError.value = "Selecciona una hora"
            isValid = false
        }

        if (_motivo.value.isEmpty()) {
            _motivoError.value = "El motivo es obligatorio"
            isValid = false
        }

        return isValid
    }

    fun crearCita(clienteId: Int) {
        if (!validarFormulario()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val horaFin = if (_horaFin.value.isEmpty()) {
                    calcularHoraFin(_horaInicio.value)
                } else {
                    _horaFin.value
                }

                val cita = CitaEntity(
                    clienteId = clienteId,
                    veterinarioId = _veterinarioId.value,
                    mascotaId = _mascotaId.value,
                    fecha = _fecha.value,
                    horaInicio = _horaInicio.value,
                    horaFin = horaFin,
                    motivo = _motivo.value,
                    estado = "Pendiente",
                    observaciones = _observaciones.value
                )

                repository.insertar(cita)

                _citaCreada.value = true
                _isLoading.value = false

                limpiarFormulario()

            } catch (e: Exception) {
                _mensajeError.value = "Error al crear cita: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun cargarCitasCliente(clienteId: Int) {
        viewModelScope.launch {
            repository.obtenerPorCliente(clienteId).collect { citas ->
                _citasCliente.value = citas
            }
        }
    }

    fun cargarCitasVeterinario(veterinarioId: Int) {
        viewModelScope.launch {
            repository.obtenerPorVeterinario(veterinarioId).collect { citas ->
                _citasVeterinario.value = citas
            }
        }
    }

    private fun calcularHoraFin(horaInicio: String): String {
        return try {
            val partes = horaInicio.split(":")
            val hora = partes[0].toInt()
            val minutos = partes[1]
            val horaFin = (hora + 1) % 24
            String.format("%02d:%s", horaFin, minutos)
        } catch (e: Exception) {
            "00:00"
        }
    }

    fun limpiarFormulario() {
        _veterinarioId.value = 0
        _mascotaId.value = 0
        _fecha.value = 0L
        _horaInicio.value = ""
        _horaFin.value = ""
        _motivo.value = ""
        _observaciones.value = ""
        limpiarErrores()
    }

    fun limpiarErrores() {
        _veterinarioError.value = null
        _mascotaError.value = null
        _fechaError.value = null
        _horaError.value = null
        _motivoError.value = null
        _mensajeError.value = null
    }

    fun resetearEstados() {
        _citaCreada.value = false
    }
}