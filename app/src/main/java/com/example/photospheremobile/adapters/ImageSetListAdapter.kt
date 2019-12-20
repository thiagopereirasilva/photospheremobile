package com.example.photospheremobile.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photospheremobile.models.ImageSet
import com.example.photospheremobile.views.ImageSetSelectedActivity
import kotlinx.android.synthetic.main.image_set_item.view.*


class ImageSetListAdapter(
    private val imageSets: List<ImageSet>,
    private val context: Context
) :
    RecyclerView.Adapter<ImageSetListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(com.example.photospheremobile.R.layout.image_set_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageSets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageSet = imageSets[position]
        // Safe call == ?
        holder?.let {
            it.label.text = imageSet.label
            it.description.text = imageSet.description
            it.itemView.setOnClickListener {
                Log.i("CLICK", "CLICK")
                context.startActivity(putExtra("imageSet", imageSet))
            }
        }
    }

    private fun putExtra(key: String, imageSet: ImageSet): Intent {
        val intent = Intent(context, ImageSetSelectedActivity::class.java)
        intent.putExtra(key, imageSet)
        return intent
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label = itemView.image_set_label
        val description = itemView.image_set_description

        fun bindView(imageSet: ImageSet) {
            val label = itemView.image_set_label
            val description = itemView.image_set_description

            label.text = imageSet.label
            description.text = imageSet.description
        }
    }

}


