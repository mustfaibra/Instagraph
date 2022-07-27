package com.mustfaibra.instagraph.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.instagraph.components.CustomButton
import com.mustfaibra.instagraph.components.IconButton
import com.mustfaibra.instagraph.components.PostGridItem
import com.mustfaibra.instagraph.models.Featured
import com.mustfaibra.instagraph.providers.LocalNavHost
import com.mustfaibra.instagraph.sealed.Tab
import com.mustfaibra.instagraph.ui.theme.BrightRed
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.LightBlack
import com.mustfaibra.instagraph.ui.theme.Purple
import com.mustfaibra.instagraph.ui.theme.PurpleRed
import com.mustfaibra.instagraph.utils.getDp

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        profileViewModel.getUserDetails()
        profileViewModel.getFakeFeatured()
    }
    val loggedUser by remember { profileViewModel.user }
    val activeTab by remember { profileViewModel.activeTab }
    val featuredStories = profileViewModel.featuredStories

    loggedUser?.let { user ->
        LazyVerticalGrid(columns = GridCells.Fixed(count = 3)) {
            /** first we put the sticky top bar */
            item(
                span = {
                    GridItemSpan(3)
                }
            ) {
                ProfileStickyToolbar(
                    userName = user.userName,
                    onUserNameClicked = {},
                    onMenuClicked = {},
                )
            }
            /** Then the user's profile info with stats */
            item(
                span = {
                    GridItemSpan(3)
                }
            ) {
                UserInfoWithStats(
                    modifier = Modifier.padding(vertical = Dimension.pagePadding),
                    profile = user.profile,
                    name = user.name,
                    bio = user.bio,
                    postsCount = user.posts.size,
                    followersCount = user.followers.size,
                    followingCount = user.following.size,
                )
            }
            /** Now to the stories that I featured on my profile */
            item(
                span = {
                    GridItemSpan(3)
                }
            ) {
                FeaturedStoriesSection(
                    featuredStories = featuredStories,
                    onAddNewFeaturedStoryClicked = {},
                    onFeaturedStoryClicked = {},
                )
            }
            /** Now the profile's tabs */
            item(
                span = {
                    GridItemSpan(3)
                }
            ) {
                ProfileTabSection(
                    tabs = listOf(Tab.Posts, Tab.Tags),
                    activeTab = activeTab,
                    onTabClicked = { newActiveTab ->
                        profileViewModel.updateActiveTab(newActiveTab = newActiveTab)
                    }
                )
            }
            /** We should decide what to show depending on the active tab : Posts or Tags */
            when (activeTab) {
                is Tab.Posts -> {
                    items(user.posts) { post ->
                        PostGridItem(
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                            cover = post.images.first(),
                            imagesCount = post.images.size
                        ) {
                            /** Handle click event on a post */
                        }
                    }
                }
                is Tab.Tags -> {}
            }
        }
    } ?: LocalNavHost.current.popBackStack()
}

@Composable
fun ProfileStickyToolbar(
    userName: String,
    onUserNameClicked: () -> Unit,
    onMenuClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(Dimension.pagePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(modifier = Modifier.size(Dimension.smIcon))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.xs),
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onUserNameClicked() }
                .padding(Dimension.xs),
        ){
            Icon(
                imageVector = Icons.Rounded.Lock,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Locked profile",
                modifier = Modifier.size(Dimension.smIcon.times(0.7f)),
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "dropdown icon"
            )
        }
        IconButton(
            icon = Icons.Rounded.Menu,
            onButtonClicked = onMenuClicked,
            backgroundColor = MaterialTheme.colors.background,
            iconTint = MaterialTheme.colors.onBackground,
            iconSize = Dimension.smIcon,
            shape = CircleShape,
        )
    }
}

