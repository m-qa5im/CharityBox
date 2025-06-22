package com.example.charitybox.ui.theme.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.charitybox.R
import com.example.charitybox.viewmodel.SignUpViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.charitybox.CharityBoxScreen
import com.example.charitybox.viewmodel.NavigationViewModel
import kotlinx.coroutines.delay

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onSignUpSuccess: @Composable () -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {



    val uiState by signUpViewModel.uiState.collectAsState()
    val isPasswordVisible = uiState.isPasswordVisible
    val navigateToLogin by signUpViewModel.navigateToLogin.collectAsState()

    LaunchedEffect(Unit) {
        //delay(2000) // Delay for splash screen animation
        if (uiState.isSignUpSuccessful) {
            onBackToLoginClick() // Call the provided navigation function to go back to login screen
        }// Navigate to login after delay
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
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
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Create Your Account",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Name input
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { signUpViewModel.onNameChange(it) },
                        label = { Text("Name") },
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
                            if (uiState.name.isNotEmpty()) {
                                IconButton(onClick = { signUpViewModel.onNameChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear Email")
                                }
                            }
                        }
                        /*trailingIcon = {
                            if (uiState.name.isNotEmpty()) {
                                IconButton(onClick = { signUpViewModel.onNameChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear Name")
                                }
                            }
                        }*/
                    )

                    // Email input
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { signUpViewModel.onEmailChange(it) },
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
                                IconButton(onClick = { signUpViewModel.onEmailChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear Email")
                                }
                            }
                        }
                        /*trailingIcon = {
                            if (uiState.email.isNotEmpty()) {
                                IconButton(onClick = { signUpViewModel.onEmailChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear Email")
                                }
                            }
                        }*/
                    )

                    // Password input
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { signUpViewModel.onPasswordChange(it) },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                            IconButton(onClick = { signUpViewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    // Confirm Password input
                    OutlinedTextField(
                        value = uiState.confirmPassword,
                        onValueChange = { signUpViewModel.onConfirmPasswordChange(it) },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                            IconButton(onClick = { signUpViewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            onClick = { signUpViewModel.signUp(uiState.name, uiState.email, uiState.password, uiState.confirmPassword) },
                            modifier = Modifier
                                .height(50.dp),
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(4.dp),

                        ) {
                            Text(
                                text = "Sign Up",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )

                        }

                        Button(
                            onClick = { onBackToLoginClick()
                                 },
                            modifier = Modifier
                                .height(50.dp),
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                        ) {
                            Text(
                                text = "Go Back to Sign In",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }




                    if (uiState.passwordError.isNotEmpty()) {
                        Text(
                            text = uiState.passwordError,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Show error message if any
                    if (uiState.errorMessage.isNotEmpty()) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Show loading indicator
                    if (uiState.isLoading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

        }
    }
}
