package com.example.charitybox.ui.theme.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.charitybox.CharityBoxScreen
import com.example.charitybox.R
import com.example.charitybox.viewmodel.NavigationViewModel
import com.example.charitybox.viewmodel.LoginViewModel

@Composable
fun SplashScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavController, // Add navController here
    onNavigateToLogin: () -> Unit // Define the navigation callback
) {
    val isUserLoggedIn by navigationViewModel.isUserLoggedIn.collectAsState()

    // Trigger navigation after the splash screen display time
    LaunchedEffect(Unit) {
        delay(5000) // Delay for splash screen animation
        navController.navigate(CharityBoxScreen.Login.name) {
            popUpTo(CharityBoxScreen.Splash.name) { inclusive = true }
        } // Navigate to login after delay
    }

    // Animation for logo movement
    val logoOffset by animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(durationMillis = 2000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Charity Box",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Connecting Hearts, Changing Lives",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
