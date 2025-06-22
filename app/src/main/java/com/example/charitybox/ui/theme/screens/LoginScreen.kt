package com.example.charitybox.ui.theme.screens

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.charitybox.R
import com.example.charitybox.ui.theme.uistates.SplashUiState
import com.example.charitybox.viewmodel.LoginViewModel
import com.example.charitybox.viewmodel.NavigationViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    //onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val navigationViewModel: NavigationViewModel = viewModel() // Fetch the navigation ViewModel
    val isPasswordVisible by loginViewModel.isPasswordVisible.collectAsState()

    // Observe login success and navigate to home screen when successful
    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onNavigateToHome() // Trigger navigation to Home screen
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // App logo and title
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Charity Box",
                fontWeight = Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Connecting Hearts, Changing Lives",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            // Card with email and password fields
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Email input
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { loginViewModel.onEmailChange(it) },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = uiState.errorMessage.isNotEmpty(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            errorBorderColor = MaterialTheme.colorScheme.error
                        ),
                        shape = MaterialTheme.shapes.large,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        singleLine = true,
                        trailingIcon = {
                            if (uiState.email.isNotEmpty()) {
                                IconButton(onClick = { loginViewModel.onEmailChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear Email")
                                }
                            }
                        }
                    )

                    // Password input
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { loginViewModel.onPasswordChange(it) },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = uiState.errorMessage.isNotEmpty(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            errorBorderColor = MaterialTheme.colorScheme.error
                        ),
                        shape = MaterialTheme.shapes.large,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { loginViewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Error message
                    if (uiState.errorMessage.isNotEmpty()) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Buttons for Login and Sign Up
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { loginViewModel.login(uiState.email, uiState.password) },
                            modifier = Modifier
                                .weight(0.8f)
                                .height(50.dp),  // Elevated height for a more prominent button
                            shape = shapes.large,  // Large rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary, // Primary color for the button background
                                contentColor = MaterialTheme.colorScheme.onPrimary, // Text color on the button
                                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Disabled button color
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Disabled text color
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)  // Elevated button with shadow
                        ) {
                            Text(text = "Login", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { onSignUpClick() },
                            modifier = Modifier
                                .weight(0.8f)
                                .height(50.dp),  // Elevated height for a more prominent button
                            shape = shapes.large,  // Large rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary, // Secondary color for the button background
                                contentColor = MaterialTheme.colorScheme.onSecondary, // Text color on the button
                                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Disabled button color
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Disabled text color
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)  // Elevated button with shadow

                        ) {
                            Text(text = "Sign Up",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold))
                        }
                    }
                }
            }
        }
    }
}

