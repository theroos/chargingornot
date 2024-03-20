package com.example.theroos.chargingornot

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.theroos.chargingornot.R
import android.content.IntentFilter
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.BatteryManager

class MainActivity : AppCompatActivity() {
    private var chargingStatusTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chargingStatusTextView = findViewById(R.id.chargingStatusTextView)

        // Register BroadcastReceiver to monitor battery state
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    private val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

            // Update TextView with charging status
            if (isCharging) {
                chargingStatusTextView!!.text = "Phone is connected to a charger."
            } else {
                chargingStatusTextView!!.text = "Phone is not connected to a charger."
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister BroadcastReceiver when the activity is destroyed
        unregisterReceiver(batteryReceiver)
    }
}