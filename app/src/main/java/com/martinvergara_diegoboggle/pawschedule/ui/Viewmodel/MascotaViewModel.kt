package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martinvergara_diegoboggle.pawschedule.data.local.database.AppDatabase
import com.martinvergara_diegoboggle.pawschedule.data.local.entities.MascotaEntity

// ------------------- INICIO DE LA CORRECCIÓN -------------------
// Esta es la ruta correcta que coincide con tu archivo de repositorio
import com.martinvergara_diegoboggle.pawschedule.data.local.repository.MascotaRepository
// ------------------- FIN DE LA CORRECCIÓN -------------------

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MascotaRepository

    // --- Estados del formulario ---
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _especie = MutableStateFlow("")
    val especie: StateFlow<String> = _especie.asStateFlow()

    private val _raza = MutableStateFlow("")
    val raza: StateFlow<String> = _raza.asStateFlow()

    private val _edad = MutableStateFlow("")
    val edad: StateFlow<String> = _edad.asStateFlow()

    private val _peso = MutableStateFlow("")
    val peso: StateFlow<String> = _peso.asStateFlow()

    private val _sexo = MutableStateFlow("")
    val sexo: StateFlow<String> = _sexo.asStateFlow()

    private val _color = MutableStateFlow("")
    val color: StateFlow<String> = _color.asStateFlow()

    private val _observaciones = MutableStateFlow("")
    val observaciones: StateFlow<String> = _observaciones.asStateFlow()

    // --- Estados de error del formulario ---
    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _especieError = MutableStateFlow<String?>(null)
    val especieError: StateFlow<String?> = _especieError.asStateFlow()

    private val _razaError = MutableStateFlow<String?>(null)
    val razaError: StateFlow<String?> = _razaError.asStateFlow()

    private val _edadError = MutableStateFlow<String?>(null)
    val edadError: StateFlow<String?> = _edadError.asStateFlow()

    private val _pesoError = MutableStateFlow<String?>(null)
    val pesoError: StateFlow<String?> = _pesoError.asStateFlow()

    private val _sexoError = MutableStateFlow<String?>(null)
    val sexoError: StateFlow<String?> = _sexoError.asStateFlow()

    private val _colorError = MutableStateFlow<String?>(null)
    val colorError: StateFlow<String?> = _colorError.asStateFlow()

    // --- Estados de la UI ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    // --- Estado de datos (lista de mascotas) ---
    private val _mascotas = MutableStateFlow<List<MascotaEntity>>(emptyList())
    val mascotas: StateFlow<List<MascotaEntity>> = _mascotas.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).mascotaDao()
        repository = MascotaRepository(dao)
    }

    // --- Funciones para actualizar el formulario ---
    fun onNombreChange(value: String) {
        _nombre.value = value
        if (value.isNotEmpty()) _nombreError.value = null
    }

    fun onEspecieChange(value: String) {
        _especie.value = value
        if (value.isNotEmpty()) _especieError.value = null
    }

    fun onRazaChange(value: String) {
        _raza.value = value
        if (value.isNotEmpty()) _razaError.value = null
    }

    fun onEdadChange(value: String) {
        _edad.value = value
        if (value.isNotEmpty()) _edadError.value = null
    }

    fun onPesoChange(value: String) {
        _peso.value = value
        if (value.isNotEmpty()) _pesoError.value = null
    }

    fun onSexoChange(value: String) {
        _sexo.value = value
        if (value.isNotEmpty()) _sexoError.value = null
    }

    fun onColorChange(value: String) {
        _color.value = value
        if (value.isNotEmpty()) _colorError.value = null
    }

    fun onObservacionesChange(value: String) {
        _observaciones.value = value
    }

    // --- Lógica de validación ---
    private fun validarFormulario(): Boolean {
        var isValid = true

        if (_nombre.value.isEmpty()) {
            _nombreError.value = "El nombre es obligatorio"
            isValid = false
        }

        if (_especie.value.isEmpty()) {
            _especieError.value = "La especie es obligatoria"
            isValid = false
        }

        if (_raza.value.isEmpty()) {
            _razaError.value = "La raza es obligatoria"
            isValid = false
        }

        if (_edad.value.isEmpty()) {
            _edadError.value = "La edad es obligatoria"
            isValid = false
        } else {
            try {
                val edadInt = _edad.value.toInt()
                if (edadInt < 0 || edadInt > 30) {
                    _edadError.value = "Edad inválida (0-30 años)"
                    isValid = false
                }
            } catch (e: Exception) {
                _edadError.value = "Debe ser un número"
                isValid = false
            }
        }

        if (_peso.value.isEmpty()) {
            _pesoError.value = "El peso es obligatorio"
            isValid = false
        } else {
            try {
                val pesoDouble = _peso.value.toDouble()
                if (pesoDouble <= 0 || pesoDouble > 200) {
                    _pesoError.value = "Peso inválido (0.1-200 kg)"
                    isValid = false
                }
            } catch (e: Exception) {
                _pesoError.value = "Debe ser un número"
                isValid = false
            }
        }

        if (_sexo.value.isEmpty()) {
            _sexoError.value = "El sexo es obligatorio"
            isValid = false
        }

        if (_color.value.isEmpty()) {
            _colorError.value = "El color es obligatorio"
            isValid = false
        }

        return isValid
    }

    // --- Funciones de Lógica de Negocio ---
    fun registrarMascota(clienteId: Int) {
        if (!validarFormulario()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val mascota = MascotaEntity(
                    idDueno = clienteId,
                    nombre = _nombre.value,
                    especie = _especie.value,
                    raza = _raza.value,
                    edad = _edad.value.toInt(),
                    peso = _peso.value.toDouble(),
                    sexo = _sexo.value,
                    color = _color.value,
                    observaciones = _observaciones.value.takeIf { it.isNotEmpty() }
                )

                repository.insertar(mascota)

                _registroExitoso.value = true
                _isLoading.value = false

                limpiarFormulario()
                cargarMascotasDelCliente(clienteId) // Actualiza la lista en MisMascotas

            } catch (e: Exception) {
                _mensajeError.value = "Error al registrar: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun cargarMascotasDelCliente(clienteId: Int) {
        viewModelScope.launch {
            repository.obtenerPorDueno(clienteId).collect { lista ->
                _mascotas.value = lista
            }
        }
    }

    // --- Funciones de limpieza ---
    fun limpiarFormulario() {
        _nombre.value = ""
        _especie.value = ""
        _raza.value = ""
        _edad.value = ""
        _peso.value = ""
        _sexo.value = ""
        _color.value = ""
        _observaciones.value = ""
        limpiarErrores()
    }

    fun limpiarErrores() {
        _nombreError.value = null
        _especieError.value = null
        _razaError.value = null
        _edadError.value = null
        _pesoError.value = null
        _sexoError.value = null
        _colorError.value = null
        _mensajeError.value = null
    }

    fun resetearEstados() {
        _registroExitoso.value = false
    }
}