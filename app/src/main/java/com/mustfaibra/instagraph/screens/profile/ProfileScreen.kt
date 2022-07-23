package com.mustfaibra.instagraph.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.instagraph.components.IconButton
import com.mustfaibra.instagraph.providers.LocalNavHost

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by remember { profileViewModel.uiState }
    val loggedUser = profileViewModel.user
    loggedUser?.let { user ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            /** first we put the sticky top bar */
            stickyHeader {
                ProfileStickyToolbar(userName = user.userName)
            }
        }
    } ?: LocalNavHost.current.popBackStack()
}

@Composable
fun ProfileStickyToolbar(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            icon = Icons.Rounded.KeyboardArrowLeft,
            onButtonClicked = {},
            backgroundColor = MaterialTheme.colors.background,
            iconTint = MaterialTheme.colors.onBackground,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.Lock,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Locked profile"
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
            onButtonClicked = {},
            backgroundColor = MaterialTheme.colors.background,
            iconTint = MaterialTheme.colors.onBackground,
        )
    }
}
