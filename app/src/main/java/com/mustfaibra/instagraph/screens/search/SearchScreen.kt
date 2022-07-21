package com.mustfaibra.instagraph.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.components.CustomInputField
import com.mustfaibra.instagraph.components.DrawableButton
import com.mustfaibra.instagraph.components.LazyStaggeredGrid
import com.mustfaibra.instagraph.components.PostGridItem
import com.mustfaibra.instagraph.ui.theme.Blue
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.Gray
import timber.log.Timber

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.sm),
    ) {
        val searchQuery by remember { searchViewModel.lastSearchQuery }
        val searchFilters = remember { searchViewModel.filters }
        val activeFilters = searchViewModel.activeFilters
        val trendingPosts = searchViewModel.trendingPosts
        val trendingPostsUiState = searchViewModel.trendingPostsUiState

        /** The search input at the top of the screen */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.pagePadding.div(2)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
        ) {
            CustomInputField(
                modifier = Modifier.weight(1f),
                value = searchQuery,
                placeholder = "Search",
                textStyle = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                imeAction = ImeAction.Search,
                backgroundColor = Gray,
                padding = PaddingValues(Dimension.pagePadding.div(2)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search icon",
                        modifier = Modifier.size(Dimension.smIcon.times(0.7f)),
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
                    )
                },
                onValueChange = {
                    /** What happens when the input value changes */
                },
                onFocusChange = {
                    /** Handle the event of input focus state change */
                },
                onKeyboardActionClicked = {
                    /** Do whatever you want when the keyboard action got clicked */
                },
            )
            /** Then the qr icon button */
            DrawableButton(
                painter = painterResource(id = R.drawable.ic_scan),
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
                onButtonClicked = {
                    /** Handle the event of the qr code icon got clicked */
                },
                iconSize = Dimension.smIcon
            )
        }

        /** The filtering options under the input field */
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = Dimension.pagePadding.div(2))
        ) {
            items(searchFilters) { filter ->
                SearchFilterLayout(
                    name = stringResource(id = filter.name),
                    icon = filter.icon,
                    isActive = activeFilters.contains(filter.id),
                    onActiveChange = {
                        searchViewModel.updateActiveFilters(id = filter.id)
                    },
                )
            }
        }
        /** Then the images grids */
        LazyStaggeredGrid(
            columnCount = 3,
            contentPadding = PaddingValues(1.dp)
        ) {
            trendingPosts.shuffled().forEach {
                item {
                    PostGridItem(
                        modifier = Modifier.padding(1.dp).fillMaxWidth(),
                        cover = it.images.first(),
                        imagesCount = it.images.size,
                        onPostClicked = {

                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SearchFilterLayout(name: String, icon: Int?, isActive: Boolean, onActiveChange: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(
                if (isActive) Blue
                else MaterialTheme.colors.background
            )
            .border(
                width = 1.dp,
                color = if (isActive) Blue
                else MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
            .clickable(
                onClick = onActiveChange,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(horizontal = Dimension.pagePadding.div(2), vertical = Dimension.xs),
        horizontalArrangement = Arrangement.spacedBy(Dimension.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = "search filter",
                modifier = Modifier.size(Dimension.smIcon.times(0.7f)),
                tint = if (isActive) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground.copy(
                    alpha = 0.8f),
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
            color = if (isActive) MaterialTheme.colors.onPrimary
            else MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
        )
    }
}
