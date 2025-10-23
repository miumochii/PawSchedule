package com.martinvergara_diegoboggle.pawschedule.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.martinvergara_diegoboggle.pawschedule.ui.auth.*
import com.martinvergara_diegoboggle.pawschedule.ui.cliente.*

// --- INICIO DE LA CORRECCIÓN 1 (Errores 'Unresolved reference') ---
// Comentamos los imports de archivos que aún no existen
// import com.martinvergara_diegoboggle.pawschedule.ui.veterinario.CitasDelDiaScreen
// import com.martinvergara_diegoboggle.pawschedule.ui.veterinario.MisHorariosScreen
// --- FIN DE LA CORRECCIÓN 1 ---

import com.martinvergara_diegoboggle.pawschedule.ui.veterinario.VeterinarioHomeScreen
import com.martinvergara_diegoboggle.pawschedule.ui.viewmodel.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ClienteRegister : Screen("cliente_register")
    object VeterinarioRegister : Screen("veterinario_register")
    object ClienteHome : Screen("cliente_home/{userId}/{userName}") {
        fun createRoute(userId: Int, userName: String) = "cliente_home/$userId/$userName"
    }
    object VeterinarioHome : Screen("veterinario_home/{userId}/{userName}") {
        fun createRoute(userId: Int, userName: String) = "veterinario_home/$userId/$userName"
    }
    object MisMascotas : Screen("mis_mascotas/{clienteId}") {
        fun createRoute(clienteId: Int) = "mis_mascotas/$clienteId"
    }
    object RegistrarMascota : Screen("registrar_mascota/{clienteId}") {
        fun createRoute(clienteId: Int) = "registrar_mascota/$clienteId"
    }
    object AgendarCita : Screen("agendar_cita/{clienteId}") {
        fun createRoute(clienteId: Int) = "agendar_cita/$clienteId"
    }
    object MisCitas : Screen("mis_citas/{clienteId}") {
        fun createRoute(clienteId: Int) = "mis_citas/$clienteId"
    }
    // --- INICIO DE LA CORRECCIÓN 1 (Errores 'Unresolved reference') ---
    // Comentamos las rutas que apuntan a archivos no existentes
    // object CitasDelDia : Screen("citas_del_dia/{veterinarioId}") {
    //     fun createRoute(veterinarioId: Int) = "citas_del_dia/$veterinarioId"
    // }
    // object MisHorarios : Screen("mis_horarios/{veterinarioId}") {
    //     fun createRoute(veterinarioId: Int) = "mis_horarios/$veterinarioId"
    // }
    // --- FIN DE LA CORRECCIÓN 1 ---
}

@Composable
fun PawScheduleNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Login
        composable(Screen.Login.route) {
            val context = LocalContext.current
            LoginScreen(
                viewModel = viewModel(
                    factory = LoginViewModelFactory(context.applicationContext as Application)
                ),
                onLoginSuccess = { userId, userType, userName ->
                    when (userType) {
                        "Cliente" -> {
                            navController.navigate(Screen.ClienteHome.createRoute(userId, userName)) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        "Veterinario" -> {
                            navController.navigate(Screen.VeterinarioHome.createRoute(userId, userName)) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // Register Selector
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToClienteRegister = {
                    navController.navigate(Screen.ClienteRegister.route)
                },
                onNavigateToVeterinarioRegister = {
                    navController.navigate(Screen.VeterinarioRegister.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Cliente Register
        composable(Screen.ClienteRegister.route) {
            ClienteRegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegistroExitoso = {
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                }
            )
        }

        // Veterinario Register
        composable(Screen.VeterinarioRegister.route) {
            VeterinarioRegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegistroExitoso = {
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                }
            )
        }

        // Cliente Home
        composable(
            route = Screen.ClienteHome.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            ClienteHomeScreen(
                userName = userName,
                onNavigateToMascotas = {
                    navController.navigate(Screen.MisMascotas.createRoute(userId))
                },
                onNavigateToAgendarCita = {
                    navController.navigate(Screen.AgendarCita.createRoute(userId))
                },
                onNavigateToMisCitas = {
                    navController.navigate(Screen.MisCitas.createRoute(userId))
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Veterinario Home
        composable(
            route = Screen.VeterinarioHome.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            VeterinarioHomeScreen(
                userName = userName,
                // Comentamos las navegaciones que no existen
                // onNavigateToCitasDelDia = {
                //     navController.navigate(Screen.CitasDelDia.createRoute(userId))
                // },
                // onNavigateToMisHorarios = {
                //     navController.navigate(Screen.MisHorarios.createRoute(userId))
                // },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Mis Mascotas
        composable(
            route = Screen.MisMascotas.route,
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0

            MisMascotasScreen(
                clienteId = clienteId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegistrarMascota = {
                    navController.navigate(Screen.RegistrarMascota.createRoute(clienteId))
                }
            )
        }

        // Registrar Mascota
        composable(
            route = Screen.RegistrarMascota.route,
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0

            RegistrarMascotaScreen(
                clienteId = clienteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Agendar Cita
        composable(
            route = Screen.AgendarCita.route,
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0

            // Ahora esta llamada funciona porque AgendarCitaScreen sí acepta 'clienteId'
            AgendarCitaScreen(
                clienteId = clienteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Mis Citas
        composable(
            route = Screen.MisCitas.route,
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0

            MisCitasScreen(
                clienteId = clienteId, // (Esta ya la habías arreglado)
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- INICIO DE LA CORRECCIÓN 1 (Errores 'Unresolved reference') ---
        // Comentamos los composables que apuntan a archivos no existentes
        /*
        // Citas Del Dia (Veterinario)
        composable(
            route = Screen.CitasDelDia.route,
            arguments = listOf(navArgument("veterinarioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val veterinarioId = backStackEntry.arguments?.getInt("veterinarioId") ?: 0

            CitasDelDiaScreen(
                veterinarioId = veterinarioId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Mis Horarios (Veterinario)
        composable(
            route = Screen.MisHorarios.route,
            arguments = listOf(navArgument("veterinarioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val veterinarioId = backStackEntry.arguments?.getInt("veterinarioId") ?: 0

            MisHorariosScreen(
                veterinarioId = veterinarioId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        */
        // --- FIN DE LA CORRECCIÓN 1 ---
    }
}