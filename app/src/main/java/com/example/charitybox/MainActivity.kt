package com.example.charitybox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.charitybox.data.DonationDatabase
import com.example.charitybox.data.UserDatabase
import com.example.charitybox.donation.DonationType
import com.example.charitybox.repository.DonationRepositoryImpl
import com.example.charitybox.repository.UsersRepositoryImpl
import com.example.charitybox.ui.theme.CharityBoxTheme
import com.example.charitybox.ui.theme.screens.DonationFormScreen
import com.example.charitybox.ui.theme.screens.HomeScreen
import com.example.charitybox.ui.theme.screens.LoginScreen
import com.example.charitybox.ui.theme.screens.SignUpScreen
import com.example.charitybox.ui.theme.screens.SplashScreen
import com.example.charitybox.viewmodel.DonationFormViewModel
import com.example.charitybox.viewmodel.DonationFormViewModelFactory
import com.example.charitybox.viewmodel.LoginViewModel
import com.example.charitybox.viewmodel.LoginViewModelFactory
import com.example.charitybox.viewmodel.NavigationViewModel
import com.example.charitybox.viewmodel.SignUpViewModel
import com.example.charitybox.viewmodel.SignUpViewModelFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize databases and DAOs
        val userDatabase = UserDatabase.getDatabase(applicationContext)
        val userDao = userDatabase.userDao()
        val donationDatabase = DonationDatabase.getDatabase(applicationContext)
        val donationDao = donationDatabase.donationDao()

        // Initialize repositories
        val usersRepository = UsersRepositoryImpl(userDao)
        val donationRepository: DonationRepositoryImpl = DonationRepositoryImpl(donationDao)

        setContent {
            CharityBoxTheme(darkTheme = false) { // Always light mode
                // Initialize ViewModel factories
                val loginViewModelFactory = LoginViewModelFactory(usersRepository)
                val signUpViewModelFactory = SignUpViewModelFactory(usersRepository)
                val donationFormViewModelFactory = DonationFormViewModelFactory(donationRepository)

                // Initialize ViewModels
                val loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
                val signUpViewModel = ViewModelProvider(this, signUpViewModelFactory).get(SignUpViewModel::class.java)
                val navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

                val donationFormViewModel = viewModel<DonationFormViewModel>(
                    factory = DonationFormViewModelFactory(donationRepository)
                )

                CharityBoxApp(
                    navigationViewModel = navigationViewModel,
                    loginViewModel = loginViewModel,
                    signUpViewModel = signUpViewModel,
                    donationFormViewModel = donationFormViewModel
                )
            }
        }
    }
}


enum class CharityBoxScreen {
    Splash,
    Login,
    Signup,
    Home,
    DonationForm
}

@Composable
fun CharityBoxApp(
    navigationViewModel: NavigationViewModel,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    donationFormViewModel: DonationFormViewModel,
    navController: NavHostController = rememberNavController()
) {
    // Observe the current destination state
    val currentDestination by navigationViewModel.currentDestination.collectAsState()

    // Automatically navigate based on the current destination
    LaunchedEffect(currentDestination) {
        when (currentDestination) {
            CharityBoxScreen.Login -> {
                navController.navigate(CharityBoxScreen.Login.name) {
                    popUpTo(CharityBoxScreen.Login.name) { inclusive = true }
                }
            }
            CharityBoxScreen.Signup -> {
                navController.navigate(CharityBoxScreen.Signup.name) {
                    popUpTo(CharityBoxScreen.Login.name) { inclusive = true }
                }
            }
            CharityBoxScreen.Home -> {
                navController.navigate(CharityBoxScreen.Home.name) {
                    popUpTo(CharityBoxScreen.Login.name) { inclusive = true }
                }
            }
            CharityBoxScreen.Splash -> {
                navController.navigate(CharityBoxScreen.Login.name) {
                    popUpTo(CharityBoxScreen.Splash.name) { inclusive = true }
                }
            }
            CharityBoxScreen.DonationForm -> {
                navController.navigate(CharityBoxScreen.DonationForm.name){
                    popUpTo(CharityBoxScreen.DonationForm.name) { inclusive = true }
                }
            }
        }
    }

    // Define NavHost
    NavHost(
        navController = navController,
        startDestination = CharityBoxScreen.Splash.name,
        modifier = Modifier
    ) {
        composable(route = CharityBoxScreen.Splash.name) {
            SplashScreen(
                navigationViewModel = navigationViewModel,
                navController = navController,
                onNavigateToLogin = {
                    navigationViewModel.updateCurrentDestination(CharityBoxScreen.Login)
                }
            )
        }

        composable(route = CharityBoxScreen.Login.name) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onSignUpClick = {
                    navigationViewModel.updateCurrentDestination(CharityBoxScreen.Signup)
                },
                onNavigateToHome = {
                    navigationViewModel.updateCurrentDestination(CharityBoxScreen.Home)
                }
            )
        }

        composable(route = CharityBoxScreen.Signup.name) {
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                onBackToLoginClick = {
                    navigationViewModel.updateCurrentDestination(CharityBoxScreen.Login)
                }
            )
        }

        composable(route = CharityBoxScreen.Home.name) {
            HomeScreen(
                homeScreenViewModel = viewModel(),
                navController = navController
            )
        }

        composable(
            route = "${CharityBoxScreen.DonationForm.name}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: -1
            val category = DonationType.values().find { it.ordinal == categoryId } ?: DonationType.DEFAULT

            DonationFormScreen(
                viewModel = donationFormViewModel,
                navHostController = navController,
                category = category
            )
        }

    }
}
