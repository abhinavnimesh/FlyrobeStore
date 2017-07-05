package com.animator_abhi.flyrobestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.animator_abhi.flyrobestore.Model.UserModel;
import com.animator_abhi.flyrobestore.utils.Prefs;
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
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;


import java.util.concurrent.TimeUnit;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{
    final static String TAG="SignUpActivity";
    String[] city=new String[]{"Select City","Delhi","Mumbai"};
    String[] storeDelhi =new String[]{"Select Store","Rajouri Store","Shapurjat Store"};
    String[] storeMumbai =new String[]{"Select Store","Santacruz Store"};
    String[] store =new String[]{"Select Store"};

    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    SmsVerifyCatcher smsVerifyCatcher;

    String currentUser;
   Button signUp;
    Button verify;
   Boolean isUserExist=false;
    Spinner citySpinner,storeIdSpinner;
    EditText userId,userName,userMob,userEmail;
    private boolean mVerificationInProgress = false;
    private boolean isUserLoggedIn=false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    EditText mVerificationField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                String otpr = message.substring(0, 6);
                Log.d("msg1", otpr);
                mVerificationField.setText(otpr);
                // String code = parseCode(message);//Parse verification code
                // etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initViews() {
        citySpinner= (Spinner) findViewById(R.id.citySpinner);
        storeIdSpinner= (Spinner) findViewById(R.id.storeSpinner);
        userId= (EditText) findViewById(R.id.userId);
        userName=(EditText) findViewById(R.id.name);
        userMob=(EditText) findViewById(R.id.mob);
        signUp= (Button) findViewById(R.id.submit);
        verify= (Button) findViewById(R.id.verify);
        userEmail=(EditText) findViewById(R.id.email);
        mVerificationField=(EditText) findViewById(R.id.otp);
    }

    private ArrayAdapter<String> initSpinner(String[] store)
    {
        ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(this,
                R.layout.spinner_text, store);
        return adapter2;
    }

    @Override
    protected void initData() {
        ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,
                R.layout.spinner_text, city);

        citySpinner.setAdapter(adapter1);
        storeIdSpinner.setAdapter(initSpinner(store));
       // getSpinnerData();

     citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             switch(position){
                 case 1:
                     storeIdSpinner.setAdapter(initSpinner(storeDelhi));
                     break;
                 case 2:
                     storeIdSpinner.setAdapter(initSpinner(storeMumbai));
                     break;
             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });

        signUp.setOnClickListener(this);
        verify.setOnClickListener(this);
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
                userMob.setEnabled(false);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                signUp.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);
                mVerificationField.setVisibility(View.VISIBLE);

                // [START_EXCLUDE]
                // Update UI
               // updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };

    }

    private void getSpinnerData() {
        final DatabaseReference firebase = database.getReference().child("city");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                  //  Toast.makeText(SignUpActivity.this, ""+snapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                    for (DataSnapshot insnapshot : snapshot.getChildren()) {
                        Toast.makeText(SignUpActivity.this, ""+insnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void checkForUserAndSignuUp() {
        currentUser=userMob.getText().toString();
        final DatabaseReference firebase = database.getReference().child("users").child(currentUser);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // customLoaderDialog.hide();
                // firebase.removeEventListener(this);
                if (snapshot.getValue() != null) {
                    /*GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                    Map<String, String> hashMap = snapshot.getValue(genericTypeIndicator );

*/
                    try {
                        showMsgDialog("Warning!", "User Already Exist", R.drawable.error_small, 0);

                        UserModel userModel=snapshot.getValue(UserModel.class);
                       // Map<String, String> hashMap = snapshot.getValue(HashMap.class);
                        Prefs.setUserId(getApplication(), userModel.getUserId());
                        Prefs.setUSERNAME(getApplication(), userModel.getName());
                        Prefs.setMobile(getApplication(), userModel.getMob());
                        Prefs.setCity(getApplication(), userModel.getCity());
                        Prefs.setStoreid(getApplication(), userModel.getStoreId());
                        Prefs.setEmail(getApplication(), userModel.getEmailId());


                    } catch (Exception e) {

                    }
                    isUserExist = true;
                    Log.d("SignUpActivity","user exist"+isUserExist);
                    //  if (customLoaderDialog != null)
                    //    customLoaderDialog.hide();
                    //      Intent intent = new Intent(getActivity(), MainActivity.class);
                    //   startActivity(intent);
                    // finish();
                } else {
                    // if (customLoaderDialog != null)
                    // customLoaderDialog.hide();
                    isUserExist = false;
                    UserModel userModel = new UserModel( userId.getText().toString(),userMob.getText().toString(), userName.getText().toString(),userEmail.getText().toString(), citySpinner.getSelectedItem().toString(), storeIdSpinner.getSelectedItem().toString());
                    firebase.setValue(userModel);
                    Prefs.setUserId(getApplication(), userModel.getUserId());
                    Prefs.setUSERNAME(getApplication(), userModel.getName());
                    Prefs.setMobile(getApplication(), userModel.getMob());
                    Prefs.setCity(getApplication(), userModel.getCity());
                    Prefs.setStoreid(getApplication(), userModel.getStoreId());
                    Prefs.setEmail(getApplication(), userModel.getEmailId());

                    Log.d("SignUpActivity","user created"+isUserExist);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //customLoaderDialog.hide();
                isUserExist = false;
                UserModel userModel = new UserModel( userId.getText().toString(),userMob.getText().toString(), userName.getText().toString(),userEmail.getText().toString(),citySpinner.getSelectedItem().toString(), storeIdSpinner.getSelectedItem().toString());
                Prefs.setUserId(getApplication(), userModel.getUserId());
                Prefs.setUSERNAME(getApplication(), userModel.getName());
                Prefs.setMobile(getApplication(), userModel.getMob());
                Prefs.setCity(getApplication(), userModel.getCity());
                Prefs.setStoreid(getApplication(), userModel.getStoreId());
                Prefs.setEmail(getApplication(), userModel.getEmailId());

                firebase.setValue(userModel);
                Log.d("SignUpActivity","user cancelled"+isUserExist);

            }

        });




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


    @Override
    public void onClick(View view) {


    /*    if (userId.getText().toString().equals("")||userEmail.getText().toString().equals("")
                ||userMob.getText().toString().equals("")||userName.getText().toString().equals("")||citySpinner.getSelectedItemPosition()==0||storeIdSpinner.getSelectedItemPosition()==0) {
            showMsgDialog("Warning!", "Fields cannot Be Empty", R.drawable.button, 0);
        }
        else {


            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    userMob.getText().toString(),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);

           // checkForUserAndSignuUp();
        }
*/
        switch (view.getId()) {
            case R.id.submit:
                if (!validatePhoneNumber()) {
                    return;
                }

                startPhoneNumberVerification("+91"+userMob.getText().toString());
                break;
            case R.id.verify:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
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

    private boolean validatePhoneNumber() {
        if (userId.getText().toString().equals("")||userEmail.getText().toString().equals("")
            ||userMob.getText().toString().equals("")||userName.getText().toString().equals("")||citySpinner.getSelectedItemPosition()==0||storeIdSpinner.getSelectedItemPosition()==0) {
        showMsgDialog("Warning!", "Fields cannot Be Empty", R.drawable.error_small, 0);
            return false;
    }
            return true;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser=userMob.getText().toString();
                            final DatabaseReference firebase = database.getReference().child("users").child(currentUser);
                            // Sign in success, update UI with the signed-in user's information
                            isUserLoggedIn=true;
                            Log.d("signup", "signInWithCredential:success");
                             showMsgDialog("Sign Up Completed","",R.drawable.success,1);
                            UserModel userModel = new UserModel( userId.getText().toString(),userMob.getText().toString(), userName.getText().toString(),userEmail.getText().toString(), citySpinner.getSelectedItem().toString(), storeIdSpinner.getSelectedItem().toString());
                            firebase.setValue(userModel);
                            Prefs.setUserId(getApplication(), userModel.getUserId());
                            Prefs.setUSERNAME(getApplication(), userModel.getName());
                            Prefs.setMobile(getApplication(), userModel.getMob());
                            Prefs.setCity(getApplication(), userModel.getCity());
                            Prefs.setStoreid(getApplication(), userModel.getStoreId());
                            Prefs.setEmail(getApplication(), userModel.getEmailId());
                            Prefs.setLoginStatus(SignUpActivity.this,isUserLoggedIn);
                            FirebaseUser user = task.getResult().getUser();
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
                           // startActivity(in);
                         //   finish();
                            // ...
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
