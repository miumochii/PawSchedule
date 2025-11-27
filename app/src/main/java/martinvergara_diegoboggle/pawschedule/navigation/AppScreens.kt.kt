package martinvergara_diegoboggle.pawschedule.navigation
//AQUI SE DEFINEN TODOS LOS NOMBRES DE LAS PANTALLAS PARA PASARSELOS AL APPNAVIGATION.
sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object HomeScreen : AppScreens("home_screen")
    object AddAppointmentScreen : AppScreens("add_appointment_screen")
    object PetListScreen : AppScreens("pet_list_screen")
    object AddPetScreen : AppScreens("add_pet_screen")
}