package com.example.photospheremobile.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photospheremobile.R
import com.example.photospheremobile.adapters.ImageSetListAdapter
import com.example.photospheremobile.models.ImageSet
import com.example.photospheremobile.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var mImageSetList: MutableList<ImageSet> = ArrayList()
    private var mAdapter: ImageSetListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = image_set_list_recyclerview
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        mAdapter = ImageSetListAdapter(mImageSetList, this)
        recyclerView!!.adapter = mAdapter
        imageSets()

        fab.setOnClickListener { view ->
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }


    private fun imageSets() {

        val call = NetworkUtils().imageSetService().getImageSets()
        call.enqueue(object : Callback<MutableList<ImageSet>> {

            override fun onResponse(
                call: Call<MutableList<ImageSet>>,
                response: Response<MutableList<ImageSet>>
            ) {
                Log.d("onResponse", "List ImageSet: " + response.body()!!.size)
                val list = response.body()
                if (list != null) {
                    mImageSetList.addAll(list!!)
                    mAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MutableList<ImageSet>>, t: Throwable) {
                Log.e("onFailure", "Got error : " + t.localizedMessage)
            }
        })
    }

}
