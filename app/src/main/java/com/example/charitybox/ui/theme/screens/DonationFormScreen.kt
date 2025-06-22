package com.example.charitybox.ui.theme.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.charitybox.R
import com.example.charitybox.donation.DonationType
import com.example.charitybox.viewmodel.DonationFormViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationFormScreen(
    viewModel: DonationFormViewModel,
    navHostController: NavHostController,
    category: DonationType
) {
//    val formState = viewModel.donationFormState.value
    val formState by viewModel.donationFormState.collectAsState() //-- changed
    val context = LocalContext.current

    Scaffold(
        topBar = {
            val bottomBorderColor =
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) // Define the color in the @Composable scope

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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 108.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Common fields for all donation types

                Text(
                    text = "Donate",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
//                OutlinedTextField(
//                    value = formState.amount,
//                    onValueChange = viewModel::onAmountChanged,
//                    label = { Text("Donation Amount") },
//                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = MaterialTheme.colorScheme.primary,
//                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
//                        errorBorderColor = MaterialTheme.colorScheme.error
//                    ),
//                    shape = MaterialTheme.shapes.large,
//                    textStyle = MaterialTheme.typography.bodyLarge,
//                    singleLine = true,
//                )
//
//                OutlinedTextField(
//                    value = formState.donorName,
//                    onValueChange = viewModel::onDonorNameChanged,
//                    label = { Text("Donor Name") },
//                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = MaterialTheme.colorScheme.primary,
//                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
//                        errorBorderColor = MaterialTheme.colorScheme.error
//                    ),
//                    shape = MaterialTheme.shapes.large,
//                    textStyle = MaterialTheme.typography.bodyLarge,
//                    singleLine = true,
//                )
//
                // Dropdown for donation type selection
                DonationTypeDropdown(
                    selectedType = formState.donationType,
                    onTypeSelected = viewModel::onDonationTypeChanged
                )
//
                // Dynamic fields based on selected donation type
                when (formState.donationType) {
                    DonationType.EDUCATION -> EducationSpecificFields(viewModel)
                    DonationType.HEALTH -> HealthSpecificFields(viewModel)
                    DonationType.BLOOD_DONATION-> BloodDonationFields(viewModel)
                    DonationType.POVERTY_ALL -> PovertyAlleviationFields(viewModel)
                    DonationType.DISASTER_RELIEF -> DisasterReliefFields(viewModel)
                    DonationType.ANIMAL_WELFARE -> AnimalWelfareFields(viewModel)
                    DonationType.ENVIRONMENTAL -> EnvironmentalProtectionFields(viewModel)
                    DonationType.WOMEN_EMPOWERMENT -> WomenEmpowermentFields(viewModel)
                    DonationType.CHILD_PROTECTION -> ChildProtectionFields(viewModel)
                    DonationType.DISABILITY_SUPPORT -> DisabledPersonsFields(viewModel)
                    //DonationType.DISASTER_RELIEF -> DisasterReliefSpecificFields(viewModel)
                    // Add other categories here as needed
                    else -> Spacer(modifier = Modifier.height(8.dp))
                }

//                Spacer(modifier = Modifier.height(16.dp))

                // Submit button
                Button(
                    onClick = {
                        viewModel.submitDonationForm(
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Operation Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onError = { /* Handle error (e.g., show error message) */ }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.weight(0.8f),
                    //.height(50.dp),  // Elevated height for a more prominent button
                    shape = shapes.large,  // Large rounded corners
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Primary color for the button background
                        contentColor = MaterialTheme.colorScheme.onPrimary, // Text color on the button
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Disabled button color
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Disabled text color
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text("Submit Donation")
                }
            }
        }
    }
}

@Composable
fun DonationTypeDropdown(
    selectedType: DonationType,
    onTypeSelected: (DonationType) -> Unit
) {
    val items = DonationType.values().toList()
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedType.name,
            onValueChange = {},
            label = { Text("Select Donation Type") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            shape = MaterialTheme.shapes.large,
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    },
                    text = { Text(type.name) }
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EducationSpecificFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
    OutlinedTextField(
        value = formState.scholarshipType,
        onValueChange = viewModel::onScholarshipTypeChanged,
        label = { Text("Scholarship Type") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
    OutlinedTextField(
        value = formState.scholarshipRegion,
        onValueChange = viewModel::onScholarshipRegionChanged,
        label = { Text("Region/Area for Scholarships") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HealthSpecificFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
    OutlinedTextField(
        value = formState.healthSupportType,
        onValueChange = viewModel::onHealthSupportTypeChanged,
        label = { Text("Type of Health Support") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
    OutlinedTextField(
        value = formState.preferredBeneficiaries,
        onValueChange = viewModel::onPreferredBeneficiariesChanged,
        label = { Text("Preferred Beneficiaries") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BloodDonationFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.bloodGroup,
        onValueChange = viewModel::onBloodGroupChanged,
        label = { Text("Blood Group") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}





@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PovertyAlleviationFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.povertySupportType,
        onValueChange = viewModel::onPovertySupportTypeChanged,
        label = { Text("Support Type") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DisasterReliefFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.disasterImpactArea,
        onValueChange = viewModel::onDisasterImpactAreaChanged,
        label = { Text("Impact Area") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WomenEmpowermentFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.womenEmpowermentFocus,
        onValueChange = viewModel::onWomenEmpowermentFocusChanged,
        label = { Text("Empowerment Focus") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChildProtectionFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.childProtectionFocus,
        onValueChange = viewModel::onChildProtectionFocusChanged,
        label = { Text("Protection Focus") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EnvironmentalProtectionFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.environmentalCause,
        onValueChange = viewModel::onEnvironmentalCauseChanged,
        label = { Text("Cause") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AnimalWelfareFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.animalWelfareType,
        onValueChange = viewModel::onAnimalWelfareTypeChanged,
        label = { Text("Type of Welfare") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DisabledPersonsFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.amount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Donation Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    OutlinedTextField(
        value = formState.donorName,
        onValueChange = viewModel::onDonorNameChanged,
        label = { Text("Donor Name") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = formState.disabilitySupportType,
        onValueChange = viewModel::onDisabilitySupportTypeChanged,
        label = { Text("Support Type") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
    )
}






/*@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DisasterReliefSpecificFields(viewModel: DonationFormViewModel) {
    val formState = viewModel.donationFormState.value
    OutlinedTextField(
        value = formState.disasterType,
        onValueChange = viewModel::onDisasterLocationChanged,
        label = { Text("Disaster Location") },
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = formState.aidType,
        onValueChange = viewModel::onAidTypeChanged,
        label = { Text("Type of Aid") },
        modifier = Modifier.fillMaxWidth()
    )
}*/
