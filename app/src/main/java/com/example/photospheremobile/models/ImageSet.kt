package com.example.photospheremobile.models

import com.google.gson.annotations.SerializedName
import java.util.*

class ImageSet(
    @SerializedName("id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("calibration")
    val calibration: Double,
    @SerializedName("author")
    val author: String,
    @SerializedName("createDate")
    val createDate: Date
)
