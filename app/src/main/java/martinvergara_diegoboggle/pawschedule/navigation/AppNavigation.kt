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

    // Compartimos el AuthViewModel para que el estado de login/registro persista
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        // 1. Login
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        // 2. Registro
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }

        // 3. Home (Principal)
        composable(route = AppScreens.HomeScreen.route) {
            // Nota: Aquí viewModel() crea una instancia nueva o usa la por defecto definida en la pantalla
            HomeScreen(navController = navController)
        }

        // 4. Agendar Cita
        composable(route = AppScreens.AddAppointmentScreen.route) {
            AddAppointmentScreen(navController = navController)
        }

        // 5. Lista de Mascotas
        composable(route = AppScreens.PetListScreen.route) {
            PetListScreen(navController = navController)
        }

        // 6. Añadir Mascota (La nueva pantalla con GPS y Cámara)
        composable(route = AppScreens.AddPetScreen.route) {
            AddPetScreen(navController = navController)
        }
    }
}