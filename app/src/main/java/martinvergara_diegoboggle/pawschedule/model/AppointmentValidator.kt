package martinvergara_diegoboggle.pawschedule.model

object AppointmentValidator {

    // Esta función devuelve TRUE si todo está bien, FALSE si hay errores
    fun validateAppointment(
        petName: String,
        ownerName: String,
        symptoms: String,
        hasAgreedToTerms: Boolean
    ): Boolean {
        // 1. Validar nombres
        if (petName.isBlank()) return false
        if (ownerName.isBlank()) return false

        // 2. Validar longitud de síntomas (mínimo 10 letras)
        if (symptoms.length < 10) return false

        // 3. Validar términos aceptados
        if (!hasAgreedToTerms) return false

        return true
    }
}