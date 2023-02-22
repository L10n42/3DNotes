package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R

@Composable
fun SettingsTopBar(
    onBackClick: () -> Unit
) {

    Surface(
        color = MaterialTheme.colors.surface.copy(alpha = 0.12f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.onSurface
                )
            }

            Text(
                text = stringResource(R.string.settings_label),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

//    TopAppBar(
//        modifier = Modifier
//            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
//            .background(
//                color = MaterialTheme.colors.surface.copy(alpha = 0.12f),
//                shape = RoundedCornerShape(16.dp)
//            ),
//        title = {
//            Text(
//                text = stringResource(R.string.settings_label),
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                color = MaterialTheme.colors.onSurface,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier.fillMaxWidth()
//            )
//        },
//        elevation = 0.dp,
//        backgroundColor = Color.Transparent,
//        navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBackIos,
//                    contentDescription = "back button",
//                    tint = MaterialTheme.colors.onSurface
//                )
//            }
//        }
//    )
}