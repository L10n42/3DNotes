package com.kappdev.notes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.notes.core.data.repository.SettingRepositoryImpl
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.navigation.componets.SetupNavGraph
import com.kappdev.notes.ui.custom_theme.CustomNotesTheme
import com.kappdev.notes.ui.custom_theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var navController: NavHostController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var settingsRepository: SettingRepository

    private val isThemeDark = mutableStateOf(false)

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsRepository = SettingRepositoryImpl(this)
        sharedPreferences = getSharedPreferences(SettingRepositoryImpl.NAME, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        getSettings()

        setContent {
            CustomNotesTheme(darkTheme = isThemeDark.value) {
                val systemUiController = rememberSystemUiController()
                val surfaceColor = CustomTheme.colors.primary
                val backgroundColor = CustomTheme.colors.background
                SideEffect {
                    systemUiController.setStatusBarColor(surfaceColor)
                    systemUiController.setNavigationBarColor(backgroundColor)
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

    private fun getSettings() { isThemeDark.value = settingsRepository.getTheme() }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SettingRepositoryImpl.KEY_THEME -> isThemeDark.value = settingsRepository.getTheme()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}