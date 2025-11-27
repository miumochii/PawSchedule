package martinvergara_diegoboggle.pawschedule.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import martinvergara_diegoboggle.pawschedule.data.network.RetrofitClient
import martinvergara_diegoboggle.pawschedule.data.network.UserAuth
import martinvergara_diegoboggle.pawschedule.data.repository.PetRepository
import martinvergara_diegoboggle.pawschedule.data.repository.AppointmentRepository


class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // Las funciones onXxxChange se mantienen iguales...

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, errorMessage = null) }
    }

    // --- FUNCIÓN CLAVE: Devuelve el ID para el Scoping de datos ---
    fun getCurrentUserId(): Int {
        return _uiState.value.currentUserId
    }

    // --- FUNCIÓN DE LOGOUT ---
    fun logout() {
        _uiState.update {
            it.copy(
                email = "",
                password = "",
                currentUserId = 0,
                isLoggedIn = false
            )
        }
    }

    // --- FUNCIÓN DE LOGIN (ASÍNCRONA: Habla con tu Microservicio) ---
    fun login() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Correo y contraseña no pueden estar vacíos.") }
            return
        }

        viewModelScope.launch {
            try {
                val authData = UserAuth(state.email, state.password)
                // Llamada Retrofit al endpoint de Login
                val response = RetrofitClient.authApiService.loginUser(authData)

                // ÉXITO: Guardamos la sesión y el ID
                _uiState.update {
                    it.copy(
                        currentUserId = response.userId,
                        isLoggedIn = true,
                        errorMessage = null,
                        password = "" // Limpiamos la contraseña de la memoria
                    )
                }

                // MUY IMPORTANTE: Recargamos los datos del nuevo usuario
                PetRepository.fetchPets(response.userId)
                AppointmentRepository.fetchAppointments(response.userId)

            } catch (e: Exception) {
                Log.e("AUTH_ERROR", "Login failed: ${e.message}")
                _uiState.update { it.copy(errorMessage = "Credenciales incorrectas o servidor no disponible.") }
            }
        }
    }

    // --- FUNCIÓN DE REGISTRO (ASÍNCRONA) ---
    fun register() {
        val state = _uiState.value
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden.") }
            return
        }

        viewModelScope.launch {
            try {
                val authData = UserAuth(state.email, state.password)
                val response = RetrofitClient.authApiService.registerUser(authData)

                // ÉXITO: Guardamos la sesión y el ID
                _uiState.update {
                    it.copy(
                        currentUserId = response.userId,
                        isLoggedIn = true,
                        errorMessage = null
                    )
                }

                // Recargamos (la lista estará vacía, pero la preparamos)
                PetRepository.fetchPets(response.userId)
                AppointmentRepository.fetchAppointments(response.userId)

            } catch (e: Exception) {
                Log.e("AUTH_ERROR", "Registro fallido: ${e.message}")
                _uiState.update { it.copy(errorMessage = "El correo electrónico ya está en uso.") }
            }
        }
    }
}

// ESTADO (ACTUALIZADO CON LA GESTIÓN DE SESIÓN)
data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val currentUserId: Int = 0, // ID numérico para las transacciones (0 = no logueado)
    val isLoggedIn: Boolean = false // Estado de la sesión
)