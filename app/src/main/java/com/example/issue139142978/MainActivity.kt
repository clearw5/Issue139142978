package com.example.issue139142978

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        // Only need to focus on the following code and the code in GestureActivity:
        btnDispatchGesture.setOnClickListener {
            if (!checkboxAccessibilityService.isChecked) {
                Toast.makeText(
                    this,
                    "Please turn on Accessibility Service first!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, GestureActivity::class.java))
        }
    }

    private val accessibilityServiceStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, intent: Intent) {
            checkboxAccessibilityService.isChecked =
                intent.action == AccessibilityGestureService.ACTION_ENABLED
        }
    }

    private fun init() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        registerReceiver(
            accessibilityServiceStatusReceiver,
            IntentFilter(AccessibilityGestureService.ACTION_ENABLED)
        )

        checkboxAccessibilityService.setOnCheckedChangeListener { _, checked ->
            val accessibilityGestureService = AccessibilityGestureService.instance
            if (checked && accessibilityGestureService == null) {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
            if (!checked && accessibilityGestureService != null) {
                accessibilityGestureService.disableSelf()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        checkboxAccessibilityService.isChecked = AccessibilityGestureService.instance != null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(accessibilityServiceStatusReceiver)
    }

}
