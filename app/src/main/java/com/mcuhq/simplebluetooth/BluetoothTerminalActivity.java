package com.mcuhq.simplebluetooth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BluetoothTerminalActivity extends AppCompatActivity {

    private TextView mReceivedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_terminal);

        mReceivedDataTextView = (TextView) findViewById(R.id.text_received_data_content);

        // Get data from intent extras
        String deviceName = getIntent().getStringExtra("device_name");
        String deviceAddress = getIntent().getStringExtra("device_address");

        setTitle("Bluetooth Terminal - " + deviceName);

        // Update UI with device information
        // You can display device name, address, etc. if needed
        // Example: mReceivedDataTextView.setText("Connected to: " + deviceName + " (" + deviceAddress + ")");
    }

    // Override onResume to start listening for data when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        // Start listening for data from the connected Bluetooth device
        // You need to implement this part based on your Bluetooth communication logic
    }

    // Override onPause to stop listening for data when the activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        // Stop listening for data from the connected Bluetooth device
        // You need to implement this part based on your Bluetooth communication logic
    }

    // Method to update the UI with received data
    // Call this method whenever new data is received from the Bluetooth device
    private void updateReceivedData(String data) {
        // Append the received data to the TextView
        mReceivedDataTextView.append(data + "\n");
    }
}
