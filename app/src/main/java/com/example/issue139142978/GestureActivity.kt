package com.example.issue139142978

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.app.Activity
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class GestureActivity : Activity() {

    private val largeMemory = ByteArray(1024 * 1024 * 10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val path = Path()
        path.moveTo(300.0F, 300.0F)
        path.lineTo(1000.0F, 1000.0F)
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 1000))
            .build()
        val emptyCallback = object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                Log.d("GestureActivity", "activity = ${this@GestureActivity}")
            }
        }
        val success =
            AccessibilityGestureService.instance?.dispatchGesture(gesture, emptyCallback, null)
                ?: false
        if (!success) {
            Toast.makeText(this, "dispatch gesture error", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

}