package com.kappdev.notes.feature_notes.presentation.util.components

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.feature_notes.presentation.util.SubButton
import com.kappdev.notes.feature_notes.presentation.util.SubButtons
import com.kappdev.notes.ui.theme.Purple500
import com.kappdev.notes.ui.theme.Teal200

data class AnimatedMultiAddButtonColors(
    val backgroundColor: Color = Color.White.copy(alpha = 0.64f),
    val labelsColor: Color = Color.Black,
    val subButtonsBackgroundColor: Color = Color.White,
    val subButtonsContentColor: Color = Purple500,
    val inactiveMainBtnBackgroundColor: Color = Teal200,
    val activeMainBtnBackgroundColor: Color = Purple500,
    val inactiveMainBtnContentColor: Color = Color.Black,
    val activeMainBtnContentColor: Color = Color.White,
)

@Composable
fun AnimatedMultiAddButton(
    colors: AnimatedMultiAddButtonColors = AnimatedMultiAddButtonColors(),
    buttonsShape: Shape = DefaultButtonShape,
    buttonsElevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = DefaultButtonElevation,
        pressedElevation = DefaultButtonPressedElevation,
        hoveredElevation = DefaultButtonElevation,
        focusedElevation = DefaultButtonElevation,
    ),
    onClick: (buttonId: String) -> Unit
) {
    var isBtnActive by remember { mutableStateOf(false) }
    val switchState: () -> Unit = { isBtnActive = !isBtnActive }

    val transition = updateTransition(
        targetState = isBtnActive,
        label = null
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedBackground(
            color = colors.backgroundColor,
            transition = transition,
            onClick = switchState
        )

        SubButtons.buttonsList.forEachIndexed { index, button ->
            AnimatedSubButton(
                transition = transition,
                activePadding = calculateSubButtonPadding(index),
                subButton = button,
                shape = buttonsShape,
                elevation = buttonsElevation,
                colors = colors,
                onClick = onClick
            )
        }

        AnimatedMainButton(
            transition = transition,
            shape = buttonsShape,
            elevation = buttonsElevation,
            colors = colors,
            onClick = switchState
        )

    }
}

@Composable
private fun BoxScope.AnimatedMainButton(
    transition: Transition<Boolean>,
    shape: Shape,
    elevation: FloatingActionButtonElevation,
    colors: AnimatedMultiAddButtonColors,
    onClick: () -> Unit
) {
    val mainButtonBackgroundColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis = BACKGROUND_ANIM_DURATION)
        },
        label = "mainButtonRotation",
        targetValueByState = { isActive ->
            if (isActive) colors.activeMainBtnBackgroundColor else colors.inactiveMainBtnBackgroundColor
        }
    )

    val mainButtonContentColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis = BACKGROUND_ANIM_DURATION)
        },
        label = "mainButtonRotation",
        targetValueByState = { isActive ->
            if (isActive) colors.activeMainBtnContentColor else colors.inactiveMainBtnContentColor
        }
    )

    val mainButtonRotation by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            )
        },
        label = "mainButtonRotation",
        targetValueByState = { isActive ->
            if (isActive) 45f else 0f
        }
    )

    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        elevation = elevation,
        backgroundColor = mainButtonBackgroundColor,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = BtnPadding, end = BtnPadding)
            .size(MainButtonSize)
            .rotate(mainButtonRotation)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "main button icon",
            tint = mainButtonContentColor
        )
    }
}

private fun calculateSubButtonPadding(index: Int): Dp {
    val startPadding =  BtnPadding + MainButtonSize
    val gapPadding = BtnPadding * (index + 1)
    val buttonsPadding = SubButtonSize * index
    return if (index == 0) {
        startPadding + gapPadding
    } else startPadding + gapPadding + buttonsPadding
}

@Composable
private fun BoxScope.AnimatedSubButton(
    transition: Transition<Boolean>,
    activePadding: Dp,
    shape: Shape,
    elevation: FloatingActionButtonElevation,
    colors: AnimatedMultiAddButtonColors,
    subButton: SubButton,
    onClick: (id: String) -> Unit
) {
    val defaultPadding = BtnPadding + ((MainButtonSize - SubButtonSize) / 2)

    val animPadding by transition.animateDp(
        transitionSpec = {
            if (this.targetState) {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            } else {
                tween(
                    durationMillis = POPUP_ITEMS_ANIM_DURATION,
                    easing = LinearEasing
                )
            }
        },
        label = "animatedPadding",
        targetValueByState = { isActive ->
            if (isActive) activePadding else defaultPadding
        }
    )

    val animAlpha by transition.animateFloat(
        transitionSpec = { tween(POPUP_ITEMS_ANIM_DURATION) },
        label = "animatedAlpha",
        targetValueByState = { isActive ->
            if (isActive) 1f else 0f
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BtnPadding),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = animPadding, end = defaultPadding)
            .alpha(animAlpha)
    ) {
        Text(
            text = stringResource(subButton.labelResId),
            fontSize = 16.sp,
            color = colors.labelsColor
        )

        FloatingActionButton(
            onClick = { onClick(subButton.id) },
            shape = shape,
            modifier = Modifier.size(SubButtonSize),
            elevation = elevation,
            backgroundColor = colors.subButtonsBackgroundColor
        ) {
            Icon(
                imageVector = subButton.icon,
                contentDescription = "sub button icon",
                tint = colors.subButtonsContentColor
            )
        }
    }
}

@Composable
private fun BoxScope.AnimatedBackground(
    transition: Transition<Boolean>,
    color: Color = Color.Black.copy(alpha = 0.5f),
    onClick: () -> Unit
) {
    val size by transition.animateDp(
        transitionSpec = { tween(BACKGROUND_ANIM_DURATION) },
        label = "backgroundSize",
        targetValueByState = { isActive ->
            if (isActive) getMaxSize() else 0.dp
        }
    )
    val alpha by transition.animateFloat(
        transitionSpec = { tween(BACKGROUND_ANIM_DURATION) },
        label = "backgroundAlpha",
        targetValueByState = { isActive ->
            if (isActive) 1f else 0f
        }
    )
    val shape by transition.animateInt(
        transitionSpec = { tween(BACKGROUND_ANIM_DURATION) },
        label = "backgroundShape",
        targetValueByState = { isActive ->
            if (isActive) 0 else 100
        }
    )

    Spacer(
        modifier = Modifier
            .size(size)
            .alpha(alpha)
            .clickable { onClick() }
            .align(Alignment.BottomEnd)
            .background(
                color = color,
                shape = RoundedCornerShape(topStartPercent = shape)
            )
    )
}

@Composable
private fun getMaxSize(): Dp {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) screenWidth * 2 else return screenHeight * 2
}

private val DefaultButtonShape: Shape = CircleShape
private val DefaultButtonElevation = 8.dp
private val DefaultButtonPressedElevation = 12.dp

private val BtnPadding = 16.dp
private val MainButtonSize = 56.dp
private val SubButtonSize = 42.dp

private const val BACKGROUND_ANIM_DURATION = 500
private const val POPUP_ITEMS_ANIM_DURATION = 300