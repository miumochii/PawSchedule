package martinvergara_diegoboggle.pawschedule.data.network

// ESTA PARTE ES PARA EL LOGIN, TIPO, AUTENTIFICA LAS COSAS.

import com.google.gson.annotations.SerializedName

// DATO A ENVIAR.
data class UserAuth(
    val email: String,
    val password: String
)

// DATO A RECIBIR.
data class LoginSuccess(
    // Usamos @SerializedName para mapear 'user_id' del JSON de Python
    @SerializedName("user_id")
    val userId: Int,
    // Este campo se mapea automáticamente porque el nombre coincide
    val email: String
)