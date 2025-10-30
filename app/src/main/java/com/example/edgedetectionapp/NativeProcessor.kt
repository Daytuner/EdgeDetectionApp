package com.example.edgedetectionapp


object NativeProcessor {

    init {
        System.loadLibrary("edgedetection")
    }

    external fun testOpenCV(): String
}