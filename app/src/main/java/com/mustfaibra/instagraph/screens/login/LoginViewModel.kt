package com.mustfaibra.instagraph.screens.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.instagraph.repositories.UserRepository
import com.mustfaibra.instagraph.sealed.DataResponse
import com.mustfaibra.instagraph.sealed.Error
import com.mustfaibra.instagraph.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)

    fun authenticateUser(onAuthenticated: () -> Unit, onAuthenticationFailed: () -> Unit) {
        uiState.value = UiState.Loading
        /** We will use the coroutine so that we don't block our dear : The UiThread */
        viewModelScope.launch {
            userRepository.signInUser(
                email = "mustaphathegreat@gmail.com",
                password = "fakepasswordyoudumb",
            ).let {
                when (it) {
                    is DataResponse.Success -> {
                        /** Authenticated successfully */
                        uiState.value = UiState.Success
                        onAuthenticated()
                    }
                    is DataResponse.Error -> {
                        /** An error occurred while authenticating */
                        uiState.value = UiState.Error(error = it.error ?: Error.Network)
                        onAuthenticationFailed()
                    }
                }
            }
        }
    }
}