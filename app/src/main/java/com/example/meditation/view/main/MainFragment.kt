package com.example.meditation.view.main

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.meditation.viewmodel.MainViewModel
import com.example.meditation.R
import com.example.meditation.databinding.FragmentMainBinding
import com.example.meditation.util.PlayStatus
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var  binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main,container,false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            viewmodel = viewModel
            setLifecycleOwner(activity)
        }

        viewModel.initParameters()

        btnPlay.setOnClickListener{
            viewModel.changeStatus()
        }

        observeViewModel()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeViewModel() {
        viewModel.txtLevel.observe(this, Observer { levelTxt -> txtLevel.text = levelTxt })
        viewModel.txtTheme.observe(this, Observer { themeTxt -> txtTheme.text = themeTxt})
        viewModel.displayTimeSeconds.observe( this, Observer { displayTime -> txtTime.text = displayTime } )
        viewModel.msgUpperSmall.observe(this, Observer { upperTxt -> txtMsgUpperSmall.text = upperTxt })
        viewModel.msgLowerLarge.observe(this, Observer { lowerTxt -> txtMsgLowerLarge.text = lowerTxt })
        viewModel.playStatus.observe(this, Observer { status ->
            updateUi(status!!)
            when (status) {
                PlayStatus.BEFORE_START -> {
                }
                PlayStatus.ON_START -> {
                    viewModel.countDownBeforeStart()
                }
                PlayStatus.RUNNING -> {
                    viewModel.startMeditation()

                }
                PlayStatus.PAUSE -> {
                    viewModel.pauseMeditation()
                }
                PlayStatus.END -> {
                    viewModel.finishMeditation()
                }
            }
        })

        viewModel.themePicFileResId.observe(this, Observer { themePicResId -> loadBackgroundImage(this, themePicResId, meditation_screen) })
    }

    private fun updateUi(status: Int) {
        when (status) {
            PlayStatus.BEFORE_START -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_play)
                    }
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.INVISIBLE
                }
            }
            PlayStatus.ON_START -> {
                binding.apply {
                    btnPlay.visibility = View.INVISIBLE
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.VISIBLE
                }

            }

            PlayStatus.RUNNING -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_pause)
                    }
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.VISIBLE
                }
            }

            PlayStatus.PAUSE -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_play)
                    }
                    btnFinish.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_finish)
                    }
                    txtShowMenu.visibility = View.VISIBLE
                }

            }

            PlayStatus.END -> {

            }
        }
    }

    private fun loadBackgroundImage(mainFragment: MainFragment, themePicResId: Int?, meditationScreen: ConstraintLayout?) {
       Glide.with(mainFragment).load(themePicResId).into(object: SimpleTarget<Drawable>(){
           override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                meditationScreen?.background = resource
           }
       })
    }


}