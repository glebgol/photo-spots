package com.glebgol.photospots.domain.data

import java.io.File

class CreateTagRequest {
    lateinit var image: File
    lateinit var description: String
    var longitude: Float = 0.0f
    var latitude: Float = 0.0f
}
