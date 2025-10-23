package martinvergara_diegoboggle.pawschedule.ui.screens.auth
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import martinvergara_diegoboggle.pawschedule.R
import martinvergara_diegoboggle.pawschedule.navigation.AppScreens
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo de PawSchedule",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Bienvenido a PawSchedule", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = authViewModel::onEmailChange,
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = authViewModel::onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null
        )
        uiState.errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (authViewModel.login()) {
                    navController.navigate(AppScreens.HomeScreen.route) {
                        popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
        TextButton(onClick = { navController.navigate(AppScreens.RegisterScreen.route) }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}