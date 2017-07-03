package com.animator_abhi.flyrobestore;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Set;

public class HomeActivity extends BaseActivity {
    BluetoothAdapter mbluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        findViewById(R.id.card).getBackground().setLevel(0);
        findViewById(R.id.transaction).getBackground().setLevel(0);


    }

    @Override
    protected void initData() {


        findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBluetooth(true);
                Set<BluetoothDevice> pairedDevices =    mbluetoothAdapter.getDefaultAdapter().getBondedDevices();
// If there are paired devices
                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        Log.d("devices",device.getName() + "    " + device.getAddress());
                        // Add the name and address to an array adapter to show in a ListView
                      //  mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
                Intent i=new Intent(getApplication(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }
}
