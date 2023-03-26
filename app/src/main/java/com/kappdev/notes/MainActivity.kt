package com.kappdev.notes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.notes.core.data.repository.SettingRepositoryImpl
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.navigation.componets.SetupNavGraph
import com.kappdev.notes.feature_notes.domain.model.ImageShade
import com.kappdev.notes.feature_notes.domain.util.ShadeColor
import com.kappdev.notes.feature_notes.domain.util.plus
import com.kappdev.notes.ui.custom_theme.CustomNotesTheme
import com.kappdev.notes.ui.custom_theme.CustomOpacity
import com.kappdev.notes.ui.custom_theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var navController: NavHostController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var settingsRepository: SettingRepository

    private val isThemeDark = mutableStateOf(false)
    private val backgroundOpacity = mutableStateOf(0f)
    private val backgroundImage = mutableStateOf<Bitmap?>(null)
    private val imageShade = mutableStateOf(ImageShade())

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsRepository = SettingRepositoryImpl(this)
        sharedPreferences = getSharedPreferences(SettingRepositoryImpl.NAME, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        getSettings()

        setContent {
            CustomNotesTheme(
                darkTheme = isThemeDark.value,
                opacity = CustomOpacity(backgroundOpacity = backgroundOpacity.value)
            ) {
                val systemUiController = rememberSystemUiController()

                val shadeColor = when (imageShade.value.color) {
                    ShadeColor.White -> Color.White.copy(imageShade.value.opacity)
                    ShadeColor.Black -> Color.Black.copy(imageShade.value.opacity)
                }
                val statusBarColor = CustomTheme.colors.surface.plus(shadeColor)
                val navigationBarColor = CustomTheme.colors.background.plus(shadeColor)
                val darkIcons = CustomTheme.colors.isLight

                SideEffect {
                    systemUiController.setStatusBarColor(statusBarColor, darkIcons)
                    systemUiController.setNavigationBarColor(navigationBarColor, darkIcons)
                }

                navController = rememberNavController()
                val image = backgroundImage.value ?: BitmapFactory.decodeResource(this.resources, R.drawable.default_background_image)
                BackgroundImage(
                    image = image,
                    imageShade = imageShade.value,
                    modifier = Modifier.fillMaxSize()
                ) {
                    SetupNavGraph(navController)
                }
            }
        }
    }

    private fun getSettings() {
        isThemeDark.value = settingsRepository.getTheme()
        backgroundOpacity.value = settingsRepository.getBackgroundOpacity()
        backgroundImage.value = settingsRepository.getBackgroundImage()
        imageShade.value = settingsRepository.getImageShade()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SettingRepositoryImpl.KEY_THEME -> isThemeDark.value = settingsRepository.getTheme()
            SettingRepositoryImpl.KEY_BACKGROUND_OPACITY -> backgroundOpacity.value = settingsRepository.getBackgroundOpacity()
            SettingRepositoryImpl.KEY_BACKGROUND_IMAGE -> backgroundImage.value = settingsRepository.getBackgroundImage()
            SettingRepositoryImpl.KEY_IMAGE_SHADE -> imageShade.value = settingsRepository.getImageShade()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}