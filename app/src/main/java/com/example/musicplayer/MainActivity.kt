package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var mediaPlayer: MediaPlayer
    private var handler: Handler = Handler()
    private var onTime: Int = 0
    private var playTime: Int = 0
    private var endTime: Int = 0
    private var forwardTime: Int = 5000
    private var backwardTime: Int = 5000
//hhh

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.music)

        seekBar.isClickable = false

        btnPlay.setOnClickListener {

            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                btnPlay.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer.start()
                btnPlay.setImageResource(R.drawable.ic_pause)
            }

            endTime = mediaPlayer.duration
            playTime = mediaPlayer.currentPosition
            seekBar.max = endTime
            onTime = 1
            txtSongTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(endTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(endTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime.toLong()))
            )
            txtStartTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(playTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()))
            )
            seekBar.progress = playTime
            handler.postDelayed(updateSongTime, 100)
        }

        btnForward.setOnClickListener {
            if ((playTime + forwardTime) <= endTime) {
                playTime += forwardTime
                mediaPlayer.seekTo(playTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!btnPlay.isEnabled) {
                btnPlay.isEnabled = true
            }
        }
        btnBackward.setOnClickListener {
            if ((playTime - backwardTime) > 0) {
                playTime -= backwardTime
                mediaPlayer.seekTo(playTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!btnPlay.isEnabled) {
                btnPlay.isEnabled = true
            }
        }
        btnStop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer.create(this, R.raw.music)
            mediaPlayer.isLooping
        }

        btnLoop.setOnClickListener {
            mediaPlayer.isLooping = true
        }
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (fromUser)
                   mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private val updateSongTime = object : Runnable {
        override fun run() {
            playTime = mediaPlayer.currentPosition

            txtStartTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(playTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(playTime.toLong())
                )
            )
            seekBar.progress = playTime
            handler.postDelayed(this, 100)
        }

    }
}