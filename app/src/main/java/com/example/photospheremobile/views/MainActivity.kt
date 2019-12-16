package com.example.photospheremobile.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photospheremobile.R
import com.example.photospheremobile.adapters.ImageSetListAdapter
import com.example.photospheremobile.models.ImageSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = image_set_list_recyclerview
        recyclerView.adapter = ImageSetListAdapter(imageSets(), this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        fab.setOnClickListener { view ->
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }


    private fun imageSets(): List<ImageSet> {
        return listOf(
            ImageSet(
                "ID:ABC",
                "Label 1",
                "Desc 1",
                12.0,
                "Jorge",
                Date()
            ),
            ImageSet(
                "ID:DEF",
                "Label 2",
                "Desc 2",
                12.0,
                "Ester",
                Date()
            ),
            ImageSet(
                "ID:GHI",
                "Label 3",
                "Desc 3",
                12.0,
                "John",
                Date()
            )
        )
    }

}
