package com.example.photospheremobile.views

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun imageSets(): List<ImageSet> {
        return listOf(
            ImageSet(
                "ID:ABC",
                "Label 1",
                "Desc 1",
                Date()
            ),
            ImageSet(
                "ID:DEF",
                "Label 2",
                "Desc 2",
                Date()
            ),
            ImageSet(
                "ID:GHI",
                "Label 3",
                "Desc 3",
                Date()
            )
        )
    }

}
