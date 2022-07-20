package com.mustfaibra.instagraph.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.components.CustomButton
import com.mustfaibra.instagraph.providers.LocalNavHost
import com.mustfaibra.instagraph.sealed.Screen
import com.mustfaibra.instagraph.sealed.UiState
import com.mustfaibra.instagraph.ui.theme.Dimension
import com.mustfaibra.instagraph.ui.theme.SkyBlue

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val controller = LocalNavHost.current
        val uiState by remember { loginViewModel.uiState }
        val loginButtonText = when (uiState) {
            is UiState.Loading -> {
                "Logging now ..."
            }
            else -> {
                "Log in as"
            }
        }
        /** Create the basic UI of the screen */
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(Dimension.pagePadding.times(1.5f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /** First we put the logo */
            Text(
                text = "Instagraph",
                style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.ExtraBold),
                fontFamily = FontFamily.Cursive,
            )
            Spacer(modifier = Modifier.height(Dimension.pagePadding.times(3)))
            /** Then the user's profile image */
            Image(
                painter = painterResource(id = R.drawable.ic_meta),
                contentDescription = "profile image",
                modifier = Modifier.size(Dimension.xlIcon)
            )
            Spacer(modifier = Modifier.height(Dimension.pagePadding))
            /** The the user's name */
            Text(
                text = "mustfa_ibra",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(Dimension.pagePadding))
            /** The login button */
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                buttonColor = SkyBlue,
                contentColor = MaterialTheme.colors.onPrimary,
                text = loginButtonText,
                enabled = uiState !is UiState.Loading,
                textStyle = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                onButtonClicked = {
                    /** Handle the click event of the login as button */
                    loginViewModel.authenticateUser(
                        onAuthenticated = {
                            /** When user is authenticated, navigate to home screen and pop all the routes in the BackStack */
                            controller.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onAuthenticationFailed = {
                            /** Do whatever you want when it failed */
                        }
                    )
                },
                leadingIcon = {
                    if (uiState is UiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(Dimension.smIcon),
                            color = MaterialTheme.colors.onPrimary,
                            strokeWidth = Dimension.xs.div(2f)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(Dimension.pagePadding))
            /** Switch accounts button */
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        /** Handle the click event of switching accounts option */
                    }
                    .padding(Dimension.sm),
                text = "Switch accounts",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = SkyBlue,
            )
        }
        Divider()
        /** Bottom Section which contains signing up option */
        CompositionLocalProvider(
            LocalContentAlpha provides 0.8f,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimension.pagePadding)
                    .padding(bottom = Dimension.pagePadding.times(2)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                )
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .padding(Dimension.xs),
                    text = "Sign up.",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
}