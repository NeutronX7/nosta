package com.isaalutions.nosta

import Home
import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isaalutions.nosta.io.repository.AuthState
import com.isaalutions.nosta.ui.screens.Login
import com.isaalutions.nosta.ui.screens.LoginScreen
import com.isaalutions.nosta.ui.screens.Registration
import com.isaalutions.nosta.ui.screens.RegistrationScreen
import com.isaalutions.nosta.ui.theme.NostaTheme
import com.isaalutions.nosta.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.descriptors.StructureKind

data class BottomNavigationItem (
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
)

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object Profile: Screen("profile_screen")
    object Cart: Screen("cart_screen")
    object Setting: Screen("setting_screen")
}

val navigationItems = listOf(
    BottomNavigationItem(
        label = "Home",
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        route = "home"
    ),
    BottomNavigationItem(
        label = "Profile",
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        route = "profile"
    )
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NostaTheme {
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = authViewModel.authState.collectAsState()

    val start = when (authState.value) {
        is AuthState.Authenticated -> Home
        is AuthState.Unauthenticated -> Login
    }

    NavHost(navController = navController, startDestination = start) {
        composable<Login> {
            LoginScreen(navController)
        }

        composable<Registration> {
            RegistrationScreen(navController)
        }

        composable<Home> {
            HomeScreen(navController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NostaTheme {
        Greeting("Android")
    }
}