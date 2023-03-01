package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun ButtonsCouple(
    modifier: Modifier = Modifier,
    positiveBtnEnable: Boolean = true,
    positiveTitleResId: Int,
    negativeTitleResId: Int,
    width: Dp = 150.dp,
    shape: Shape = CustomTheme.shapes.large,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            shape = shape,
            modifier = Modifier.width(width),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = CustomTheme.colors.background,
                contentColor = CustomTheme.colors.primary
            ),
            elevation = ButtonDefaults.elevation(
                disabledElevation = 0.dp,
                defaultElevation = 0.dp
            ),
            onClick = onNegativeClick
        ) {
            Text(text = stringResource(negativeTitleResId), fontSize = 16.sp)
        }

        Button(
            shape = shape,
            modifier = Modifier.width(width),
            colors = ButtonDefaults.buttonColors(
                disabledBackgroundColor = CustomTheme.colors.background,
                disabledContentColor = CustomTheme.colors.onBackground
            ),
            elevation = ButtonDefaults.elevation(
                disabledElevation = 0.dp,
                defaultElevation = 0.dp
            ),
            onClick = onPositiveClick,
            enabled = positiveBtnEnable
        ) {
            Text(text = stringResource(positiveTitleResId), fontSize = 16.sp)
        }
    }
}