package com.mustfaibra.instagraph.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.instagraph.components.CustomButton
import com.mustfaibra.instagraph.models.PostReacts
import com.mustfaibra.instagraph.sealed.NotificationType
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.LightBlack
import com.mustfaibra.instagraph.ui.theme.SkyBlue
import com.mustfaibra.instagraph.utils.getFormattedDate
import com.mustfaibra.instagraph.utils.myPlaceHolder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        notificationViewModel.getMyNotifications()
    }

    val uiState by remember { notificationViewModel.uiState }
    val notifications = notificationViewModel.notifications
    val todayDateAsString = notificationViewModel.todayDateAsLong.getFormattedDate("dd-MM-yyyy")

    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background),
    ) {
        when (uiState) {
            is UiState.Idle -> {
                /** It's idle now */
            }
            is UiState.Loading -> {
                /** Still loading, show the placeholder */
                items(8) { NotificationItemPH() }
            }
            is UiState.Success -> {
                /** Loading finished successfully ! */
                /** First we put the show follow requests option */
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.background)
                            .clickable { }
                            .padding(Dimension.pagePadding),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Follow Requests",
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f)
                        )
                    }
                    Divider(modifier = Modifier.padding(bottom = Dimension.pagePadding))
                }
                /** then we show the notifications after grouping it by the day of the notification */
                val groupedNotification = notifications
                    .groupBy {
                        it.time
                            .getFormattedDate("dd-MM-yyyy HH:mm")
                            .split(" ")
                            .first()
                    }
                /** Then iterate through each day notifications */
                for (day in groupedNotification) {
                    val notificationDay = day.key
                    val isItToday = notificationDay == todayDateAsString

                    /** Header can be "Today" or "This week" */
                    val notificationDayHeader =
                        if (isItToday) "Today" else "This Week"

                    stickyHeader {
                        Text(
                            modifier = Modifier.padding(start = Dimension.pagePadding),
                            text = notificationDayHeader,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                    items(day.value) { notification ->
                        val notificationTime = if (isItToday) {
                            notification.time
                                .getFormattedDate("dd-MM-yyyy HH:mm")
                                .split(" ").last()
                        } else {
                            notification.time
                                .getFormattedDate("EEEE HH:mm")
                        }

                        NotificationItemLayout(
                            notificationId = notification.id,
                            type = notification.type,
                            time = notificationTime,
                            onNotificationClicked = {

                            }
                        )
                    }
                    /** Then the divider between the different days */
                    item {
                        Divider(
                            modifier = Modifier.padding(vertical = Dimension.pagePadding)
                        )
                    }
                }
            }
            is UiState.Error -> {
                /** An error occur, show the message you want to tell the user */
            }
        }
    }
}

@Composable
fun NotificationItemLayout(
    notificationId: Int,
    type: NotificationType,
    time: String,
    onNotificationClicked: () -> Unit,
) {
    /** Now should show a dynamic notification's UI depending on the notification's type */
    when (type) {
        is NotificationType.FollowNotification -> {
            var fakeFollowState by remember { mutableStateOf(type.followed) }

            FollowNotificationUI(
                userName = type.user.userName,
                userProfile = type.user.profile,
                message = " started following you.",
                followed = fakeFollowState,
                time = time,
                onFollowClick = {
                    fakeFollowState = !fakeFollowState
                },
                onNotificationClicked = onNotificationClicked,
            )
        }
        is NotificationType.ReactsNotification -> {
            ReactNotificationUI(
                postId = type.postId,
                postCoverUrl = type.postCoverUrl,
                postReacts = type.postReacts,
                time = time,
                onNotificationClicked = onNotificationClicked,
            )
        }
        is NotificationType.MentionNotification -> {
            MentionNotificationUI(
                userName = type.user.userName,
                userProfile = type.user.profile,
                postId = type.postId,
                commentId = type.commentId,
                comment = type.comment,
                time = time,
                onNotificationClicked = onNotificationClicked,
            )
        }
    }
}

@Composable
fun FollowNotificationUI(
    userName: String,
    userProfile: Int,
    message: String,
    followed: Boolean,
    time: String,
    onFollowClick: () -> Unit,
    onNotificationClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNotificationClicked() }
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = userProfile),
            contentDescription = "image",
            modifier = Modifier
                .size(Dimension.lgIcon)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimension.pagePadding)
                .weight(1f),
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append(userName)
                }
                append(message)
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Normal,
                        color = LightBlack,
                    ).toSpanStyle()
                ) {
                    append(" $time")
                }
            },
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        /** Embedded follow button */
        CustomButton(
            shape = MaterialTheme.shapes.small,
            borderStroke = BorderStroke(
                width = if (followed) Dimension.zero else 1.dp,
                color = LightBlack
            ),
            padding = PaddingValues(
                horizontal = Dimension.pagePadding.div(2),
            ),
            buttonColor = if (followed) SkyBlue else MaterialTheme.colors.background,
            contentColor = if (followed) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
            text = if (followed) "Message" else "Follow",
            textStyle = MaterialTheme.typography.subtitle2.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            ),
            onButtonClicked = onFollowClick,
        )
    }
}

@Composable
fun ReactNotificationUI(
    postId: Int,
    postCoverUrl: Int,
    postReacts: PostReacts,
    time: String,
    onNotificationClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNotificationClicked() }
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = postReacts.recentUser.profile),
            contentDescription = "image",
            modifier = Modifier
                .size(Dimension.lgIcon)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimension.pagePadding)
                .weight(1f),
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append(postReacts.recentUser.userName)
                }
                append(" and ")
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append("${postReacts.othersCount} others ")
                }
                append("liked your photo.")
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Normal,
                        color = LightBlack,
                    ).toSpanStyle()
                ) {
                    append(" $time")
                }
            },
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        /** Liked post's photo */
        Image(
            painter = painterResource(id = postCoverUrl),
            contentDescription = "image",
            modifier = Modifier
                .size(Dimension.lgIcon),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun MentionNotificationUI(
    userName: String,
    userProfile: Int,
    postId: Int,
    commentId: Int,
    comment: String,
    time: String,
    onNotificationClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNotificationClicked() }
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = userProfile),
            contentDescription = "image",
            modifier = Modifier
                .size(Dimension.lgIcon)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(Dimension.pagePadding))
        Text(
            modifier = Modifier
                .weight(1f),
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append(userName)
                }
                /** Notification's body */
                append(" Mentioned you in a comment: ")
                /** the comment overview */
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    ).toSpanStyle()
                ) {
                    append(comment)
                }
                /** Now we add the time */
                withStyle(
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Normal,
                        color = LightBlack,
                    ).toSpanStyle()
                ) {
                    append(" $time")
                }
            },
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Normal,
            ),
        )

    }
}

@Composable
fun NotificationItemPH() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimension.lgIcon)
                .myPlaceHolder(visible = true, shape = CircleShape),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = Dimension.pagePadding)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimension.xs)
        ) {
            for (x in 0..1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimension.sm)
                        .myPlaceHolder(visible = true, shape = CircleShape)
                )
            }
        }
        /** Liked post's photo */
        Box(
            modifier = Modifier
                .size(Dimension.lgIcon)
                .myPlaceHolder(visible = true),
        )
    }
}
