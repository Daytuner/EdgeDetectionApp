package com.example.edgedetectionapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val CAMERA_PERMISSION_CODE = 100

    private lateinit var textureView: android.view.TextureView
    private lateinit var statusText: TextView
    private var cameraManager: CameraManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textureView = findViewById(R.id.textureView)
        statusText = findViewById(R.id.statusText)

        // Test OpenCV
        try {
            val version = NativeProcessor.testOpenCV()
            Log.d(TAG, "OpenCV Version: $version")
            statusText.text = "OpenCV: $version - Checking camera permission..."
        } catch (e: Exception) {
            Log.e(TAG, "OpenCV error", e)
            statusText.text = "OpenCV Error: ${e.message}"
            return
        }

        // Request camera permission
        if (checkCameraPermission()) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Camera permission granted")
                startCamera()
            } else {
                Log.e(TAG, "Camera permission denied")
                statusText.text = "Camera permission denied"
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCamera() {
        statusText.text = "Starting camera..."
        cameraManager = CameraManager(this, textureView) { bytes, width, height ->
            // Frame callback - we'll process frames here later
        }
        cameraManager?.startCamera()
        statusText.text = "Camera ready - Live preview"
    }

    override fun onPause() {
        super.onPause()
        cameraManager?.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        if (checkCameraPermission() && cameraManager != null) {
            cameraManager?.startCamera()
        }
    }
}
