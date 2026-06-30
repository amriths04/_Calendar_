package com.example.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalView
import android.view.HapticFeedbackConstants
import com.example.util.ResponsiveUtil

@Composable
fun WeekStartToggle(
    firstDayOfWeek: Int,
    isDark: Boolean,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = ResponsiveUtil.verticalScale(16f), 
                horizontal = ResponsiveUtil.moderateScale(8f)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Week starts on",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        val optionWidth = 80.dp
        val toggleHeight = 40.dp
        
        val indicatorOffset by animateDpAsState(
            targetValue = if (firstDayOfWeek == 7) 0.dp else optionWidth,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            label = "indicatorOffset"
        )
        
        val sundayTextColor by animateColorAsState(
            targetValue = if (firstDayOfWeek == 7) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            label = "sundayTextColor"
        )
        
        val mondayTextColor by animateColorAsState(
            targetValue = if (firstDayOfWeek == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            label = "mondayTextColor"
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f))
                .padding(4.dp)
                .height(toggleHeight)
        ) {
            // Animated sliding indicator
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(optionWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(optionWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { 
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                onDaySelected(7) 
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sunday",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = sundayTextColor
                    )
                }

                Box(
                    modifier = Modifier
                        .width(optionWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { 
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                onDaySelected(1) 
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Monday",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = mondayTextColor
                    )
                }
            }
        }
    }
}
