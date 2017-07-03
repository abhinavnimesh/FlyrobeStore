package com.animator_abhi.flyrobestore;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener{
BluetoothAdapter mbluetoothAdapter;
    TextView tv;
    Set<BluetoothDevice> pairedDevices;
   Button scanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        scanBtn= (Button) findViewById(R.id.scanBtn);
        tv= (TextView) findViewById(R.id.textView);
        scanBtn.setOnClickListener(this);
        mbluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
       pairedDevices =    mbluetoothAdapter.getBondedDevices();




    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.d("devices","Found device " + device.getName());
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        String devices="";
        switch(v.getId())
        {
            case R.id.scanBtn :
              /*  // If there are paired devices
                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        Log.d("devices",device.getName() + "    " + device.getAddress());
                       devices=devices+ device.getName() + "    " + device.getAddress()+"\n";
                        // Add the name and address to an array adapter to show in a ListView
                        //  mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
tv.setText(devices);*/
                IntentFilter filter = new IntentFilter();

                filter.addAction(BluetoothDevice.ACTION_FOUND);
              filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

                registerReceiver(mReceiver, filter);
                mbluetoothAdapter.startDiscovery();



                break;
        }
    }
}
