package com.example.charitybox.ui.theme.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.charitybox.CharityBoxScreen
import com.example.charitybox.R
import com.example.charitybox.donation.DonationCategory
import com.example.charitybox.donation.donationCategories
import com.example.charitybox.viewmodel.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = viewModel(), navController: NavHostController) {
    val uiState by homeScreenViewModel.uiState.collectAsState()

    // Fetch data when the screen is displayed
    homeScreenViewModel.fetchUserData()

    // Set the content for the screen
    Scaffold(
        topBar = {
            val bottomBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) // Define the color in the @Composable scope

            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // App Logo
                        Image(
                            modifier = Modifier
                                .size(45.dp) // Logo size
                                .padding(8.dp), // Padding around the logo
                            painter = painterResource(R.drawable.applogo), // Replace with your logo
                            contentDescription = null
                        )

                        // App Name
                        Text(
                            text = "CharityBox",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant // Maintain your color scheme
                            ),
                            modifier = Modifier.padding(start = 8.dp) // Space between logo and text
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f), // Darker shade of card background
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Title text color
                ),
                modifier = Modifier
                    .fillMaxWidth() // Fills the whole width
                    .clip(MaterialTheme.shapes.extraLarge) // Rounded edges
                    .border( // Adds a border
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // Matches card's border color
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.75f) // Even darker background
                    )
                    .drawBehind {
                        // Draw a bottom border using the pre-calculated color
                        val borderWidth = 2.dp.toPx()
                        drawLine(
                            color = bottomBorderColor,
                            start = Offset(0f, size.height - borderWidth),
                            end = Offset(size.width, size.height - borderWidth),
                            strokeWidth = borderWidth
                        )
                    }
                    .statusBarsPadding() // Integrates well with the status bar
            )
        }
    ) { padding ->
        // Ensure padding is correctly applied to avoid overlap with the top bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 88.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Search Box at the top, placed under the TopAppBar
            //var searchQuery by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    //.fillMaxHeight()
                    .padding(12.dp) // Adjust padding to place under the TopAppBar
                    .clip(RoundedCornerShape(50.dp)) // Pill shape
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50.dp)) // Custom border color
                    .background(MaterialTheme.colorScheme.surface) // Background color
            ) {
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = { homeScreenViewModel.updateSearchQuery(it) },
                    label = { Text("") }, // No label text
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle Done action */ }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface, // Background when focused
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface, // Background when unfocused
                        disabledContainerColor = MaterialTheme.colorScheme.surface, // Background when disabled
                        focusedTextColor = MaterialTheme.colorScheme.onSurface, // Text color when focused
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f), // Text color when unfocused
                        disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), // Text color when disabled
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface, // Leading icon color when focused
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f), // Leading icon color when unfocused
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), // Leading icon color when disabled
                        focusedIndicatorColor = Color.Transparent, // Transparent underline when focused
                        unfocusedIndicatorColor = Color.Transparent, // Transparent underline when unfocused
                        disabledIndicatorColor = Color.Transparent // Transparent underline when disabled
                    )
                )

            }

            // Display content based on login state
            if (uiState.isLoggedIn) {
                // Filtered list of categories based on the search query
                /*val filteredCategories = donationCategories.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }
*/
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp), // Padding to avoid overlap with search bar
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.filteredCategories) { category ->
                        DonationCategoryCard(category, homeScreenViewModel, navController = navController)
                    }
                }
            } else {
                Text(
                    text = "You are not logged in. Please log in.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DonationCategoryCard(category: DonationCategory, viewModel: HomeScreenViewModel, navController: NavHostController) {
    val isExpanded = viewModel.uiState.collectAsState().value.showAdditionalContent[category.id] ?: false


    // Card to hold the image, title, and description
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp), // Add padding around the card
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevated card
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f) // Slightly darkened variant color
        ),*/
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)) // Subtle border
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image in a circular shape on the left
                Image(
                    painter = painterResource(id = category.imageResourceId),
                    contentDescription = category.title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface),
                        //.padding(4.dp),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Row for title and icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f) // Ensure title takes space
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                // Toggle visibility of additional content
                                viewModel.toggleShowAdditionalContent(category.id)
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Conditional content based on showAdditionalContent state
            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Show the description when clicked
                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Donation Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("${CharityBoxScreen.DonationForm.name}/${category.id}")
                            },
                            modifier = Modifier
                                .weight(0.8f)
                                .height(50.dp),  // Elevated height for a more prominent button
                            shape = shapes.large,  // Large rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary, // Secondary color for the button background
                                contentColor = MaterialTheme.colorScheme.onPrimary, // Text color on the button
                                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Disabled button color
                                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Disabled text color
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                        ) {
                            Text(text = "Donate Now")
                        }
                    }
                }
            }
        }
    }
}
