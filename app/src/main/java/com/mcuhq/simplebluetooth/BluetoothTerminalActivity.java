package com.mcuhq.simplebluetooth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BluetoothTerminalActivity extends AppCompatActivity {

    private TextView mReceivedDataTextView;

    BluetoothCommunicationService bluetoothCommunicationService = new BluetoothCommunicationService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_terminal);

        // Get data from intent extras
        String deviceName = getIntent().getStringExtra("device_name");
        String deviceAddress = getIntent().getStringExtra("device_address");

        // Start data receiving process
        startDataReceivingProcess(deviceName, deviceAddress);
    }

    // Method to start the data receiving process
    private void startDataReceivingProcess(String deviceName, String deviceAddress) {
        System.out.println("Device: " + deviceName);
        System.out.println("Device Address: " + deviceAddress);
        // Implement your logic to start receiving data from the device
        // You can use the deviceName parameter to identify the device

        BluetoothCommunicationService bluetoothService = new BluetoothCommunicationService(this);

        // Set listener to receive data from BluetoothCommunicationService
        bluetoothService.setOnDataReceivedListener(this::updateReceivedData);

        // Start listening for data
        bluetoothService.startListeningForData();

    }



    // Override onResume to start listening for data when the activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        bluetoothCommunicationService.setOnDataReceivedListener(this::updateReceivedData);
        bluetoothCommunicationService.startListeningForData();
    }


    // Override onPause to stop listening for data when the activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        // Stop listening for data from the connected Bluetooth device
        bluetoothCommunicationService.stopListeningForData();
    }




    // Method to update the UI with received data
    // Call this method whenever new data is received from the Bluetooth device
    private void updateReceivedData(String data) {
        // Extract the voltage value from the received data
        String voltageValue = data.substring(data.indexOf(":") + 2);

        // Format the voltage value to show 2 decimal places
        String formattedVoltage = String.format("%.2f", Double.parseDouble(voltageValue));

        // Construct the display string with the voltage label and formatted value
        String displayString = "Voltage: " + formattedVoltage + "V";

        // Append the formatted data to the TextView
        mReceivedDataTextView.append(displayString + "\n");
    }


}
