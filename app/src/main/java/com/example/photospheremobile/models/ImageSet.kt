package com.example.photospheremobile.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ImageSet : Serializable {
    @SerializedName("id")
    var id: String? = null
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("uuid")
    var uuid: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("label")
    var label: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("description")
    var description: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("calibration")
    var calibration: Double? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("author")
    var author: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("images_paths")
    var imagesPaths: List<String>? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("hdr_image")
    var hdrImage: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("created_date")
    var createDate: String? = null
        get() = field
        set(value) {
            field = value
        }
}
