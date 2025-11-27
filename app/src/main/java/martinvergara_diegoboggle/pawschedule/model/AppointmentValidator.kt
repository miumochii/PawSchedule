package martinvergara_diegoboggle.pawschedule.model

//AQUÍ ESTÁN LAS VALIDACIONES DE AUTENTIFICACIÓN

object AppointmentValidator {

    fun validateAppointment(
        petName: String,
        ownerName: String,
        symptoms: String,
        hasAgreedToTerms: Boolean
    ): Boolean {
        if (petName.isBlank()) return false
        if (ownerName.isBlank()) return false

        if (symptoms.length < 10) return false

        if (!hasAgreedToTerms) return false

        return true
    }
}