package com.mustfaibra.instagraph.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.components.CustomInputField
import com.mustfaibra.instagraph.components.DrawableButton
import com.mustfaibra.instagraph.components.IconButton
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.providers.LocalNavHost
import com.mustfaibra.instagraph.ui.theme.Blue
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.Gray
import com.mustfaibra.instagraph.ui.theme.LightBlack
import com.mustfaibra.instagraph.utils.getDp

@Composable
fun ChatsScreen(
    chatsViewModel: ChatsViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        chatsViewModel.getMyChats()
        chatsViewModel.getOnlineUsers()
    }

    val navController = LocalNavHost.current
    val loggedUser by remember { chatsViewModel.user }
    val uiState by remember { chatsViewModel.chatsUiState }
    val searchQuery by remember { chatsViewModel.searchQuery }
    val chats = chatsViewModel.filteredChats
    val onlineUsers = chatsViewModel.onlineUsers

    loggedUser?.let { user ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            /** Chats top bar */
            item {
                ChatsTopBar(
                    userName = user.userName,
                    onBackClicked = { navController.popBackStack() },
                    onUserNameClicked = {},
                    onVideoChatClicked = {},
                    onNewMessageClicked = {},
                )
                Spacer(modifier = Modifier.height(Dimension.pagePadding))
            }
            /** Online users section if they are exist */
            if (onlineUsers.isNotEmpty()) {
                item {
                    OnlineUsersSection(
                        users = onlineUsers,
                        onUserClicked = {},
                    )
                    Spacer(modifier = Modifier.height(Dimension.pagePadding))
                }
            }
            /** Search input in the top of the screen */
            item {
                CustomInputField(
                    modifier = Modifier
                        .padding(horizontal = Dimension.pagePadding)
                        .fillMaxWidth(),
                    value = searchQuery,
                    placeholder = "Search",
                    requireSingleLine = true,
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
                        chatsViewModel.updateSearchQuery(query = it)
                    },
                    onFocusChange = {
                        /** Handle the event of input focus state change */
                    },
                    onKeyboardActionClicked = {
                        /** Do whatever you want when the keyboard action got clicked */
                    },
                )
                Spacer(modifier = Modifier.height(Dimension.pagePadding))
            }
            /** Header section */
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimension.pagePadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Messages",
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    )
                    Text(
                        text = "Requests",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Blue
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(Dimension.pagePadding))
            }

            /** Chats list section */
            items(chats) { chat ->
                ChatItemLayout(
                    userProfile = chat.otherUser.profile,
                    userName = chat.otherUser.name ?: "User name",
                    lastMessage = chat.lastMessage.body,
                    lastMessageTime = chat.lastMessage.time,
                    onChatClicked = {

                    },
                    onTakePictureClicked = {},
                )
            }
        }
    }
}

@Composable
fun ChatsTopBar(
    userName: String,
    onBackClicked: () -> Unit,
    onUserNameClicked: () -> Unit,
    onVideoChatClicked: () -> Unit,
    onNewMessageClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimension.pagePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /** Back icon */
            IconButton(
                icon = Icons.Rounded.ArrowBack,
                onButtonClicked = onBackClicked,
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onBackground,
                iconSize = Dimension.smIcon,
                shape = CircleShape,
            )
            /** Logged user userName section */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimension.xs),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onUserNameClicked() }
                    .padding(Dimension.xs),
            ) {
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
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        ) {
            DrawableButton(
                painter = painterResource(id = R.drawable.ic_video_call),
                onButtonClicked = onVideoChatClicked,
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onBackground,
                iconSize = Dimension.smIcon,
                shape = CircleShape,
            )
            IconButton(
                icon = Icons.Rounded.Add,
                onButtonClicked = onNewMessageClicked,
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onBackground,
                iconSize = Dimension.smIcon,
                shape = CircleShape,
            )
        }
    }
}


@Composable
fun OnlineUsersSection(users: List<User>, onUserClicked: () -> Unit) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
        contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
    ) {
        items(users) { user ->
            OnlineUserLayout(
                userProfile = user.profile,
                userName = user.name ?: user.userName,
                onUserClicked = {},
            )
        }
    }
}

@Composable
fun OnlineUserLayout(
    userProfile: Int,
    userName: String,
    onUserClicked: () -> Unit,
) {
    /** We must ensure that the very long username shouldn't mess the UI and should be clipped */
    var itemSize by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onUserClicked()
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
                    itemSize = it.size.width
                }
        ) {
            /** Then the story's image */
            Image(
                painter = rememberImagePainter(data = userProfile),
                contentDescription = "user image",
                modifier = Modifier
                    .size(Dimension.xlIcon.times(0.6f))
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .size(Dimension.md)
                    .clip(CircleShape)
                    .background(Green)
                    .align(Alignment.BottomEnd),
            )
        }
        Text(
            modifier = Modifier.width(itemSize.getDp()),
            overflow = TextOverflow.Clip,
            maxLines = 1,
            text = userName,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
        )
    }
}

@Composable
fun ChatItemLayout(
    userProfile: Int,
    userName: String,
    lastMessage: String,
    lastMessageTime: String,
    onChatClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .clickable { onChatClicked() }
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        /** First the chat's owner profile */
        Image(
            painter = rememberImagePainter(data = userProfile),
            contentDescription = "user image",
            modifier = Modifier
                .size(Dimension.lgIcon)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        /** User's name and last message section */
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.pagePadding)
                .weight(1f)
        ) {
            Text(text = userName, style = MaterialTheme.typography.subtitle1)
            Text(text = lastMessage,
                style = MaterialTheme.typography.subtitle2.copy(color = LightBlack))
        }
        DrawableButton(
            painter = painterResource(id = R.drawable.ic_camera),
            onButtonClicked = onTakePictureClicked,
            backgroundColor = MaterialTheme.colors.background,
            iconTint = LightBlack,
            iconSize = Dimension.smIcon,
            shape = CircleShape,
        )
    }
}
