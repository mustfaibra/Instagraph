package com.mustfaibra.instagraph.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.UserSession
import com.mustfaibra.instagraph.components.DrawableButton
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.ui.theme.Blue
import com.mustfaibra.instagraph.ui.theme.BrightRed
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.LightBlack
import com.mustfaibra.instagraph.ui.theme.Purple
import com.mustfaibra.instagraph.ui.theme.PurpleRed
import com.mustfaibra.instagraph.utils.getDp
import com.mustfaibra.instagraph.utils.myPlaceHolder


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
            Box {
                DrawableButton(
                    painter = painterResource(id = R.drawable.ic_tv),
                    iconSize = Dimension.smIcon,
                    shape = CircleShape,
                    iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                    backgroundColor = Color.Transparent,
                    onButtonClicked = {

                    }
                )
                Box(
                    modifier = Modifier
                        .size(Dimension.xs)
                        .clip(CircleShape)
                        .background(BrightRed)
                        .align(Alignment.TopEnd)
                )
            }
            /** Chats button */
            Box {
                DrawableButton(
                    painter = painterResource(id = R.drawable.ic_send),
                    iconSize = Dimension.smIcon,
                    shape = CircleShape,
                    iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                    backgroundColor = Color.Transparent,
                    onButtonClicked = {

                    }
                )
                Box(
                    modifier = Modifier
                        .size(Dimension.md)
                        .clip(CircleShape)
                        .background(BrightRed)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "+9",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

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
        contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
    ) {
        item {
            Column(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            /** Handling the event of adding a new story option */
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimension.xs),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    /** Then the story's image */
                    Image(
                        painter = painterResource(id = UserSession.user?.profile
                            ?: R.drawable.ic_user),
                        contentDescription = "user's story",
                        modifier = Modifier
                            .padding(Dimension.xs)
                            .size(Dimension.xlIcon
                                .times(0.6f)
                                .plus(2.dp))
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Blue)
                            .padding(Dimension.xs)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.md),
                            tint = MaterialTheme.colors.background,
                        )
                    }
                }
                Text(
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    text = "Your story",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
                )
            }
        }
        when (uiState) {
            is UiState.Loading -> {
                /** We should show the placeholder of the stories */
                items(6) {
                    StoryItemLayoutPH()
                }
            }
            is UiState.Success -> {
                /** We should show the list of our stories */
                items(stories) { story ->
                    StoryItemLayout(
                        story = story,
                        onStoryClicked = {
                            onStoryClicked(story)
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

@Composable
fun StoryItemLayout(
    story: Story,
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
                painter = rememberImagePainter(data = story.url),
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
            text = story.user.userName,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
        )
    }
}

@Composable
fun StoryItemLayoutPH() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimension.sm),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                    shape = CircleShape,
                )
                .padding(Dimension.xs)
        ) {
            /** Then the fake story's image */
            Box(
                modifier = Modifier
                    .size(Dimension.xlIcon.times(0.6f))
                    .myPlaceHolder(
                        visible = true,
                        shape = CircleShape,
                    ),
            )
        }
        /** The fake story's owner username */
        Box(
            modifier = Modifier
                .width(Dimension.xlIcon.times(0.6f))
                .height(Dimension.sm)
                .myPlaceHolder(
                    visible = true,
                    shape = CircleShape,
                ),
        )
    }
}

