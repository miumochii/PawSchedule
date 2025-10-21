package martinvergara_diegoboggle.pawschedule.ui.screens.auth
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import martinvergara_diegoboggle.pawschedule.data.repository.UserRepository
class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }
    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, errorMessage = null) }
    }
    fun login(): Boolean {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Correo y contraseña no pueden estar vacíos.") }
            return false
        }
        val user = UserRepository.findUserByEmail(state.email)
        if (user == null || user.passwordHash != state.password) {
            _uiState.update { it.copy(errorMessage = "Credenciales incorrectas.") }
            return false
        }
        return true
    }
    fun register(): Boolean {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank() || state.confirmPassword.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios.") }
            return false
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden.") }
            return false
        }
        val success = UserRepository.registerUser(state.email, state.password)
        if (!success) {
            _uiState.update { it.copy(errorMessage = "El correo electrónico ya está en uso.") }
            return false
        }
        return true
    }
}
data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null
)