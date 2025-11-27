package martinvergara_diegoboggle.pawschedule.data.repository
//AQUI TAMBIÉN SE GUARDA LA INFO.
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import martinvergara_diegoboggle.pawschedule.model.User
object UserRepository {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()
    fun findUserByEmail(email: String): User? {
        return _users.value.find { it.email.equals(email, ignoreCase = true) }
    }
    fun registerUser(email: String, passwordHash: String): Boolean {
        if (findUserByEmail(email) != null) {
            return false
        }
        val newUser = User(email = email, passwordHash = passwordHash)
        _users.value += newUser
        return true
    }
}