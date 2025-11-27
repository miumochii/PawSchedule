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

    // ✅ CORRECCIÓN: AuthViewModel compartido en toda la app
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.HomeScreen.route) {
            // ✅ Pasamos el authViewModel
            HomeScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.AddAppointmentScreen.route) {
            // ✅ Pasamos el authViewModel
            AddAppointmentScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.PetListScreen.route) {
            // ✅ Pasamos el authViewModel
            PetListScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = AppScreens.AddPetScreen.route) {
            // ✅ Pasamos el authViewModel
            AddPetScreen(navController = navController, authViewModel = authViewModel)
        }
    }
}