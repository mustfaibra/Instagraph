package com.mustfaibra.instagraph.screens.search


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.Post
import com.mustfaibra.instagraph.models.SearchFilter
import com.mustfaibra.instagraph.sealed.DataResponse
import com.mustfaibra.instagraph.sealed.Error
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.utils.appendOrRemove
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) : ViewModel() {
    val lastSearchQuery = mutableStateOf("")
    val filters = listOf(
        SearchFilter(
            id = 1,
            icon = R.drawable.ic_tv,
            name = R.string.igtv,
        ),
        SearchFilter(
            id = 2,
            icon = R.drawable.ic_shop_bag,
            name = R.string.shop,
        ),
        SearchFilter(
            id = 3,
            name = R.string.style,
        ),
        SearchFilter(
            id = 4,
            name = R.string.sports,
        ),
        SearchFilter(
            id = 5,
            name = R.string.auto,
        ),
        SearchFilter(
            id = 6,
            name = R.string.anime,
        ),
        SearchFilter(
            id = 7,
            name = R.string.culture,
        ),
        SearchFilter(
            id = 8,
            name = R.string.nature,
        ),
    )
    val activeFilters: MutableList<Int> = mutableStateListOf()
    val trendingPosts: MutableList<Post> = mutableStateListOf()
    val trendingPostsUiState = mutableStateOf<UiState>(UiState.Loading)

    init {
        getTrendingPosts()
    }

    /** A function to update the query that the user is currently typing into the input field "the state" */
    fun updateSearchQuery(query: String) {
        this.lastSearchQuery.value = query
    }

    /** The function that actually make the search process */
    fun searchForPosts(query: String) {

    }

    fun updateActiveFilters(id: Int) {
        activeFilters.appendOrRemove(element = id)
    }

    private fun getTrendingPosts() {
        trendingPostsUiState.value = UiState.Loading
        viewModelScope.launch {
            fakeServicesImpl.getFakePosts().let { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Success -> {
                        /** Fetched successfully */
                        trendingPostsUiState.value = UiState.Success
                        dataResponse.data?.let {
                            trendingPosts.addAll(it)
                        }
                    }
                    else -> {
                        /** Fetching failed unfortunately */
                        trendingPostsUiState.value =
                            UiState.Error(error = dataResponse.error ?: Error.Network)
                    }
                }
            }
        }
    }
}