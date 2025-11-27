package martinvergara_diegoboggle.pawschedule.ui.screens.auth
//LOGICA PARA LOGIN SCREEN Y REGISTER SCREEN
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

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, errorMessage = null) }
    }

    fun getCurrentUserId(): Int {
        return _uiState.value.currentUserId
    }

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

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Correo y contraseña no pueden estar vacíos.") }
            return
        }

        viewModelScope.launch {
            try {
                val authData = UserAuth(state.email, state.password)
                val response = RetrofitClient.authApiService.loginUser(authData)

                _uiState.update {
                    it.copy(
                        currentUserId = response.userId,
                        isLoggedIn = true,
                        errorMessage = null,
                        password = ""
                    )
                }

                PetRepository.fetchPets(response.userId)
                AppointmentRepository.fetchAppointments(response.userId)

                onSuccess()

            } catch (e: Exception) {
                Log.e("AUTH_ERROR", "Login failed: ${e.message}")
                _uiState.update {
                    it.copy(errorMessage = "Credenciales incorrectas o servidor no disponible.")
                }
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden.") }
            return
        }

        viewModelScope.launch {
            try {
                val authData = UserAuth(state.email, state.password)
                val response = RetrofitClient.authApiService.registerUser(authData)

                _uiState.update {
                    it.copy(
                        currentUserId = response.userId,
                        isLoggedIn = true,
                        errorMessage = null
                    )
                }

                PetRepository.fetchPets(response.userId)
                AppointmentRepository.fetchAppointments(response.userId)

                onSuccess()

            } catch (e: Exception) {
                Log.e("AUTH_ERROR", "Registro fallido: ${e.message}")
                _uiState.update {
                    it.copy(errorMessage = "El correo electrónico ya está en uso.")
                }
            }
        }
    }
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val currentUserId: Int = 0,
    val isLoggedIn: Boolean = false
)