package com.mcuhq.simplebluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import java.util.Set;

public class BluetoothCommunicationService {
    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothA2dp bluetoothA2dp;
    private BluetoothDevice bluetoothDevice;

    public BluetoothCommunicationService(Context context) {
        this.context = context;

    }



    public void startListeningForData() {
        // Get the Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if Bluetooth is supported and enabled
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            // Handle Bluetooth not available or enabled
            return;
        }

        // Get a reference to the connected Bluetooth device (assume already connected)
        bluetoothDevice = getConnectedBluetoothDevice();

        // Check if A2DP profile is supported
        if (!isA2dpProfileSupported(bluetoothDevice)) {
            // Handle A2DP not supported on this device
            return;
        }

        // Get the A2DP proxy object
        bluetoothA2dp = getBluetoothA2dpProxy();

        // Start streaming audio to the connected Bluetooth device
        startAudioStreaming();
    }

    public void stopListeningForData() {
        // Check if A2DP is supported and initialized
        if (bluetoothA2dp != null) {
            // Stop streaming audio
            stopAudioStreaming();
        }
    }

    private BluetoothDevice getConnectedBluetoothDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) { // Replace with your device name
                    return device;

                }
            }
        }
        return null;
    }

    private boolean isA2dpProfileSupported(BluetoothDevice device) {
        return bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED;
    }

    private BluetoothA2dp getBluetoothA2dpProxy() {
        final BluetoothA2dp[] bluetoothA2dp = new BluetoothA2dp[1];
        bluetoothAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if (profile == BluetoothProfile.A2DP) {
                    bluetoothA2dp[0] = (BluetoothA2dp) proxy;
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
                if (profile == BluetoothProfile.A2DP) {
                    bluetoothA2dp[0] = null;
                }
            }
        }, BluetoothProfile.A2DP);
        return bluetoothA2dp[0];
    }

    private void startAudioStreaming() {
        // Implement your logic to start audio streaming
    }

    private void stopAudioStreaming() {
        // Implement your logic to stop audio streaming
    }
    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }

    private OnDataReceivedListener onDataReceivedListener;

    public void setOnDataReceivedListener(OnDataReceivedListener listener) {
        this.onDataReceivedListener = listener;
    }

    // Call this method whenever new data is received from the Bluetooth device
    private void onDataReceived(String data) {
        if (onDataReceivedListener != null) {
            onDataReceivedListener.onDataReceived(data);
        }
    }


}
