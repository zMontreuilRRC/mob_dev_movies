package com.example.movies.screens.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.sign

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel
) {
    val uiState by signInViewModel.uiState

    Column (
        modifier = Modifier.padding(50.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Email", fontSize = 25.sp)
        TextField(
            modifier = Modifier.padding(0.dp, 25.dp),
            value = uiState.email,
            onValueChange = { emailValue -> signInViewModel.updateEmailState(emailValue)},
            textStyle = TextStyle(fontSize = 25.sp)
        )
        Text("Password", fontSize = 25.sp)
        TextField(
            modifier = Modifier.padding(0.dp, 25.dp),
            value = uiState.password,
            onValueChange = { passwordValue -> signInViewModel.updatePasswordState(passwordValue)},
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 25.sp)
        )
        Button(
            modifier = Modifier.size(width = 250.dp, height = 75.dp).
                paddingFromBaseline(0.dp, 25.dp),
            onClick = { /* TODO */ }
        ) {
            Text(
                "Log In",
                fontSize = 30.sp
            )
        }
        Button(
            modifier = Modifier.size(width = 200.dp, height = 75.dp)
                .paddingFromBaseline(0.dp, 25.dp),
            onClick = { signInViewModel.registerUser() }
        ) {
            Text(
                "Register",
                fontSize = 30.sp
            )
        }
    }
}
