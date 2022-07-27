package com.mustfaibra.instagraph.screens.chats


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.instagraph.UserSession
import com.mustfaibra.instagraph.models.Chat
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.repositories.ChatsRepository
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
class ChatsViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository,
) : ViewModel() {
    val chatsUiState = mutableStateOf<UiState>(UiState.Loading)
    val user = mutableStateOf<User?>(null)
    val allChats: MutableList<Chat> = mutableStateListOf()
    val filteredChats: MutableList<Chat> = mutableStateListOf()
    val onlineUsers: MutableList<User> = mutableStateListOf()
    val onlineUsersUiState = mutableStateOf<UiState>(UiState.Loading)
    val searchQuery = mutableStateOf("")

    init {
        user.value = UserSession.user
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
        filteredChats.clear().also {
            filteredChats.addAll(allChats.filter { it.otherUser.name?.startsWith(query,true) == true })
        }
    }

    fun getMyChats() {
        if (allChats.isNotEmpty()) return
        chatsUiState.value = UiState.Loading
        viewModelScope.launch {
            chatsRepository.getFakeChats().let { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Success -> {
                        /** Got a response from the server successfully */
                        chatsUiState.value = UiState.Success
                        dataResponse.data?.let {
                            allChats.addAll(it)
                            filteredChats.addAll(it)
                        }
                    }
                    is DataResponse.Error -> {
                        /** An error happened when fetching data from the server */
                        chatsUiState.value =
                            UiState.Error(error = dataResponse.error ?: Error.Network)
                    }
                }
            }
        }
    }

    fun getOnlineUsers() {
        if (onlineUsers.isNotEmpty()) return
        onlineUsersUiState.value = UiState.Loading
        viewModelScope.launch {
            chatsRepository.getFakeOnlineUsers().let { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Success -> {
                        /** Got a response from the server successfully */
                        onlineUsersUiState.value = UiState.Success
                        dataResponse.data?.let {
                            onlineUsers.addAll(it)
                        }
                    }
                    is DataResponse.Error -> {
                        /** An error happened when fetching data from the server */
                        onlineUsersUiState.value =
                            UiState.Error(error = dataResponse.error ?: Error.Network)
                    }
                }
            }
        }
    }
}