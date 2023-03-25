package com.kappdev.notes.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.kappdev.notes.R
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.feature_notes.domain.model.ImageShade
import com.kappdev.notes.feature_notes.domain.util.ImageConvertor

class SettingRepositoryImpl(val context: Context): SettingRepository {

    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getBackgroundOpacity(): Float {
        return sharedPreferences.getFloat(KEY_BACKGROUND_OPACITY, 0.16f)
    }

    override fun setBackgroundOpacity(value: Float) {
        editor.putFloat(KEY_BACKGROUND_OPACITY, value).apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(KEY_THEME, false)
    }

    override fun setTheme(isDark: Boolean) {
        editor.putBoolean(KEY_THEME, isDark).apply()
    }

    override fun setBackgroundImage(image: Bitmap) {
        val encodedImage = ImageConvertor.bitmapToString(image)
        editor.putString(KEY_BACKGROUND_IMAGE, encodedImage).apply()
    }

    override fun getBackgroundImage(): Bitmap {
        val encodedImage = sharedPreferences.getString(KEY_BACKGROUND_IMAGE, null)
        val image = ImageConvertor.stringToBitmap(encodedImage)
        return image ?: getDefaultImage()
    }

    override fun setImageShade(imageShade: ImageShade) {
        val imageShadeJson = Gson().toJson(imageShade)
        editor.putString(KEY_IMAGE_SHADE, imageShadeJson).apply()
    }

    override fun getImageShade(): ImageShade {
        val imageShadeJson = sharedPreferences.getString(KEY_IMAGE_SHADE, null)
        return if (imageShadeJson != null) {
            Gson().fromJson(imageShadeJson, ImageShade::class.java)
        } else {
            ImageShade()
        }
    }

    private fun getDefaultImage() = BitmapFactory.decodeResource(context.resources, R.drawable.default_background_image)

    companion object {
        const val NAME = "settings"

        const val KEY_THEME = "is_theme_dark"
        const val KEY_BACKGROUND_OPACITY = "background_opacity"
        const val KEY_BACKGROUND_IMAGE = "background_image"
        const val KEY_IMAGE_SHADE = "image_shade"
    }
}