package com.mustfaibra.instagraph.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mustfaibra.instagraph.R
import com.mustfaibra.instagraph.providers.LocalNavHost
import com.mustfaibra.instagraph.sealed.Screen
import com.mustfaibra.instagraph.ui.theme.Dimension

@Composable
fun SplashScreen(splashViewModel: SplashViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = Dimension.lg.times(2f), top = Dimension.lg.times(3f)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val controller = LocalNavHost.current
        val isAppLaunchedBefore by splashViewModel.isAppLaunchedBefore.collectAsState(initial = false)

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.insta_logo_lottie)
        )
        // to control the animation
        val progress by animateLottieCompositionAsState(
            // pass the composition created above
            composition,
            iterations = 1,
            restartOnPlay = false,
            speed = 0.5f,
        )
        LaunchedEffect(key1 = progress) {
            if (progress == 1f) {
                if (isAppLaunchedBefore) {
                    /** Launched before and assumed logged for my scenario, we should go to home now */
                    controller.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                } else {
                    /**
                     * Not launched before so we should navigate to another screen in case required,
                     * In my scenario I will go to login
                     */
                    controller.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(Dimension.xlIcon.times(1.3f)),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.xs),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "From",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(0.8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_meta),
                    contentDescription = "meta",
                    modifier = Modifier.size(Dimension.smIcon),
                    tint = MaterialTheme.colors.primary,
                )
                Text(
                    text = "Meta",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary,
                )
            }

        }
    }
}