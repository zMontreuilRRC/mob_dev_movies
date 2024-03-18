package com.example.movies.screens.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.screens.search.SearchViewModel

data class SignInUiState(
    val email: String = "",
    val password: String = ""
)
class SignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    var uiState = mutableStateOf(SignInUiState())
        private set

    lateinit var navigateOnSignIn: () -> Unit

    fun updatePasswordState(newValue: String) {
       uiState.value = uiState.value.copy (password = newValue)
    }

    fun updateEmailState(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun registerUser() {
        authRepository.createAccount(
            uiState.value.email,
            uiState.value.password) {
            navigateOnSignIn()
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val authRepository = application.container.authRepository
                SignInViewModel(authRepository = authRepository)
            }
        }
    }
}