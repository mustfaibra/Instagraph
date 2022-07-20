package com.mustfaibra.instagraph.sealed

import com.mustfaibra.instagraph.sealed.Error as ErrorBody

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    class Error(val error: ErrorBody) : UiState()
}
