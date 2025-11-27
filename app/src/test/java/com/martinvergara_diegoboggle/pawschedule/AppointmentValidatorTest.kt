package martinvergara_diegoboggle.pawschedule

import martinvergara_diegoboggle.pawschedule.model.AppointmentValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppointmentValidatorTest {

    @Test
    fun `validateAppointment returns TRUE when all data is correct`() {
        // GIVEN (Dado unos datos correctos)
        val result = AppointmentValidator.validateAppointment(
            petName = "Firulais",
            ownerName = "Diego",
            symptoms = "Tiene mucha tos y fiebre alta", // +10 caracteres
            hasAgreedToTerms = true
        )

        // THEN (Entonces el resultado debe ser Verdadero)
        assertTrue("La validación debería pasar si los datos están bien", result)
    }

    @Test
    fun `validateAppointment returns FALSE when pet name is empty`() {
        val result = AppointmentValidator.validateAppointment(
            petName = "", // <--- ERROR AQUÍ
            ownerName = "Diego",
            symptoms = "Tiene mucha tos",
            hasAgreedToTerms = true
        )
        assertFalse("La validación debería fallar si no hay nombre de mascota", result)
    }

    @Test
    fun `validateAppointment returns FALSE when symptoms are too short`() {
        val result = AppointmentValidator.validateAppointment(
            petName = "Firulais",
            ownerName = "Diego",
            symptoms = "Tos", // <--- Muy corto (< 10 chars)
            hasAgreedToTerms = true
        )
        assertFalse("La validación debería fallar si los síntomas son muy cortos", result)
    }

    @Test
    fun `validateAppointment returns FALSE when terms are not agreed`() {
        val result = AppointmentValidator.validateAppointment(
            petName = "Firulais",
            ownerName = "Diego",
            symptoms = "Tiene mucha tos y fiebre alta",
            hasAgreedToTerms = false // <--- No aceptó términos
        )
        assertFalse("La validación debería fallar si no acepta los términos", result)
    }
}