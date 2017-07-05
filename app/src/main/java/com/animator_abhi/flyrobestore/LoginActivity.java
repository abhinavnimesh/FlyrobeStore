package com.animator_abhi.flyrobestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.Model.UserModel;
import com.animator_abhi.flyrobestore.Volley.VolleyWebservicePostJson;
import com.animator_abhi.flyrobestore.Volley.VolleyWebserviceResponseListener;
import com.animator_abhi.flyrobestore.utils.ApiUtils;
import com.animator_abhi.flyrobestore.utils.Constants;
import com.animator_abhi.flyrobestore.utils.Prefs;
import com.animator_abhi.flyrobestore.utils.SharedPrefUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonSyntaxException;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity implements /*VolleyWebserviceResponseListener ,*/View.OnClickListener {
    Button signIn,signUp,submit;
    final static String TAG="LoginActivity";
   // TextInputLayout otp;
    EditText userId,otp;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    int otpCode;
    private FirebaseDatabase database;
    Boolean isUserExist=false;
    Boolean isUserLoggedIn=false;
    FirebaseAuth mAuth;
    TextInputLayout tInputLayout;
    TextView phone;
    String message;
    private String phoneNo;
   // VolleyWebserviceResponseListener listener=LoginActivity.this;
     SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference firebase = database.getReference().child("users");

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                // updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    //   mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                 /*   Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();*/
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
               // userMob.setEnabled(false);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                signUp.setVisibility(View.GONE);
                //userId.setVisibility(View.GONE);
                signIn.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                tInputLayout.setVisibility(View.GONE);
                // [START_EXCLUDE]
                // Update UI
                // updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                String otpr = message.substring(0, 6);
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
        tInputLayout= (TextInputLayout) findViewById(R.id.textInputLayout);
        otp= (EditText) findViewById(R.id.otp);

    }

    @Override
    protected void initData() {
        isUserLoggedIn=Prefs.getLoginStatus(this);

        if (isUserLoggedIn)
        {
            Intent i=new Intent(this,HomeActivity.class);
            startActivity(i);
            finish();
            return;
        }



         signIn.setOnClickListener(this);
        submit.setOnClickListener(this);
     /*   submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().equals("")) {
                    showMsgDialog("Warning!", "OTP Cannot Be Empty", R.drawable.error_small, 0);
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
                        showMsgDialog("Warning!", "Please Enter Correct OTP.", R.drawable.error_small, 0);
                    }
                }}
        });*/

       /* signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId.getText().toString().equals("")) {
                    showMsgDialog("Warning!", "User ID Cannot Be Empty", R.drawable.error_small, 0);
                }
                else


                if (checkUser(userId.getText().toString())){
                    userId.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.INVISIBLE);
                signUp.setVisibility(View.GONE);

                // phone.setText(phoneNo);
                otpCode = (int) ((Math.random() * (99999 - 10000)) + 10000);
                Log.d(TAG,""+otpCode);
                SharedPrefUtils.putInt(getApplication(), Constants.OTP, otpCode);
                JSONObject jsonObject = new JSONObject();
                message = String.valueOf(otpCode) + " is your Login OTP. Use this to verify your mobile number. Please don't share it with anyone else. Regards, Team Flyrobe.";

                try {
                    jsonObject.put("message", message);
                    jsonObject.put("send_to", userId.getText().toString());
                    jsonObject.put("gateway", 2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new VolleyWebservicePostJson(getApplicationContext(), listener, ApiUtils.SMS_SEND_URL, jsonObject, "post", ApiUtils.SMS_SEND_ID).execute();


                // SharedPrefUtils.putInt(OtpVerificationActivity.this, Constants.OTP, otp);



            }
            else
                {
                    Log.d("else",""+isUserExist);

                }
            }
        });*/
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplication(),SignUpActivity.class);
                startActivity(i);
            }
        });


    }

    private boolean checkUser(String number) {

        final DatabaseReference firebase = database.getReference().child("users").child(number);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    isUserExist=true;
                   // showMsgDialog("Warning!", "User Does Exist", R.drawable.error_small, 0);
                    try {
                        isUserExist=true;
                        startPhoneNumberVerification(userId.getText().toString());
                      /*  UserModel userModel=dataSnapshot.getValue(UserModel.class);
                        // Map<String, String> hashMap = snapshot.getValue(HashMap.class);
                        Prefs.setUserId(getApplication(), userModel.getUserId());
                        Prefs.setUSERNAME(getApplication(), userModel.getName());
                        Prefs.setMobile(getApplication(), userModel.getMob());
                        Prefs.setCity(getApplication(), userModel.getCity());
                        Prefs.setStoreid(getApplication(), userModel.getStoreId());
                        Prefs.setEmail(getApplication(), userModel.getEmailId());*/


                    } catch (Exception e) {
                        isUserExist=true;

                    }
                }
                else {
                    isUserExist=false;
                    showMsgDialog("Warning!", "User Does not Exist", R.drawable.error_small, 0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                isUserExist=false;
                showMsgDialog("Warning!", "cancel", R.drawable.error_small, 0);

            }
        });
