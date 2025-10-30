package com.example.edgedetectionapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Test OpenCV integration
        val textView = findViewById<TextView>(R.id.sample_text)
        val version = NativeProcessor.testOpenCV()
        textView.text = "OpenCV Version: $version"
    }
}