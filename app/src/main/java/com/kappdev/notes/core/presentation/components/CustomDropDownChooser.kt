package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun OutlineDropDown(
    items: List<String>,
    selectedValue: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    shape: Shape = RoundedCornerShape(4.dp),
    boxColor: Color = CustomTheme.colors.onSurface,
    menuColor: Color = CustomTheme.colors.surface,
    contentPadding: PaddingValues = PaddingValues(start = 4.dp, top = 4.dp, bottom = 4.dp),
    onSelect: (item: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val changeState = { expanded = !expanded }

    var boxSize by remember { mutableStateOf(Size.Zero) }
    val boxWidth = with(LocalDensity.current) { boxSize.width.toDp() }

    Box(modifier = modifier) {
        DropdownBox(
            shape = shape,
            enable = enable,
            boxColor = boxColor,
            expanded = expanded,
            value = selectedValue,
            onClick = changeState,
            textStyle = textStyle,
            contentPadding = contentPadding,
            modifier = Modifier
                .wrapContentWidth()
                .onGloballyPositioned { coordinates ->
                    boxSize = coordinates.size.toSize()
                }
        )

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .width(boxWidth)
                .height(200.dp)
                .background(color = menuColor),
            onDismissRequest = changeState,
            properties = PopupProperties(focusable = true)
        ) {
            items.forEach { item ->
                DropdownItem(
                    value = item,
                    textStyle = textStyle,
                    onClick = {
                        onSelect(item)
                        changeState()
                    }
                )
            }
        }
    }
}

@Composable
private fun DropdownItem(
    value: String,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Text(
            text = value,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DropdownBox(
    shape: Shape,
    value: String,
    enable: Boolean,
    boxColor: Color,
    expanded: Boolean,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onClick: () -> Unit
) {
    TextButton(
        shape = shape,
        enabled = enable,
        onClick = onClick,
        modifier = modifier,
        contentPadding = contentPadding,
        border = BorderStroke(width = 1.dp, color = boxColor)
    ) {
        Text(
            text = value,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.wrapContentWidth()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "dropdown menu icon",
            tint = boxColor,
            modifier = Modifier.rotate(if (expanded) 180f else 360f)
        )
    }
}
