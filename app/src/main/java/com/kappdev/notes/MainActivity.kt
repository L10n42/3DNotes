package com.kappdev.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.navigation.componets.SetupNavGraph
import com.kappdev.notes.ui.theme.NotesTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NotesTheme {
                val systemUiController = rememberSystemUiController()
                val color = MaterialTheme.colors.surface.copy(alpha = 0.16f)
                SideEffect {
                    systemUiController.setStatusBarColor(color, darkIcons = false)
                }

                navController = rememberNavController()
                val backgroundImage = painterResource(R.drawable.background_image_1)
                BackgroundImage(
                    image = backgroundImage ,
                    modifier = Modifier.fillMaxSize()
                ) {
                    SetupNavGraph(navController)
                }
            }
        }
    }
}