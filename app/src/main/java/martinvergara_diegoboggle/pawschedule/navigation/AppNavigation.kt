package martinvergara_diegoboggle.pawschedule.navigation
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import martinvergara_diegoboggle.pawschedule.ui.screens.add_appointment.AddAppointmentScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.AuthViewModel
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.LoginScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.auth.RegisterScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.home.HomeScreen
import martinvergara_diegoboggle.pawschedule.ui.screens.pet_profile.PetListScreen
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
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
            HomeScreen(navController)
        }
        composable(route = AppScreens.AddAppointmentScreen.route) {
            AddAppointmentScreen(navController)
        }
        composable(route = AppScreens.PetListScreen.route) {
            PetListScreen(navController)
        }
    }
}