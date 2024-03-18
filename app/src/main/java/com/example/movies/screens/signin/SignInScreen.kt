package com.example.movies.screens.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.sign

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel
) {
    val uiState by signInViewModel.uiState

    Column () {
        TextField(
            value = uiState.email,
            onValueChange = { emailValue -> signInViewModel.updateEmailState(emailValue)},
        )
        TextField(
            value = uiState.password,
            onValueChange = { passwordValue -> signInViewModel.updatePasswordState(passwordValue)},
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = { signInViewModel.registerUser() }
        ) {
            Text(text = "Register")
        }

    }
}
