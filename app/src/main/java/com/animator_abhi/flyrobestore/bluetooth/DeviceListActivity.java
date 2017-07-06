package com.animator_abhi.flyrobestore.bluetooth;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.MainActivity;
import com.animator_abhi.flyrobestore.R;

/**
 * Device list.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class DeviceListActivity extends Activity {
	private ListView mListView;
	private DeviceListAdapter mAdapter;
	private static boolean isPaired=false;
	private ArrayList<BluetoothDevice> mDeviceList;
	private BluetoothAdapter mBluetoothAdapter;

	private Button btnOk;
	@Override
	public void onBackPressed() {
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_paired_devices);
		
		mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");
		mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
		this.setFinishOnTouchOutside(false);
		mListView		= (ListView) findViewById(R.id.lv_paired);
		btnOk= (Button) findViewById(R.id.btn_Ok);

		mAdapter		= new DeviceListAdapter(this);
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getPairedDevices()) {
					Intent i = new Intent(getApplication(), MainActivity.class);
					startActivity(i);
					finish();
				}
				else
				{
					Toast.makeText(DeviceListActivity.this,"Not Paired With MSwipe Device",Toast.LENGTH_SHORT).show();
					finish();
					MainBluetoothActivity.mError.setVisibility(View.VISIBLE);
					MainBluetoothActivity.mImageView.setImageResource(R.drawable.error_small);
					MainBluetoothActivity.mError.setText("Not Paired with Mswipe Device");
					MainBluetoothActivity.bluetoothLayout.setVisibility(View.VISIBLE);
				}

			}
		});
		
		mAdapter.setData(mDeviceList);
		mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {			
			@Override
			public void onPairButtonClick(int position) {
				BluetoothDevice device = mDeviceList.get(position);
				
				if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
					unpairDevice(device);
				} else {
					showToast("Pairing...");
					
					pairDevice(device);
				}
			}
		});
		
		mListView.setAdapter(mAdapter);
		
		registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)); 
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(mPairReceiver);
		
		super.onDestroy();
	}
	
	
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        
	        if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {	        	
	        	 final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
	        	 final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
	        	 
	        	 if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
	        		 showToast("Paired");
	        	 } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
	        		 showToast("Unpaired");
	        	 }
	        	 
	        	 mAdapter.notifyDataSetChanged();
	        }
	    }
	};

	private boolean getPairedDevices(){
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

		if (pairedDevices == null || pairedDevices.size() == 0) {
			showToast("No Paired Devices Found");
		} else {

			ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

			list.addAll(pairedDevices);
			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().substring(0,2).equals("WP")){
					isPaired=true;
					Toast.makeText(DeviceListActivity.this, ""+device.getName(), Toast.LENGTH_SHORT).show();
					break;
				}

				// Add the name and address to an array adapter to show in a ListView
				// mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}

			// Intent intent = new Intent(getApplication(), DeviceListActivity.class);

			//intent.putParcelableArrayListExtra("device.list", list);

			// startActivity(intent);
		}
		return isPaired;
	}
}