package com.iinnovation.hindishayari

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

// for navigation drawer data class
data class DrawerItem(
    val router: String,
    val title: String,
    val isSelected: ImageVector,
    val notSelected: ImageVector
)


val listofDrawer = listOf(
    DrawerItem(
        router = "",
        title = "Share App",
        isSelected = Icons.Filled.Share,
        notSelected = Icons.Outlined.Share
    ),
    DrawerItem(
        router = "",
        title = "Rate App",
        isSelected = Icons.Filled.Star,
        notSelected = Icons.Outlined.Star
    ),
    DrawerItem(
        router = "",
        title = "About Us",
        isSelected = Icons.Filled.AccountBox,
        notSelected = Icons.Outlined.AccountBox
    ),
    DrawerItem(
        router = "",
        title = "Privacy Policy",
        isSelected = Icons.Filled.Phone,
        notSelected = Icons.Outlined.Phone
    )
)