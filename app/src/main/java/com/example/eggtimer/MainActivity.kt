package com.example.eggtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var seek: SeekBar?=null
    var go: Button?=null
    var time: TextView?=null

    var isSet: Boolean = false
    var timeLeft: Long=0

    var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seek = findViewById(R.id.seek)
        time = findViewById(R.id.time)
        go = findViewById(R.id.go)

        seek?.max= 600
        seek?.progress = 30

        seek?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) { // TODO Auto-generated method stub


            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { // TODO Auto-generated method stub
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) { // TODO Auto-generated method stub
                timeLeft=progress.toLong()
                updateText(timeLeft)
            }
        })


    }

    fun updateText(i:Long)
    {
        Log.d("check","Inside update text")
        var min = i/60
        var sec = i - min*60
        var tex = if(sec<=9) {
            "$min:0$sec"
        } else {
            "$min:$sec"
        }

        time?.text = tex
    }

    fun reset()
    {
        seek?.isEnabled = true
        isSet=false
        go?.text = "GO"
        seek?.progress = 30
        timeLeft=0
        updateText(30)
        timer?.cancel()
    }

    fun set(view: View)
    {
        var cancel= false

        if(!isSet)
        {
            seek?.isEnabled = false
            isSet=true
            go?.text="Stop"
            timer =object : CountDownTimer(timeLeft*1000, 1000)
            {
                override fun onTick(millisUntilFinished: Long) {
                        updateText(millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    MediaPlayer.create(this@MainActivity,R.raw.airhorn).start()
                    reset()
                    Log.d("time", "finish")
                }
            }.start()

        }

        else
        {
            reset()
        }

    }
}
