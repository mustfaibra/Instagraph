package com.mustfaibra.instagraph.screens.profile


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mustfaibra.instagraph.UserSession
import com.mustfaibra.instagraph.repositories.UserRepository
import com.mustfaibra.instagraph.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)
    val user = UserSession.user
}