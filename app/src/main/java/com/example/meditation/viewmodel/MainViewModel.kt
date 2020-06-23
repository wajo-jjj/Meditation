package com.example.meditation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation.model.UserSettings
import com.example.meditation.model.UserSettingsRepository
import com.example.meditation.util.PlayStatus

class MainViewModel: ViewModel() {

    //画面真ん中上の小さい文字
    var msgUpperSmall = MutableLiveData<String>()
    //画面真ん中上の大きい文字
    var msgLowerLarge = MutableLiveData<String>()
    //背景画像のリソースID
    var themePicFileResId = MutableLiveData<Int>()
    //テーマ
    var txtTheme = MutableLiveData<String>()
    //レベル
    var txtLevel = MutableLiveData<String>()

    //変換前の秒の時間
    var remainedTimeSeconds = MutableLiveData<Int>()
    //変換した分の時間
    var displayTimeSeconds = MutableLiveData<String>()

    var playStatus = MutableLiveData<Int>()

    private val userSettingsRepository = UserSettingsRepository()
    private lateinit var userSettings: UserSettings

    fun initParameters() {
        userSettings = userSettingsRepository.loadUserSettings()
        msgUpperSmall.value =""
        msgLowerLarge.value =""
        themePicFileResId.value = userSettings.themeResId
        txtTheme.value = userSettings.themeName
        txtLevel.value = userSettings.levelName
        remainedTimeSeconds.value = userSettings.time * 60
        displayTimeSeconds.value = changeTimeFormat(remainedTimeSeconds.value!!)
        playStatus.value = PlayStatus.BEFORE_START

    }

    //秒を分表示に変換するためのメソッド
    private fun changeTimeFormat(timeSeconds: Int): String? {
        val minutes = timeSeconds / 60
        val minutesString = if (minutes < 10) "0" + minutes.toString() else minutes.toString()
        val seconds = timeSeconds - (minutes * 60)
        val secondsString = if (seconds < 10) "0" + seconds.toString() else seconds.toString()
        return minutesString + " : " + secondsString
    }

    fun setLevel(selectedItemId: Int) {
       txtLevel.value = userSettingsRepository.setLevel(selectedItemId)
    }


}