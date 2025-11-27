package martinvergara_diegoboggle.pawschedule.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment.AddAppointmentScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.add_pet.AddPetScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.AuthViewModel
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.LoginScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.RegisterScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.home.HomeScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile.PetListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()

    NavHost( //ESTE ES LE VEHÍCULO DE NAVEGACION.
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) { //COMPOSABLE SON LOS DESTINOS, ROUTE, LA RUTA
            LoginScreen(navController = navController, authViewModel = authViewModel) //EQUIPAJE, INFO QUE SE PASA DE PANTALLA EN PANTALLA.
        }

        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.AddAppointmentScreen.route) {
            AddAppointmentScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.PetListScreen.route) {
            PetListScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.AddPetScreen.route) {
            AddPetScreen(navController = navController, authViewModel = authViewModel)
        }
    }
}