Log.d("user",""+isUserExist);
        return isUserExist;
    }
/*
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

    }*/
private boolean validatePhoneNumber() {
    if (userId.getText().toString().equals("")) {
        showMsgDialog("Warning!", "Fields cannot Be Empty", R.drawable.error_small, 0);
        return false;
    }
    return true;
}

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signIn:
                if (!validatePhoneNumber()) {
                    return;
                }

               checkUser(userId.getText().toString());


                break;
            case R.id.submit:
                String code = otp.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    showMsgDialog("Warning!", "OTP Cannot Be Empty", R.drawable.error_small, 0);

                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
         /*   case R.id.button_resend:
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
            case R.id.sign_out_button:
                signOut();
                break;*/
        }


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

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //currentUser=userMob.getText().toString();
                           // final DatabaseReference firebase = database.getReference().child("users").child(currentUser);
                            // Sign in success, update UI with the signed-in user's information
                            Prefs.setMobile(getApplication(), userId.getText().toString());
                            Log.d("signup", "signInWithCredential:success");
                            isUserLoggedIn=true;
                            Prefs.setLoginStatus(LoginActivity.this,isUserLoggedIn);
                            //showMsgDialog("Sign Up Completed","You can Login",R.drawable.success,1);
                         /*   UserModel userModel = new UserModel( userId.getText().toString(),userMob.getText().toString(), userName.getText().toString(),userEmail.getText().toString(), citySpinner.getSelectedItem().toString(), storeIdSpinner.getSelectedItem().toString());
                            firebase.setValue(userModel);
                            Prefs.setUserId(getApplication(), userModel.getUserId());
                            Prefs.setUSERNAME(getApplication(), userModel.getName());
                            Prefs.setMobile(getApplication(), userModel.getMob());
                            Prefs.setCity(getApplication(), userModel.getCity());
                            Prefs.setStoreid(getApplication(), userModel.getStoreId());
                            Prefs.setEmail(getApplication(), userModel.getEmailId());

                            FirebaseUser user = task.getResult().getUser();
                            Intent in=new Intent(getApplication(),HomeActivity.class);*/
                            // startActivity(in);
                            //   finish();
                            // ...
                            final DatabaseReference firebaseCred = database.getReference().child("cred");
                            firebaseCred.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue()!=null)
                                    {


                                         /*   Map<String, String> hashMap = dataSnapshot.getValue(HashMap.class);
                                            Prefs.setMasterId(getApplication(),getValuesWithValid(hashMap, "userName") );
                                            Prefs.setMasterPass(getApplication(), getValuesWithValid(hashMap, "pass"));*/
                                           Prefs.setMasterId(getApplication(), (String) dataSnapshot.child("userName").getValue());
                                            Prefs.setMasterPass(getApplication(), (String) dataSnapshot.child("pass").getValue());
                                    //   Toast.makeText(LoginActivity.this,"username"+(String) dataSnapshot.child("userName").getValue(),Toast.LENGTH_SHORT).show();

                                      //    Toast.makeText(getApplication(),""+getValuesWithValid(hashMap, "userName")+ getValuesWithValid(hashMap, "pass"),Toast.LENGTH_SHORT).show();
                                      //  Log.d("cred from firebase",""+getValuesWithValid(hashMap, "userName")+ getValuesWithValid(hashMap, "pass"));

                                    }
                                    else{
                                       // Toast.makeText(getApplication(),"no data found",Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                  //  Toast.makeText(getApplication(),"error while access",Toast.LENGTH_SHORT).show();


                                }
                            });

                            Intent in=new Intent(getApplication(),HomeActivity.class);
                            in.putExtra("flag",2);
                         startActivity(in);
                           finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("signup", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

   /* private String getValuesWithValid(Map<String, String> hashMap, String displayName) {
        if (hashMap.containsKey("" + displayName) && hashMap.get("" + displayName).length() > 0) {
            return hashMap.get("" + displayName) + "";
        } else {
            return "";
        }
    }*/
}
