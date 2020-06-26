package com.example.meditation.view.main
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.meditation.R
import com.example.meditation.util.FragmentTag
import com.example.meditation.util.PlayStatus
import com.example.meditation.view.dialog.LevelSelectDialog
import com.example.meditation.view.dialog.ThemeSelectDialog
import com.example.meditation.view.dialog.TimeSelectDialog
import com.example.meditation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.ReadOnlyProperty


class MainActivity : AppCompatActivity() {


      //下のviewModelの取得の仕方でエラーが出た場合はこっちを使う。
    //private val viewModel :MainViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java) }

    private val viewModel: MainViewModel by viewModels { ViewModelProvider.NewInstanceFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.screen_container,
                    MainFragment()
                )
                .commit()
        }

        observeViewModel()




        btmNavi.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_select_level -> {
                    LevelSelectDialog().show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                    ThemeSelectDialog().show(supportFragmentManager, FragmentTag.THEME_SELECT.name)
                    true

                }
                R.id.item_select_time -> {
                    TimeSelectDialog().show(supportFragmentManager, FragmentTag.TIME_SELECT.name)

                    true

                }
                else -> { false
                }
            }
        }


    }

    private fun observeViewModel() {
        viewModel.playStatus.observe(this, Observer { status ->
            when (status) {
                PlayStatus.BEFORE_START -> {
                    btmNavi.visibility = View.VISIBLE
                }
                PlayStatus.ON_START -> {
                    btmNavi.visibility = View.INVISIBLE
                }
                PlayStatus.RUNNING -> {
                    btmNavi.visibility = View.INVISIBLE

                }
                PlayStatus.PAUSE -> {
                    btmNavi.visibility = View.INVISIBLE

                }
                PlayStatus.END -> {

                }
            }

        })
    }
}
