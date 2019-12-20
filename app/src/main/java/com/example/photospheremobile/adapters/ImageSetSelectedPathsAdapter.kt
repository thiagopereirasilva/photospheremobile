package com.example.photospheremobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_image_set_selected.view.*

class ImageSetSelectedPathsAdapter(
    private val imageSetsPaths: List<String>,
    private val context: Context
) :
    RecyclerView.Adapter<ImageSetSelectedPathsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(
                com.example.photospheremobile.R.layout.content_image_set_selected,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageSetsPaths.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageSet = imageSetsPaths[position]
        // Safe call == ?
        holder?.let {
            Picasso.get().load(imageSet).resize(120, 60).into(holder.url)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val url = itemView.item_image

        fun bindView(url: String) {
            val url = itemView.item_image
        }
    }

}