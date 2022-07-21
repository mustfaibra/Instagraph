package com.mustfaibra.instagraph.screens.search


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.models.SearchFilter
import com.mustfaibra.instagraph.utils.appendOrRemove
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val searchQuery = mutableStateOf("")
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

    fun updateSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    fun updateActiveFilters(id: Int){
        activeFilters.appendOrRemove(element = id)
    }
}