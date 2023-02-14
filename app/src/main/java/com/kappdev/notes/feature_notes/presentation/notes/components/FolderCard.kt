package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.feature_notes.domain.model.Folder

@Composable
fun FolderCard(
    modifier: Modifier = Modifier,
    folder: Folder
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.16f)
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LeadingIcon()

            Spacer(Modifier.width(16.dp))
            FolderName(text = folder.name)
        }
    }
}

@Composable
private fun FolderName(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LeadingIcon() {
    Icon(
        imageVector = Icons.Default.Folder,
        contentDescription = "folder icon",
        tint = Color.Yellow,
        modifier = Modifier.size(32.dp)
    )
}