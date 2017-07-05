package com.animator_abhi.flyrobestore;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.bluetooth.AndroidBluetooth;
import com.animator_abhi.flyrobestore.bluetooth.MainBluetoothActivity;
import com.animator_abhi.flyrobestore.utils.Prefs;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class HomeActivity extends BaseActivity {
    BluetoothAdapter mbluetoothAdapter;
    Intent i;
FirebaseAuth auth;
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
        auth=FirebaseAuth.getInstance();
        i=getIntent();
int flg=i.getIntExtra("flag",0);
        if (flg==1){
            Toast.makeText(getApplication(),"Payment Successful",Toast.LENGTH_SHORT).show();}
        if (flg==2)
        {        Toast.makeText(this,"Welcome "+auth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();
        }
      //  Toast.makeText(getApplication(),"creds"+ Prefs.getMasterId(getApplicationContext()),Toast.LENGTH_SHORT).show();
        findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBluetooth(true);

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
