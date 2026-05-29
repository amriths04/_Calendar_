package com.example.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.util.ResponsiveUtil

@Composable
fun FloatingBottomDock(
    activeTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .padding(bottom = ResponsiveUtil.moderateScale(2f)) // pushed further down
            .fillMaxWidth()
            .zIndex(100f)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(ResponsiveUtil.moderateScale(24f)))
                .background(Color(0xFF222222).copy(alpha = 0.90f)) // Adjusted to 0.90 as requested
                .padding(ResponsiveUtil.moderateScale(6f))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ResponsiveUtil.moderateScale(16f)) // slightly separate more
            ) {
                DockButton(
                    text = "Calendar",
                    icon = Icons.Filled.Home,
                    isSelected = activeTab == "calendar",
                    onClick = { onTabSelected("calendar") }
                )
                DockButton(
                    text = "Widget",
                    icon = Icons.Filled.Widgets,
                    isSelected = activeTab == "widget",
                    onClick = { onTabSelected("widget") }
                )
            }
        }
    }
}

@Composable
private fun DockButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White
    val view = LocalView.current

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(ResponsiveUtil.moderateScale(18f)))
            .background(backgroundColor)
            .clickable { 
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                onClick() 
            }
            .padding(
                horizontal = ResponsiveUtil.moderateScale(24f),
                vertical = ResponsiveUtil.moderateScale(12f)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = contentColor,
            modifier = Modifier.size(ResponsiveUtil.moderateScale(24f))
        )
        Spacer(modifier = Modifier.height(ResponsiveUtil.moderateScale(4f)))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = ResponsiveUtil.normalize(12f)
            ),
            color = contentColor
        )
    }
}
