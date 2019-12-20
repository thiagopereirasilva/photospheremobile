package com.example.photospheremobile.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photospheremobile.R

import com.example.photospheremobile.adapters.ImageSetSelectedPathsAdapter
import com.example.photospheremobile.models.ImageSet
import kotlinx.android.synthetic.main.activity_image_set_selected.*
import java.sql.Array

class ImageSetSelectedActivity : AppCompatActivity() {

    private var mAdapter: ImageSetSelectedPathsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_set_selected)

        val imageSet = intent.getSerializableExtra("imageSet") as? ImageSet

        val actionbar = supportActionBar
        actionbar!!.title = "ImageSet "+imageSet?.label
        actionbar.setDisplayHomeAsUpEnabled(true)

        val recyclerView = image_set_paths_recyclerview
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        mAdapter = ImageSetSelectedPathsAdapter(imageSet?.imagesPaths!!, this)
        recyclerView!!.adapter = mAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
