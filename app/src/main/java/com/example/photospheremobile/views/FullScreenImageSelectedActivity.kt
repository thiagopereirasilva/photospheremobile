package com.example.photospheremobile.views

import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen_image_selected.*
import kotlinx.android.synthetic.main.content_full_screen_image_selected.*


class FullScreenImageSelectedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.photospheremobile.R.layout.activity_full_screen_image_selected)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val imageUrl = intent.getStringExtra("imageUrl")
        val photoView = photoView
        val progressBar = progressBar
        Picasso
            .get()
            .load(imageUrl)
            .rotate(90.0F)
            .into(photoView, object : Callback.EmptyCallback() {
                override fun onSuccess() {
                    progressBar.visibility = GONE
                }
            })
    }

}
