package com.example.movies.screens.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.model.MovieUser

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null
)

class SignInViewModel(
    private val _authRepository: AuthRepository
): ViewModel() {
    //region STATE
    var uiState = mutableStateOf(SignInUiState())
        private set

    lateinit var navigateOnSignIn: () -> Unit

    fun updatePasswordState(newValue: String) {
       uiState.value = uiState.value.copy (password = newValue)
    }

    fun updateEmailState(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue.trim())
    }


    //endregion



    private fun validEmailAndPassword(): Boolean {
        if(uiState.value.email.isBlank()){
            uiState.value = uiState.value.copy(
                errorMessage = "Please enter a valid email."
            )
            return false;
        }
        else if(uiState.value.password.length < 6) {
            uiState.value = uiState.value.copy(
                errorMessage = "Please enter a password at least 6 characters in length."
            )
            return false
        } else {
            return true
        }
    }

    fun registerUser() {
        try {
            if(validEmailAndPassword()) {
                _authRepository.createAccount(
                    uiState.value.email,
                    uiState.value.password.trim()
                ) {
                    throwable ->
                    if(throwable != null) {
                        uiState.value = uiState.value.copy(
                            errorMessage =  throwable.message
                                ?: "Something went wrong registering your account."
                        )
                    } else {
                        authenticateUser()
                    }
                }
            }
        } catch(e: Exception) {
            uiState.value = uiState.value.copy(
                errorMessage =  e.message ?: "Something went wrong registering your account.")
        }
    }

    fun authenticateUser() {
        try {
            if(validEmailAndPassword()) {
                _authRepository.authenticate(
                    uiState.value.email,
                    uiState.value.password
                ) {
                    throwable ->
                    if(throwable != null) {
                        uiState.value = uiState.value.copy(
                            errorMessage =  throwable.message
                                ?: "Something went wrong registering your account."
                        )
                    } else {
                        _authRepository.getCurrentUser()
                        navigateOnSignIn()
                    }

                }
            }
        } catch(e: Exception) {
            uiState.value = uiState.value.copy(
                errorMessage =  e.message ?: "Something went wrong logging into your account.")
        }
    }

    fun logout() {
        _authRepository.logoutUser()
    }
    //endregion


    //region COMPANION
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val authRepository = application.container.authRepository
                SignInViewModel(
                    _authRepository = authRepository
                )
            }
        }
    }

    //endregion
}