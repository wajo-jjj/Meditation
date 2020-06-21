package com.example.meditation

import android.content.Context
import android.content.SharedPreferences

class UserSettingsRepository {
    private val context = MyApplication.appContext
    private val pref = context.getSharedPreferences(
        UserSettings.PREF_USER_SETTINGS_NAME, Context.MODE_PRIVATE)

    fun loadUserSettings(): UserSettings{
        return UserSettings(
            //レベルID
            levelId = pref.getInt(UserSettings.UserSettingsPrefKey.LEVEL_ID.name,LevelId.EASY),
            //レベルの文字列
            levelName = context.getString(pref.getInt(UserSettings.UserSettingsPrefKey.LEVEL_NAME_STR_ID.name, R.string.level_easy_header)),
            //テーマID
            themeId = pref.getInt(UserSettings.UserSettingsPrefKey.THEME_ID.name,0),
            //テーマの文字列
            themeName = context.getString(pref.getInt(UserSettings.UserSettingsPrefKey.THEME_NAME_STR_ID.name,R.string.theme_silent)),
            //テーマの画像のResId
            themeResId = pref.getInt(UserSettings.UserSettingsPrefKey.THEME_RES_ID.name, R.drawable.pic_nobgm),
            //テーマのBGM
            themeSoundId = pref.getInt(UserSettings.UserSettingsPrefKey.THEME_SOUND_ID.name,0),
            //瞑想時間
            time = pref.getInt(UserSettings.UserSettingsPrefKey.TIME.name, 30)
        )
    }

}