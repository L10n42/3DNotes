package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.NoteWithAnnotation
import com.kappdev.notes.feature_notes.domain.util.DateConvertor
import com.kappdev.notes.ui.custom_theme.CustomTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnotatedNoteCard(
    noteWithAnnotation: NoteWithAnnotation,
    modifier: Modifier = Modifier,
    onClick: (id: Long) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        onClick = { onClick(noteWithAnnotation.id) }
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Title(noteWithAnnotation.title)

            if (noteWithAnnotation.content.isNotEmpty()) Content(noteWithAnnotation.content)

            Time(noteWithAnnotation.timestamp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onLongClick: () -> Unit = {},
    onClick: (id: Long) -> Unit
) {
    var cardSize by remember { mutableStateOf(Size.Zero) }
    val cardWidth = with(LocalDensity.current) { cardSize.width.toDp() }
    val cardHeight = with(LocalDensity.current) { cardSize.height.toDp() }

    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(CustomTheme.shapes.large)
                .combinedClickable(
                    onClick = { onClick(note.id) },
                    onLongClick = { onLongClick() }
                )
                .onGloballyPositioned { coordinates ->
                    cardSize = coordinates.size.toSize()
                },
            elevation = 0.dp,
            backgroundColor =  CustomTheme.colors.transparentSurface
        ) {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Title(note.title, selected)

                if (note.content.isNotEmpty()) Content(note.content)

                Time(note.timestamp)
            }
        }

        SelectionEffect(isSelected = selected, height = cardHeight, width = cardWidth, cardType = SelectionCardType.NOTE)
    }
}

@Composable
private fun Time(timestamp: Long) {
    val time = DateConvertor.getDateString(timestamp)

    Text(
        text = time,
        fontSize = 14.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun Content(text: AnnotatedString) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun Title(text: AnnotatedString) {
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
private fun Content(text: String) {

    Text(
        text = text,
        fontSize = 16.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun Title(text: String, selected: Boolean) {
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