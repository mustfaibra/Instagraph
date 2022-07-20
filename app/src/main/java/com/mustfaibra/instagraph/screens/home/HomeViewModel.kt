package com.mustfaibra.instagraph.screens.home


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.repositories.StoryRepository
import com.mustfaibra.instagraph.sealed.DataResponse
import com.mustfaibra.instagraph.sealed.Error
import com.mustfaibra.instagraph.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
) : ViewModel(){
    val storiesUiState = mutableStateOf<UiState>(UiState.Idle)
    val uiState = mutableStateOf<UiState>(UiState.Idle)
    val stories: MutableList<Story> = mutableStateListOf()

    fun getStories() {
        if(storiesUiState.value is UiState.Success) return

        viewModelScope.launch {
            /** Getting the stories from the fake repository */
            storyRepository.getStories().let { response ->
                when (response) {
                    is DataResponse.Success -> {
                        /** Got stories successfully */
                        storiesUiState.value = UiState.Success
                        response.data?.let { responseStories ->
                            stories.addAll(responseStories)
                        }
                    }
                    else -> {
                        /** Failed to get the stories, we should inform the UI */
                        storiesUiState.value = UiState.Error(error = response.error ?: Error.Network)
                    }
                }
            }
        }
    }
}