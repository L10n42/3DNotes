package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.layout.*
import com.kappdev.notes.R
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun UnableToFind() {
    val color = CustomTheme.colors.onSurface.copy(alpha = 0.5f)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier.size(128.dp),
                tint = color
            )

            Text(
                text = stringResource(R.string.msg_unable_to_find),
                fontSize = 18.sp,
                color = color
            )
        }
    }
}