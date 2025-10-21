package martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment
data class AppointmentFormUiState(
    val petName: String = "",
    val ownerName: String = "",
    val date: String = "",
    val time: String = "",
    val symptoms: String = "",
    val hasAgreedToTerms: Boolean = false,
    val errors: AppointmentFormErrors = AppointmentFormErrors()
)
data class AppointmentFormErrors(
    val petNameError: String? = null,
    val ownerNameError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,
    val symptomsError: String? = null,
    val termsError: String? = null
)