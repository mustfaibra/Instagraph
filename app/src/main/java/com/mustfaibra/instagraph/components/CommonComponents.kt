package com.mustfaibra.instagraph.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.UserSession
import com.mustfaibra.instagraph.models.Story
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.Screen
import com.mustfaibra.instagraph.ui.theme.BrightRed
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.Purple
import com.mustfaibra.instagraph.ui.theme.PurpleRed
import com.mustfaibra.instagraph.utils.getDp
import com.mustfaibra.instagraph.utils.mirror
import com.mustfaibra.instagraph.utils.myPlaceHolder

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.surface,
    requireSingleLine: Boolean = true,
    textShouldBeCentered: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MaterialTheme.shapes.small,
    padding: PaddingValues = PaddingValues(horizontal = Dimension.xs),
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (string: String) -> Unit,
    onFocusChange: (focused: Boolean) -> Unit,
    onKeyboardActionClicked: KeyboardActionScope.() -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current

    Row(
        modifier = modifier
            .clip(shape = shape)
            .background(backgroundColor)
            .padding(paddingValues = padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2))
    ) {
        leadingIcon()
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                },
            value = value,
            onValueChange = {
                if (it.length <= maxLength) {
                    /** when the value change and maxLength is not reached yet then pass it up **/
                    onValueChange(it)
                }
            },
            decorationBox = { container ->
                Box(
                    contentAlignment = if (textShouldBeCentered) Alignment.Center else Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = textColor.copy(alpha = 0.5f),
                            maxLines = if (requireSingleLine) 1 else Int.MAX_VALUE,
                        )
                    }
                    container()
                }
            },
            visualTransformation = visualTransformation,
            singleLine = requireSingleLine,
            textStyle = textStyle.copy(color = textColor),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusRequester.freeFocus()
                    /** It doesn't has the focus now, hide the input keyboard */
                    inputService?.hideSoftwareKeyboard()
                    onKeyboardActionClicked()
                }
            ),
            cursorBrush = SolidColor(value = textColor),
        )
        trailingIcon()
    }
}

@Composable
fun SecondaryTopBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    onBackClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(1.5f)),
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Transparent)
                .clickable {
                    onBackClicked()
                }
                .padding(Dimension.elevation)
                .mirror(),
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            tint = contentColor,
            contentDescription = "back",
        )
        Text(
            text = title,
            style = MaterialTheme.typography.button,
            color = contentColor,
            maxLines = 1,
        )
    }
}

@Composable
fun AppBottomNav(
    activeRoute: String,
    bottomNavDestinations: List<Screen>,
    backgroundColor: Color,
    onActiveRouteChange: (route: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = Dimension.xs, horizontal = Dimension.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        bottomNavDestinations.forEach {
            val isActive = activeRoute.equals(other = it.route, ignoreCase = true)
            AppBottomNavItem(
                modifier = Modifier.weight(1f),
                active = isActive,
                destination = it,
                onRouteClicked = {
                    if (!isActive) {
                        onActiveRouteChange(it.route)
                    }
                }
            )
        }
    }
}

@Composable
fun AppBottomNavItem(
    modifier: Modifier = Modifier,
    active: Boolean,
    destination: Screen,
    onRouteClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable(
                onClick = onRouteClicked,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(Dimension.xs),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .size(Dimension.xs)
                .clip(CircleShape)
                .background(
                    if (destination is Screen.Notifications && !active) BrightRed
                    else Color.Transparent
                )
        )
        Spacer(modifier = Modifier.height(Dimension.xs))
        if (destination is Screen.Profile) {
            /** If the destination is the profile screen, show user's profile image */
            Image(
                painter = painterResource(id = UserSession.user?.profile ?: R.drawable.ic_user),
                contentDescription = "post's owner image",
                modifier = Modifier
                    .size(Dimension.mdIcon.times(0.8f))
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        } else {
            /** If the destination is not the profile then just show it's icon */
            Icon(
                painter = painterResource(id = destination.icon ?: R.drawable.ic_home_empty),
                contentDescription = null,
                tint = if (active) MaterialTheme.colors.onBackground
                else MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.size(Dimension.mdIcon.times(0.8f)),
            )
        }
    }
}

@Composable
fun PostGridItem(
    modifier: Modifier = Modifier,
    cover: Int,
    imagesCount: Int,
    onPostClicked: () -> Unit,
) {
    Box(
        modifier = modifier.clickable { onPostClicked() },
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = cover),
            contentDescription = "post-cover",
            contentScale = ContentScale.Crop,
        )
        if (imagesCount > 1) {
            Icon(
                painter = painterResource(id = R.drawable.ic_group),
                contentDescription = "group",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Dimension.sm)
                    .size(Dimension.smIcon),
                tint = MaterialTheme.colors.background,
            )
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
                painter = rememberImagePainter(data = story.user.profile),
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
