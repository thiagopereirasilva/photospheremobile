package com.example.photospheremobile.views.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.photospheremobile.R
import com.example.photospheremobile.models.ImageSet
import com.example.photospheremobile.service.ImageSetServiceImpl
import com.example.photospheremobile.views.CameraActivity
import com.example.photospheremobile.views.MainActivity
import java.util.*

class DialogNewImageSet : DialogFragment() {
    private var label: EditText? = null
    private var description: EditText? = null
    private var author: EditText? = null
    private var buttonSave: Button? = null
    private var buttonBack: Button? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var uuid: String? = null
        val view: View =
            activity!!.layoutInflater.inflate(R.layout.dialog_new_imageset, null)

        if (arguments != null) {
            uuid = arguments!!.getString("UUID")
        }

        this.label = view.findViewById(R.id.label)
        this.description = view.findViewById(R.id.description)
        this.author = view.findViewById(R.id.author)
        this.buttonSave = view.findViewById(R.id.salvar)
        this.buttonBack = view.findViewById(R.id.cancel)

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        this.buttonBack!!.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CameraActivity::class.java)
                it.startActivity(intent)
            }
        }

        this.buttonSave!!.setOnClickListener {
            activity?.let {
                var imageSet = ImageSet()
                imageSet?.uuid = uuid!!
                imageSet?.label = this.label?.text.toString()
                imageSet?.description = this.description?.text.toString()
                imageSet?.author = this.author?.text.toString()
                imageSet?.let { it1 -> saveImageSet(it1) }
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }

        return alert.create()
    }

    private fun saveImageSet(imageset: ImageSet) {
        ImageSetServiceImpl().saveImageSet(imageset)
    }
}