package com.example.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun DarkLightModeToggle(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }

    // Animate the thumb offset (Left inside padding is 4.dp, thumb size is 30.dp, so max offset is 76.dp - 30.dp - 4.dp = 42.dp)
    val thumbOffset by animateDpAsState(
        targetValue = if (isDark) 42.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = 0.72f,
            stiffness = 300f
        ),
        label = "thumb_offset"
    )

    val lightModeProgress by animateFloatAsState(
        targetValue = if (isDark) 0f else 1f,
        animationSpec = tween(durationMillis = 350),
        label = "light_mode_progress"
    )

    val darkModeProgress by animateFloatAsState(
        targetValue = if (isDark) 1f else 0f,
        animationSpec = tween(durationMillis = 350),
        label = "dark_mode_progress"
    )

    // Track Background Color Transition
    val trackColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF151D35) else Color(0xFFACDEF7),
        animationSpec = tween(durationMillis = 350),
        label = "track_color"
    )

    // Thumb Background Color Transition
    val thumbColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFFE2E8F0) else Color(0xFFFFCC00),
        animationSpec = tween(durationMillis = 350),
        label = "thumb_color"
    )

    // Animated border color for the toggle
    val borderColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF334155) else Color.Black,
        animationSpec = tween(durationMillis = 350),
        label = "border_color"
    )

    // Outer capsule box (width: 76.dp, height: 38.dp) with premium interaction
    Box(
        modifier = modifier
            .size(width = 76.dp, height = 38.dp)
            .border(1.5.dp, borderColor, RoundedCornerShape(19.dp))
            .clip(RoundedCornerShape(19.dp))
            .background(trackColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,  // No default ripple to keep the crisp tactile feel
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    onToggle()
                }
            )
            .testTag("dark_mode_toggle")
    ) {
        // --- Day Scenery (Clouds on the right, slides out to the right) ---
        if (lightModeProgress > 0f) {
            // Cloud 1
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 20.dp * (1f - lightModeProgress) - 6.dp, y = (-6).dp)
                    .graphicsLayer(alpha = lightModeProgress)
                    .size(width = 16.dp, height = 10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White.copy(alpha = 0.75f))
            )
            // Cloud 2
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 30.dp * (1f - lightModeProgress) - 12.dp, y = (-5).dp)
                    .graphicsLayer(alpha = lightModeProgress)
                    .size(width = 12.dp, height = 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = 0.65f))
            )
            // Cloud 3
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-15).dp * (1f - lightModeProgress) + 16.dp, y = 8.dp)
                    .graphicsLayer(alpha = lightModeProgress)
                    .size(width = 10.dp, height = 6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.White.copy(alpha = 0.4f))
            )
        }

        // --- Night Scenery (Stars on the left, slides in from the left) ---
        if (darkModeProgress > 0f) {
            // Star 1
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-20).dp * (1f - darkModeProgress) + 12.dp, y = 8.dp)
                    .graphicsLayer(alpha = darkModeProgress)
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            // Star 2
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-30).dp * (1f - darkModeProgress) + 22.dp, y = (-8).dp)
                    .graphicsLayer(alpha = darkModeProgress)
                    .size(2.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
            )
            // Star 3
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-15).dp * (1f - darkModeProgress) + 8.dp, y = (-4).dp)
                    .graphicsLayer(alpha = darkModeProgress)
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            // Star 4
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-10).dp * (1f - darkModeProgress) + 30.dp, y = 14.dp)
                    .graphicsLayer(alpha = darkModeProgress)
                    .size(1.5.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.5f))
            )
        }

        // --- Concentric Sunbeam / Moonlight Glowing Rings (Moving dynamically with the thumb) ---
        // Ring 1 (Largest, outer aura)
        Box(
            modifier = Modifier
                .offset(x = thumbOffset - 18.dp)
                .align(Alignment.CenterStart)
                .size(66.dp)
                .clip(CircleShape)
                .background(
                    color = if (isDark) {
                        Color.White.copy(alpha = 0.04f * darkModeProgress)
                    } else {
                        Color(0xFFFFF9C4).copy(alpha = 0.12f * lightModeProgress)
                    }
                )
        )
        // Ring 2 (Middle aura)
        Box(
            modifier = Modifier
                .offset(x = thumbOffset - 9.dp)
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    color = if (isDark) {
                        Color.White.copy(alpha = 0.08f * darkModeProgress)
                    } else {
                        Color(0xFFFFF9C4).copy(alpha = 0.20f * lightModeProgress)
                    }
                )
        )
        // Ring 3 (Inner aura)
        Box(
            modifier = Modifier
                .offset(x = thumbOffset - 4.dp)
                .align(Alignment.CenterStart)
                .size(38.dp)
                .clip(CircleShape)
                .background(
                    color = if (isDark) {
                        Color.White.copy(alpha = 0.12f * darkModeProgress)
                    } else {
                        Color(0xFFFFF9C4).copy(alpha = 0.35f * lightModeProgress)
                    }
                )
        )

        // --- Sliding Thumb (Sun or Moon) ---
        Box(
            modifier = Modifier
                .padding(start = thumbOffset)
                .align(Alignment.CenterStart)
                .size(30.dp)
                .clip(CircleShape)
                .background(thumbColor)
        ) {
            // Moon craters (fade in/out dynamically)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = darkModeProgress)
            ) {
                // Crater 1
                Box(
                    modifier = Modifier
                        .padding(start = 6.dp, top = 8.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF94A3B8).copy(alpha = 0.4f))
                )
                // Crater 2
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 14.dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF94A3B8).copy(alpha = 0.4f))
                )
                // Crater 3
                Box(
                    modifier = Modifier
                        .padding(start = 18.dp, top = 6.dp)
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF94A3B8).copy(alpha = 0.35f))
                )
            }

            // Sun soft core highlight (fade in/out dynamically)
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFF176).copy(alpha = 0.5f))
                    .align(Alignment.Center)
                    .graphicsLayer(alpha = lightModeProgress)
            )
        }
    }
}

