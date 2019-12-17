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
import com.example.photospheremobile.views.CameraActivity
import com.example.photospheremobile.views.MainActivity

class DialogNewImageSet : DialogFragment() {
    private var edit1: EditText? = null
    private var edit2: EditText? = null
    private var buttonSave: Button? = null
    private var buttonBack: Button? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View =
            activity!!.layoutInflater.inflate(R.layout.dialog_new_imageset, null)

        this.edit1 = view.findViewById(R.id.edit1)
        this.edit2 = view.findViewById(R.id.edit2)
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
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }

        return alert.create()
    }
}