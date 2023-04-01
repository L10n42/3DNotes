package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.feature_notes.domain.util.DateConvertor
import com.kappdev.notes.ui.custom_theme.CustomTheme
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun Time(timestamp: Long) {
    val time = DateConvertor.getDateString(timestamp)

    Text(
        text = time,
        fontSize = 14.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun AlarmTime(time: LocalDateTime) {
    val timeString = DateConvertor.getDateString(
        time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Alarm,
            contentDescription = "alarm icon",
            tint = CustomTheme.colors.primary,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = timeString,
            fontSize = 14.sp,
            color = CustomTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun Content(text: AnnotatedString, maxLines: Int) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Title(text: AnnotatedString) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Content(text: String, maxLines: Int) {

    Text(
        text = text,
        fontSize = 16.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Title(text: String, selected: Boolean) {
    val widthFraction = if (selected) 0.9f else 1f
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth(widthFraction)
    )
}