@Composable
fun PostItemLayout(
    ownerName: String,
    ownerProfile: Int,
    location: String,
    images: List<Int>,
    recentReactUser: User,
    reactsCount: Int,
    caption: String,
    date: String,
    onMenuExpandChange: () -> Unit,
    onPostLikeChange: () -> Unit,
    onPostBookmarkChange: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(0.8f)),
    ) {
        /** Post's owner info header with menu option */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.pagePadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /** Post's writer info */
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(0.8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                /** First the owner's profile image */
                Image(
                    painter = rememberImagePainter(data = ownerProfile),
                    contentDescription = "story image",
                    modifier = Modifier
                        .size(Dimension.mdIcon)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Column {
                    Text(
                        text = ownerName,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontSize = MaterialTheme.typography.subtitle1.fontSize.times(0.9f),
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        text = location,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontSize = MaterialTheme.typography.subtitle1.fontSize.times(0.75f),
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                }
            }
            /** Option menu icon */
            DrawableButton(
                backgroundColor = MaterialTheme.colors.background,
                shape = CircleShape,
                iconSize = Dimension.smIcon,
                iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                painter = painterResource(id = R.drawable.ic_more_horizontal),
                onButtonClicked = onMenuExpandChange,
            )
        }
        /** Now, the exciting part, the post's image/images with the reactions */
        PostImagesWithReactions(
            images = images,
            onPostLikeChange = {},
            onPostBookmarkChange = {},
        )
        ReactsWithCaption(
            recentReactUser = recentReactUser,
            reactsCount = reactsCount,
            ownerName = ownerName,
            caption = caption,
            date = date,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostImagesWithReactions(
    images: List<Int>,
    onPostLikeChange: () -> Unit,
    onPostBookmarkChange: () -> Unit,
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.xs),
    ) {
        val imageModifier = if (images.size > 1) Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f) else Modifier.fillMaxWidth()
        Box(modifier = Modifier.fillMaxWidth()) {
            /** Horizontal pager section */
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                count = images.size,
                state = pagerState,
                itemSpacing = Dimension.zero,
            ) {
                Image(
                    painter = painterResource(id = images[this.currentPage]),
                    contentDescription = "post image",
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop,
                )
            }

            /** The image's count when the post contains more than images */
            if (images.size > 1) {
                Text(
                    text = "${pagerState.currentPage.inc()}/${images.size}",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(Dimension.pagePadding)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.onBackground.copy(alpha = 0.8f))
                        .padding(horizontal = Dimension.sm, vertical = Dimension.xs.div(2))
                        .align(Alignment.TopEnd),
                    color = MaterialTheme.colors.background,
                )
            }
        }
        /** Reaction on post actions : like comment, like, share */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.pagePadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimension.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DrawableButton(
                    backgroundColor = MaterialTheme.colors.background,
                    shape = CircleShape,
                    iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                    painter = painterResource(id = R.drawable.ic_heart),
                    onButtonClicked = onPostLikeChange,
                )
                DrawableButton(
                    backgroundColor = MaterialTheme.colors.background,
                    shape = CircleShape,
                    iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                    painter = painterResource(id = R.drawable.ic_message_circle),
                    onButtonClicked = { },
                )
                DrawableButton(
                    backgroundColor = MaterialTheme.colors.background,
                    shape = CircleShape,
                    iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                    painter = painterResource(id = R.drawable.ic_send),
                    onButtonClicked = { },
                )
            }
            /** Again, if there are many images, then show the pager indicators */
            if (images.size > 1) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = Dimension.pagePadding.times(2)),
                        horizontalArrangement = Arrangement.spacedBy(Dimension.xs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        items(pagerState.pageCount) { index ->
                            Box(
                                modifier = Modifier
                                    .size(
                                        if (pagerState.currentPage == index) Dimension.sm.times(0.7f)
                                        else Dimension.sm.times(0.6f)
                                    )
                                    .clip(CircleShape)
                                    .background(
                                        if (pagerState.currentPage == index) Blue
                                        else MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                                    )
                            )
                        }
                    }
                }
            }
            DrawableButton(
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                shape = CircleShape,
                painter = painterResource(id = R.drawable.ic_bookmark),
                onButtonClicked = onPostBookmarkChange,
            )
        }
    }
}

@Composable
fun ReactsWithCaption(
    recentReactUser: User,
    reactsCount: Int,
    ownerName: String,
    caption: String,
    date: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = Dimension.pagePadding),
    ) {
        /** Reacts and caption section */
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2))
        ) {
            Image(
                painter = painterResource(id = recentReactUser.profile),
                contentDescription = "post image",
                modifier = Modifier
                    .size(Dimension.mdIcon.times(0.7f))
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = buildAnnotatedString {
                    append("Liked by ")
                    withStyle(
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                        ).toSpanStyle()
                    ) {
                        append(recentReactUser.userName)
                    }
                    append(" and ")
                    withStyle(
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                        ).toSpanStyle()
                    ) {
                        append("$reactsCount")
                    }
                    append(" others")
                },
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.Normal,
                ),
            )
        }
        /** Now the caption */
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append(ownerName)
                }
                append("  ")
                append(caption)
            },
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        /** Now the post's date */
        Text(
            text = date,
            style = MaterialTheme.typography.subtitle2,
            color = LightBlack,
        )
    }
}

@Composable
fun PostItemLayoutPH() {

}