package com.mustfaibra.instagraph.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.ui.theme.Dimension

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getStories()
        homeViewModel.getPosts()
    }
    val storiesUiState by remember { homeViewModel.storiesUiState }
    val stories = homeViewModel.stories

    val postsUiState by remember { homeViewModel.postsUiState }
    val posts = homeViewModel.posts

    val likedPosts = homeViewModel.likedPostsIds
    val bookmarkedPosts = homeViewModel.bookmarkedPostsIds

    /** Actual Home's UI */
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding)
    ) {
        /**  First the top bar */
        stickyHeader {
            HomeTopBar()
        }
        item {
            /** Then the stories section, my favorite part actually  */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(Dimension.xs),
            ) {
                Divider()
                StoriesSection(
                    stories = stories,
                    uiState = storiesUiState,
                    onStoryClicked = {

                    },
                )
                Divider()
            }
        }
        /** Now it's the time to show the posts of the home */
        when (postsUiState) {
            is UiState.Loading -> {
                /** We should show the placeholder of the posts */
                items(8) {
                    PostItemLayoutPH()
                }
            }
            is UiState.Success -> {
                /** Show the list of the posts */
                items(posts) { post ->
                    PostItemLayout(
                        ownerName = post.user.userName,
                        ownerProfile = post.user.profile,
                        location = post.location,
                        images = post.images,
                        recentReactUser = post.reacts.recentUser,
                        reactsCount = post.reacts.othersCount,
                        caption = post.caption,
                        date = post.date,
                        postLiked = post.id in likedPosts,
                        postBookmarked = post.id in bookmarkedPosts,
                        onMenuExpandChange = {},
                        onPostLikeChange = {
                            homeViewModel.updateLikedPosts(id = post.id)
                        },
                        onPostBookmarkChange = {
                            homeViewModel.updateBookmarkedPosts(id = post.id)
                        },
                    )
                }
            }
            else -> {
                /** Handling the error state here */
            }
        }
    }
}
