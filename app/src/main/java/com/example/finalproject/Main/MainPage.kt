package com.example.finalproject.Main

import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import kotlinx.android.synthetic.main.activity_main_page.*


class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        supportActionBar?.hide()

        titleText.text="垃圾分类"
        button1.setImageResource(R.drawable.pic12)
        text1.textSize= 20F
        text1.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        videoView.setVideoURI(uri)
        videoView.setMediaController(MediaController(this))
        videoView.requestFocus()

    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.suspend()
    }
}