@Composable
fun UserInfoWithStats(
    modifier: Modifier = Modifier,
    profile: Int,
    name: String?,
    bio: String?,
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        /** Profile image with stats row */
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.pagePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
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
                    .padding(Dimension.xs),
            ) {
                Image(
                    modifier = Modifier
                        .size(Dimension.xlIcon)
                        .clip(shape = CircleShape),
                    painter = painterResource(id = profile),
                    contentDescription = "user profile image"
                )
            }
            /** Stats data */
            StatItemLayout(
                title = "Posts",
                count = postsCount,
            )
            StatItemLayout(
                title = "Followers",
                count = followersCount,
            )
            StatItemLayout(
                title = "Following",
                count = followingCount,
            )
        }
        /** Now, name and bio section and edit profile button */
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.pagePadding)
                .fillMaxWidth(),
        ) {
            Text(
                text = name ?: "No name",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = bio ?: "*** No Bio ***",
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
                color = LightBlack,
            )
            CustomButton(
                modifier = Modifier
                    .padding(top = Dimension.pagePadding.div(2))
                    .fillMaxWidth(),
                buttonColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.small,
                padding = PaddingValues(vertical = Dimension.xs),
                borderStroke = BorderStroke(width = 1.dp,
                    color = MaterialTheme.colors.onBackground.copy(0.4f)),
                text = "Edit Profile",
                textStyle = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                onButtonClicked = {},
            )
        }
    }
}

@Composable
fun StatItemLayout(
    title: String,
    count: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$count",
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
            color = LightBlack.copy(alpha = 0.8f),
        )
    }
}

@Composable
fun FeaturedStoriesSection(
    featuredStories: List<Featured>? = null,
    onAddNewFeaturedStoryClicked: () -> Unit,
    onFeaturedStoryClicked: (featured: Featured) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = Dimension.pagePadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimension.xs),
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimension.xlIcon
                            .times(0.6f)
                            .plus(2.dp))
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(Purple, PurpleRed, BrightRed),
                                tileMode = TileMode.Clamp,
                            ),
                            shape = CircleShape,
                        )
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.background)
                        .clickable(
                            onClick = {
                                /** Handling the event of adding a new featured story option */
                                onAddNewFeaturedStoryClicked()
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.smIcon),
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
                Text(
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    text = "New",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
                )
            }
        }
        /** Now show the featured stories if it's not null */
        featuredStories?.let {
            items(featuredStories) { featured ->
                FeaturedStoryItemLayout(drawable = featured.images.first(),
                    title = featured.title) {
                    /** Handling the click event on a featured story */
                    onFeaturedStoryClicked(featured)
                }
            }
        }
    }
    Divider(
        modifier = Modifier.padding(top = Dimension.pagePadding.div(2))
    )
}

@Composable
fun FeaturedStoryItemLayout(
    drawable: Int,
    title: String,
    onStoryClicked: () -> Unit,
) {

    /** We must ensure that the very long username shouldn't mess the UI and should be clipped */
    var storySize by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onStoryClicked()
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimension.xs),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .onGloballyPositioned {
                    /** Here am gonna get the measurement that the parent box occupied */
                    storySize = it.size.width
                }
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
        ) {
            /** Then the story's image */
            Image(
                painter = rememberImagePainter(data = drawable),
                contentDescription = "story image",
                modifier = Modifier
                    .size(Dimension.xlIcon.times(0.6f))
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier.width(storySize.getDp()),
            overflow = TextOverflow.Clip,
            maxLines = 1,
            text = title,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ProfileTabSection(
    tabs: List<Tab>,
    activeTab: Tab,
    onTabClicked: (tab: Tab) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (tab in tabs) {
            TabItemLayout(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        /** If the clicked tab is not the active tab, pass the event up to update the the active tab */
                        if (tab != activeTab) onTabClicked(tab)
                    },
                icon = tab.icon,
                active = tab == activeTab,
            )
        }
    }
}

@Composable
fun TabItemLayout(modifier: Modifier = Modifier, icon: Int, active: Boolean) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .padding(vertical = Dimension.sm)
                .size(Dimension.smIcon),
            painter = painterResource(id = icon),
            contentDescription = "tab icon",
            tint = MaterialTheme.colors.onBackground,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(if (active) MaterialTheme.colors.onBackground else Color.Transparent)
        )
    }
}
