package com.kappdev.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.kappdev.notes.core.data.repository.SettingRepositoryImpl
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.core.presentation.navigation.componets.SetupNavGraph
import com.kappdev.notes.feature_notes.domain.model.AlarmContent
import com.kappdev.notes.feature_notes.domain.model.AlarmContentType
import com.kappdev.notes.feature_notes.domain.model.ImageShade
import com.kappdev.notes.feature_notes.domain.util.ShadeColor
import com.kappdev.notes.feature_notes.domain.util.getAverageOf
import com.kappdev.notes.feature_notes.domain.util.overlay
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
    private val imageShade = mutableStateOf(ImageShade())
    private val backgroundOpacity = mutableStateOf(0f)
    private val backgroundImage = mutableStateOf<Bitmap?>(null)
    private val startScreenRout = mutableStateOf(Screen.Notes.route)

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsRepository = SettingRepositoryImpl(this)
        sharedPreferences = getSharedPreferences(SettingRepositoryImpl.NAME, Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        getSettings()
        catchDataFrom(intent)

        setContent {
            CustomNotesTheme(
                darkTheme = isThemeDark.value,
                opacity = CustomOpacity(backgroundOpacity = backgroundOpacity.value)
            ) {
                navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                val image = backgroundImage.value ?: BitmapFactory.decodeResource(resources, R.drawable.default_background_image)

                Log.e("onCreate", "calculating Colors!!")
                val statBarColor = image
                    .getAverageOf(y = 1)
                    .overlay(shadeTint(), imageShade.value.opacity)
                val navBarColor =  image
                    .getAverageOf(y = image.height - 1)
                    .overlay(shadeTint(), imageShade.value.opacity)
                val statDarkIcons = ColorUtils.calculateLuminance(statBarColor.toArgb()) > 0.5
                val navDarkIcons = ColorUtils.calculateLuminance(navBarColor.toArgb()) > 0.5
                SideEffect {
                    systemUiController.setStatusBarColor(statBarColor, statDarkIcons)
                    systemUiController.setNavigationBarColor(navBarColor, navDarkIcons)
                }

                LaunchedEffect(key1 = startScreenRout) {
                    navController.navigate(startScreenRout.value)
                }

                BackgroundImage(
                    shadeColor = shadeColor(),
                    bitmap = image,
                    modifier = Modifier.fillMaxSize(),
                    content = { SetupNavGraph(navController) }
                )
            }
        }
    }

    private fun catchDataFrom(intent: Intent) {
        val contentJson = intent.getStringExtra("content")
        contentJson?.let { json ->
            val content = Gson().fromJson(json, AlarmContent::class.java)

            when (content.type) {
                AlarmContentType.Note -> startScreenRout.value = Screen.AddEditNote.route + "?noteId=${content.id}"
                AlarmContentType.TodoList -> startScreenRout.value = Screen.TodoList.route + "?todoListId=${content.id}"
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

    private fun shadeColor(): Color {
        return when (imageShade.value.color) {
            ShadeColor.White -> Color.White.copy(imageShade.value.opacity)
            ShadeColor.Black -> Color.Black.copy(imageShade.value.opacity)
        }
    }

    private fun shadeTint() = if (imageShade.value.color == ShadeColor.White) Color.White else Color.Black
}