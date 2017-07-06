package com.animator_abhi.flyrobestore;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.utils.NetworkConnectivityListener;
import com.animator_abhi.flyrobestore.utils.Prefs;
import com.mswipe.wisepad.apkkit.WisePadController;
import com.mswipe.wisepad.apkkit.WisePadControllerListener;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

public abstract class BaseActivity extends AppCompatActivity  implements WisePadControllerListener {
    public ViewGroup mContentContainer;
    public TextView mTitleTextView;
    public ViewGroup mFrameHeader;
    public ViewGroup mProgressBar;
    public ViewGroup contentLayout;
    private Intent setRefreshViewIntent;
    private Menu mMenu;
    private ViewGroup view_noInternet;
    private NetworkConnectivityListener mNetworkConnectivityListener;
    private  Button mNoConnectionButton;
    Dialog myDialog;
    Toolbar toolbar;
    WisePadController mWisePadController;
   // SmsVerifyCatcher smsVerifyCatcher;
    private final int REQUEST_PERMISSION = 1;
    public String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    public void onError(String error, int errorCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mswipe APKKit");
        builder.setMessage(error);
        builder.setPositiveButton("ok", null);
        builder.create().show();
    }

    @Override
    public void onMswipeAppInstalled() {

    }

    @Override
    public void onMswipeAppUpdated() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mNetworkConnectivityListener = new NetworkConnectivityListener();
        mNetworkConnectivityListener.startListening(this);
        mWisePadController = WisePadController.sharedInstance(this, this);
        //ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE,REQUEST_PERMISSION);

    /*    smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                String otpr = message.substring(0, 5);
                Log.d("msg1", otpr);
                // String code = parseCode(message);//Parse verification code
                // etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });*/

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


         mContentContainer=(ViewGroup)findViewById(R.id.content_frame_layout);
        mProgressBar = (ViewGroup) findViewById(R.id.progress_layout);
        contentLayout = (ViewGroup) findViewById(R.id.content_layout);

        if (getLayoutId() != 0 && getLayoutId() != -1) {
            View layoutView = LayoutInflater.from(this).inflate(getLayoutId(), null);
            mContentContainer.addView(layoutView);
        } else {
            if (getLayoutId() == -1) {
                mContentContainer.setVisibility(View.GONE);
            }
        }
        mNoConnectionButton = (Button) findViewById(R.id.no_connection_button);

        // No Internet View
        view_noInternet = (ViewGroup) findViewById(R.id.view_no_connection);
        myDialog = new Dialog(this);

        mNoConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* startActivity(new Intent(BaseActivity.this, WardrobeNewDesign.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));*/


                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);


              /*  finish();
                if (setRefreshViewIntent != null) {
                    startActivity(setRefreshViewIntent);
                }*/


            }
        });

        //init SmsVerifyCatcher


        initViews();
        initData();
        addPermission();
    }


    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initData();


    public void showMsgDialog(String title, String msg, int img, final int btnFlag) {
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.layout_dialog_box);
        myDialog.setCancelable(false);
        final TextView ok = (TextView) myDialog.findViewById(R.id.btn_db_ok);
        final TextView tvMsg = (TextView) myDialog.findViewById(R.id.db_msg);
        final TextView tvTitle = (TextView) myDialog.findViewById(R.id.db_title);
        ImageView ivImg = (ImageView) myDialog.findViewById(R.id.ivImg);
        ivImg.setImageResource(img);
        tvTitle.setText(title);
        tvMsg.setText(msg);

       // mPicassoClient.load(img).placeholder(R.drawable.image_placeholder_vertical).fit().centerInside().into(ivImg);

        if (btnFlag == 2) {
            ok.setText("Retry");
        }
        if (btnFlag == 1) {
            ok.setText("Done");
        }
        if (btnFlag == 0) {
            ok.setText("Cancel");
        }

        if (btnFlag == 3) {
            ok.setText("Ok");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnFlag == 0) {
                    myDialog.cancel();
                } else if (btnFlag == 1) {
                    Intent i= new Intent(BaseActivity.this,HomeActivity.class);
                    i.putExtra("flag",2);
                    startActivity(i);
                    finish();
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else if(btnFlag==3){
                    myDialog.cancel();

                }
                else{
                    finish();
                   /* if (setRefreshViewIntent != null) {
                        startActivity(setRefreshViewIntent);
                    }*/
                }

            }

        });

        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    // getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        myDialog.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
   //     smsVerifyCatcher.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
     //   smsVerifyCatcher.onStop();


    }
    @Override
    protected void onResume() {
        super.onResume();
        mWisePadController = WisePadController.sharedInstance(this, this);

        try {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNetworkConnectivityListener.startListening(this);


    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            unregisterReceiver(mNetworkReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mNetworkConnectivityListener.stopListening();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        mNetworkConnectivityListener.stopListening();

    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
        addPermission();
    }*/

    public void addPermission() {
        int permission = ActivityCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionFine = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarse = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permission != PackageManager.PERMISSION_GRANTED && permissionFine != PackageManager.PERMISSION_GRANTED && permissionCoarse != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BaseActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_PERMISSION
            );
        }


    }
    private final BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (enableNoConnectionView()) {
                // connected to the internet
                if (activeNetwork != null) {
                    view_noInternet.setVisibility(View.GONE);
                   // retryCustomRequest();
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                    }
                } else {
                    // not connected to the internet
                  //  stopCustomRequest();
                    view_noInternet.setVisibility(View.VISIBLE);
                    //mNoConnectionButton.setVisibility(View.GONE);
                }
            }
        }
    };
    protected boolean enableNoConnectionView() {
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        this.mMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        if (logoutItem != null) {
            View logoutActionView = logoutItem.getActionView();

            logoutActionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(BaseActivity.this);

                    alertDialog.setTitle("Logout");
                    alertDialog.setMessage("Are you sure you want to Logout?");

                    alertDialog.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    Log.d("##Logout", "logging...");
                                    logout();

                                }

                            })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    alertDialog.show();


                }

            });

        }





        return super.onCreateOptionsMenu(menu);
    }



    public void logout() {



        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(BaseActivity.this);

        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to Logout?");

        alertDialog.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        Log.d("##Logout", "logging...");
                       // logout();
                       // Prefs.setLoginStatus(getApplication(), false);
                        Prefs.clearPrefs(getApplication());
                        Intent i = new Intent(getApplication(), LoginActivity.class);
                        startActivity(i);
                        finish();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    }

                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog.show();


    }

}
