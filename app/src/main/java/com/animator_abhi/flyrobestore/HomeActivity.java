package com.animator_abhi.flyrobestore;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.animator_abhi.flyrobestore.bluetooth.AndroidBluetooth;
import com.animator_abhi.flyrobestore.bluetooth.MainBluetoothActivity;
import com.animator_abhi.flyrobestore.utils.Constants;
import com.animator_abhi.flyrobestore.utils.Prefs;
import com.google.firebase.auth.FirebaseAuth;
import com.mswipe.wisepad.apkkit.WisePadController;

import java.util.Set;

public class HomeActivity extends BaseActivity {
    BluetoothAdapter mbluetoothAdapter;
    Intent i;
    private String mSwipeId,
            mSwipePassword;
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
        findViewById(R.id.lstTransaction).getBackground().setLevel(0);


    }

    @Override
    protected void initData() {

        auth=FirebaseAuth.getInstance();
        i=getIntent();
int flg=i.getIntExtra("flag",0);
        mSwipeId=Prefs.getMasterId(this);
        mSwipePassword=Prefs.getMasterPass(this);
        if (flg==1){
            Toast.makeText(getApplication(),"Payment Successful",Toast.LENGTH_SHORT).show();}
        if (flg==2)
        {        Toast.makeText(this,"Welcome "+auth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();
        }
      //  Toast.makeText(getApplication(),"creds"+ Prefs.getMasterId(getApplicationContext()),Toast.LENGTH_SHORT).show();
        findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setBluetooth(true)){

                Intent i=new Intent(getApplication(),MainBluetoothActivity.class);
                startActivity(i);
                }
            }
        });

        findViewById(R.id.logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        findViewById(R.id.lstTransaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastTrxDetails();
            }
        });
    }


    private void getLastTrxDetails() {

        mWisePadController.processLastTrx(
                mSwipeId,
                mSwipePassword,
                Constants.isProduction,
                Constants.mOrientation,
                Constants.isSPrinterSupported);

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



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WisePadController.MS_LAST_TRX_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
              Intent intent = data;
                String status = (" " + intent.getStringExtra("txtStatus") == null ? "" : intent.getStringExtra("txtStatus"));//TrxAmount
                // showMsgDialog("Last Transaction",status,R.drawable.success,0);
                // setViews();
            } else {

                if (data != null && data.hasExtra("errMsg")) {

                    final Dialog dialog = Constants.mshowDialog(HomeActivity.this, Constants.CARDSALE_DIALOG_MSG, data.getExtras().getString("errMsg"), "3");

                    Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();

                        }
                    });
                }
            }
        }


        if (requestCode == WisePadController.MS_VOID_TRX_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                String trxdate = data.getStringExtra("trxDate") == null ? "" : data.getStringExtra("trxDate");

                final Dialog dialog = Constants.mshowDialog(HomeActivity.this, "Void Transaction",
                        "Status" + "\n\n" +
                                "Approved" + "\n" +
                                "Tx date: " + trxdate + "\n" +
                                "Card No: **** **** **** " + "1823" + "\n" +
                                "Amount: " + "10.00", "1");


                Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
                yes.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        dialog.dismiss();

                        finish();


                    }
                });

                dialog.show();

            } else {

                if (data != null) {

                    final Dialog dialog = Constants.mshowDialog(HomeActivity.this, "Void Transaction", data.getStringExtra("statusMessage"), "1");
                    Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
                    yes.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    dialog.show();

                }
            }

        }
    }
}
