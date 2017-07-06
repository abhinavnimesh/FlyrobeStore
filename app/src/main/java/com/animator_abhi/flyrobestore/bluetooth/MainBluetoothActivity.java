package com.animator_abhi.flyrobestore.bluetooth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.app.Activity;
import android.app.ProgressDialog;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.animator_abhi.flyrobestore.MainActivity;
import com.animator_abhi.flyrobestore.R;

/**
 * Main activity.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class MainBluetoothActivity extends Activity {
	private TextView mStatusTv;
	private Button mActivateBtn;
	private Button mPairedBtn;
	private Button mScanBtn;
    public static ImageView mImageView;
    public  static    TextView mError;
    private static boolean isPaired=false;
    public  static LinearLayout bluetoothLayout;
	
	private ProgressDialog mProgressDlg;
	
	private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
    private HashSet<BluetoothDevice> mHashDeviceList = new HashSet<BluetoothDevice>();
	
	private BluetoothAdapter mBluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_bluetooth_main);
        bluetoothLayout = (LinearLayout) findViewById(R.id.bluetoothLayout);

        mStatusTv 			= (TextView) findViewById(R.id.tv_status);
		mActivateBtn 		= (Button) findViewById(R.id.btn_enable);
		mPairedBtn 			= (Button) findViewById(R.id.btn_view_paired);
		mScanBtn 			= (Button) findViewById(R.id.btn_scan);
		mImageView= (ImageView) findViewById(R.id.imageView2);
        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
		mError= (TextView) findViewById(R.id.tv_error);
        mProgressDlg 		= new ProgressDialog(this);
		
		mProgressDlg.setMessage("Scanning...");
		mProgressDlg.setCancelable(false);
		mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		        
		        mBluetoothAdapter.cancelDiscovery();
		    }
		});
		
		if (mBluetoothAdapter == null) {
			showUnsupported();
		} else {
              if(getPairedDevices()) {
                  Intent i=new Intent(this, MainActivity.class);
                  startActivity(i);
                  finish();
              }
              else
              {
                  bluetoothLayout.setVisibility(View.VISIBLE);
                  mBluetoothAdapter.startDiscovery();
              }

			mPairedBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
                    getPairedDevices();

				}
			});
			
			mScanBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View arg0) {

                    mBluetoothAdapter.startDiscovery();
				}
			});
			
			mActivateBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					if (mBluetoothAdapter.isEnabled()) {
						mBluetoothAdapter.disable();
						
						showDisabled();
					} else {
						Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						
					    startActivityForResult(intent, 1000);
					}
				}
			});
			
			if (mBluetoothAdapter.isEnabled()) {
				showEnabled();
			} else {
				showDisabled();
			}
		}
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	public void onPause() {
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.isDiscovering()) {
				mBluetoothAdapter.cancelDiscovery();
			}
		}
		
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(mReceiver);
		
		super.onDestroy();
	}

	private boolean getPairedDevices(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices == null || pairedDevices.size() == 0) {
            showToast("No Paired Devices Found");
            //bluetoothLayout.setVisibility(View.VISIBLE);
        } else {

            ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

            list.addAll(pairedDevices);
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().substring(0,2).equals("WP")){
                    isPaired=true;
                   // Toast.makeText(MainBluetoothActivity.this, ""+device.getName(), Toast.LENGTH_SHORT).show();
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
	private void showEnabled() {
		mStatusTv.setText("Bluetooth is On");

		mActivateBtn.setText("Disable");		
		mActivateBtn.setEnabled(true);
		
		mPairedBtn.setEnabled(true);
		mScanBtn.setEnabled(true);
	}
	
	private void showDisabled() {
		mStatusTv.setText("Bluetooth is Off");
		//mStatusTv.setTextColor(Color.RED);
		
		mActivateBtn.setText("Enable");
		mActivateBtn.setEnabled(true);
		
		mPairedBtn.setEnabled(false);
		mScanBtn.setEnabled(false);
	}
	
	private void showUnsupported() {
		mStatusTv.setText("Bluetooth is unsupported by this device");
		
		mActivateBtn.setText("Enable");
		mActivateBtn.setEnabled(false);
		
		mPairedBtn.setEnabled(false);
		mScanBtn.setEnabled(false);
	}
	
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {	    	
	        String action = intent.getAction();
	        
	        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
	        	final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
	        	 
	        	if (state == BluetoothAdapter.STATE_ON) {
	        		//showToast("Enabled");
	        		 
	        		showEnabled();
	        	 }
	        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
	        	//mDeviceList = new ArrayList<BluetoothDevice>();
                mHashDeviceList=new HashSet<BluetoothDevice>();
                bluetoothLayout.setVisibility(View.GONE);
				mProgressDlg.show();
	        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	        	mProgressDlg.dismiss();
                Intent newIntent = new Intent(getApplication(), DeviceListActivity.class);
                mDeviceList=new ArrayList<BluetoothDevice>(mHashDeviceList);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                if(mDeviceList.isEmpty()){
                    bluetoothLayout.setVisibility(View.VISIBLE);
                    mImageView.setImageResource(R.drawable.error_small);
                    mError.setVisibility(View.VISIBLE);
                    mError.setText(R.string.error_bluetoothNotFound);
                }
	        	else {
                    mImageView.setImageResource(R.drawable.bluetooth_icon);
                    mError.setVisibility(View.GONE);
				startActivity(newIntent);}
	        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	        	BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try{
              if ((device.getName().substring(0,2)+"").equals("WP")) {
                    mHashDeviceList.add(device);
                    Toast.makeText(getApplication(),""+device.getName().substring(0,2),Toast.LENGTH_SHORT).show();
                    mDeviceList.add(device);
                }}
                catch (Exception e)
                {}
              //  mHashDeviceList.add(device);

                Log.d("devices","Found device " + device.getName());
	        }
	    }
	};

}