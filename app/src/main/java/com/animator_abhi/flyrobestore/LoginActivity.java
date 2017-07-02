package com.animator_abhi.flyrobestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.Volley.VolleyWebservicePostJson;
import com.animator_abhi.flyrobestore.Volley.VolleyWebserviceResponseListener;
import com.animator_abhi.flyrobestore.utils.ApiUtils;
import com.animator_abhi.flyrobestore.utils.Constants;
import com.animator_abhi.flyrobestore.utils.SharedPrefUtils;
import com.google.gson.JsonSyntaxException;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements VolleyWebserviceResponseListener ,View.OnClickListener {
    Button signIn,signUp,submit;
    final static String TAG="LoginActivity";
   // TextInputLayout otp;
    EditText userId,otp;
    int otpCode;
    TextView phone;
    String message;
    private String phoneNo;
    VolleyWebserviceResponseListener listener=LoginActivity.this;
     SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                String otpr = message.substring(0, 5);
                Log.d("msg1", otpr);
                otp.setText(otpr);
                // String code = parseCode(message);//Parse verification code
                // etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

    }

    @Override
    protected int getLayoutId() {
       return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        findViewById(R.id.signIn).getBackground().setLevel(0);
        findViewById(R.id.signUp).getBackground().setLevel(0);
        submit= (Button) findViewById(R.id.submit);
        signIn= (Button) findViewById(R.id.signIn);
        signUp= (Button) findViewById(R.id.signUp);
        userId= (EditText) findViewById(R.id.userId);
        otp= (EditText) findViewById(R.id.otp);

    }

    @Override
    protected void initData() {





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().equals("")) {
                    showMsgDialog("Warning!", "OTP Cannot Be Empty", R.drawable.button, 0);
                }
                else{
                    int i=SharedPrefUtils.getInt(getApplication(), Constants.OTP);
                    int otpByUser= Integer.parseInt(otp.getText().toString());


                    if (i==otpByUser)
                    {

                        Intent in=new Intent(getApplication(),HomeActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else{
                        showMsgDialog("Warning!", "Please Enter Correct OTP.", R.drawable.flyrobe, 0);
                    }
                }}
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.INVISIBLE);
                signUp.setVisibility(View.GONE);
                userId.setVisibility(View.GONE);
                // phone.setText(phoneNo);
                otpCode = (int) ((Math.random() * (99999 - 10000)) + 10000);
                Log.d(TAG,""+otpCode);
                SharedPrefUtils.putInt(getApplication(), Constants.OTP, otpCode);
                JSONObject jsonObject = new JSONObject();
                message = String.valueOf(otpCode) + " is your Login OTP. Use this to verify your mobile number. Please don't share it with anyone else. Regards, Team Flyrobe.";

                try {
                    jsonObject.put("message", message);
                    jsonObject.put("send_to", "8750934760");
                    jsonObject.put("gateway", 2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new VolleyWebservicePostJson(getApplicationContext(), listener, ApiUtils.SMS_SEND_URL, jsonObject, "post", ApiUtils.SMS_SEND_ID).execute();


                // SharedPrefUtils.putInt(OtpVerificationActivity.this, Constants.OTP, otp);



            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplication(),SignUpActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void responseWithId(String strresponse, String via, int urlId) throws JsonSyntaxException, NullPointerException {

    }

    @Override
    public void onError() throws NullPointerException {

    }

    @Override
    public void response(String response, String via) throws JsonSyntaxException, NullPointerException {

    }

    @Override
    public void slowInternetConnction() throws NullPointerException {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
