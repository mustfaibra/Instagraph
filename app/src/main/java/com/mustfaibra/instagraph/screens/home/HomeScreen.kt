package com.mustfaibra.instagraph.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.components.DrawableButton
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.ui.theme.BrightRed
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.Purple
import com.mustfaibra.instagraph.ui.theme.PurpleRed

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding)
    ) {
        LaunchedEffect(key1 = Unit){
            homeViewModel.getStories()
        }
        val storiesUiState by remember { homeViewModel.storiesUiState }
        val uiState by remember { homeViewModel.uiState }
        val stories = homeViewModel.stories
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {}
            is UiState.Error -> {}
        }

        /** Actual Home's UI , First the top bar*/
        HomeTopBar()
        /** Then the stories section, my favorite part actually  */
        StoriesSection(
            stories = stories,
            uiState = uiState,
            onStoryClicked = {

            },
        )
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimension.pagePadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DrawableButton(
            painter = painterResource(id = R.drawable.ic_camera),
            iconSize = Dimension.smIcon,
            shape = CircleShape,
            iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            backgroundColor = Color.Transparent,
            onButtonClicked = {

            }
        )
//        Image(
//            painter = painterResource(id = R.drawable.ic_instagram_logo_word),
//            contentDescription = "logo word",
//            modifier = Modifier.size(Dimension.xlIcon),
//        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        ) {
            DrawableButton(
                painter = painterResource(id = R.drawable.ic_tv),
                iconSize = Dimension.smIcon,
                shape = CircleShape,
                iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                backgroundColor = Color.Transparent,
                onButtonClicked = {

                }
            )
            DrawableButton(
                painter = painterResource(id = R.drawable.ic_send),
                iconSize = Dimension.smIcon,
                shape = CircleShape,
                iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                backgroundColor = Color.Transparent,
                onButtonClicked = {

                }
            )
        }
    }
    Divider()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoriesSection(
    stories: List<Story>,
    uiState: UiState,
    onStoryClicked: (story: Story) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ){
        stickyHeader {

        }
        items(stories){story->
            StoryItemLayout(
                story = story,
                onStoryClicked = {

                },
            )
        }
    }
}

@Composable
fun StoryItemLayout(
    story: Story,
    onStoryClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.clip(CircleShape).clickable { onStoryClicked() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimension.xs),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(Purple, PurpleRed, BrightRed),
                        tileMode = TileMode.Clamp,
                    ),
                    shape = CircleShape,
                )
                .padding(Dimension.xs)
        ){
            /** Then the story's image */
            Image(
                painter = rememberImagePainter(data = story.url),
                contentDescription = "story image",
                modifier = Modifier
                    .size(Dimension.xlIcon.times(0.5f))
                    .clip(CircleShape)
            )
        }
    }
}