package com.mustfaibra.instagraph.sealed

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mustfaibra.instagraph.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int? = null,
) {
    object Splash : Screen(route = "splash")
    object Login : Screen(route = "login")
    object Home : Screen(route = "home", title = R.string.home, icon = R.drawable.ic_home_empty)
    object Search : Screen(route = "search", title = R.string.search, icon = R.drawable.ic_search)
    object AddPost : Screen(route = "add_post", title = R.string.add_post, icon = R.drawable.ic_plus_square)
    object Notifications : Screen(route = "notifications", title = R.string.notifications, icon = R.drawable.ic_heart)
    object Profile : Screen(route = "profile", title = R.string.profile, icon = R.drawable.ic_user)
    object Chats : Screen(route = "chats")
}
