package com.kappdev.notes.feature_notes.presentation.notes.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FolderCard(
    folder: Folder,
    onClick: (id: Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomTheme.shapes.large,
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        onClick = { onClick(folder.id) }
    ) {
        Row(
            modifier = Modifier.padding(all = CustomTheme.spaces.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CustomTheme.spaces.medium)
        ) {
            LeadingIcon()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val configuration = LocalConfiguration.current
                val nameFraction = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.95f else 0.9f
                FolderName(folder.name, Modifier.fillMaxWidth(nameFraction))

                Items(folder.items)
            }
        }
    }
}

@Composable
private fun Items(items: Int, modifier: Modifier = Modifier) {
    val text = if (items >= 100) "99+" else items.toString()
    Text(
        text = text,
        fontSize = 16.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
private fun FolderName(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun LeadingIcon() {
    Icon(
        imageVector = Icons.Default.Folder,
        contentDescription = "folder icon",
        tint = Color.Yellow,
        modifier = Modifier.size(42.dp).alpha(0.9f)
    )
}