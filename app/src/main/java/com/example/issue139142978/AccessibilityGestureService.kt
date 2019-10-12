package com.example.issue139142978

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class AccessibilityGestureService : AccessibilityService() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(ACTION_ENABLED))
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    companion object {
        var instance: AccessibilityGestureService? = null
            private set
        const val ACTION_ENABLED = "com.example.issue139142978.accessibility.enabled"
    }
}