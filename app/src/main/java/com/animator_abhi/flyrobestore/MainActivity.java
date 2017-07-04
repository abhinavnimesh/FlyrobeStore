package com.animator_abhi.flyrobestore;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.animator_abhi.flyrobestore.Model.TransactionModel;
import com.animator_abhi.flyrobestore.utils.Constants;
import com.animator_abhi.flyrobestore.utils.Prefs;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mswipe.wisepad.apkkit.WisePadController;

public class MainActivity extends BaseActivity {
 Button proceed;
    EditText edtCreditAmount, edtCustomeName, edtPhoneNo, edtReceipt, edtEmail, edtNotes;
    String  mSwipeId, mSwipePassword, customerName,creditAmount, phoneNo, receipt, email, notes;
    Button btnPay;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        proceed= (Button) findViewById(R.id.submit);
        findViewById(R.id.submit).getBackground().setLevel(0);
        edtCreditAmount = (EditText) findViewById(R.id.amount);
        edtCustomeName = (EditText) findViewById(R.id.customer);
        edtPhoneNo= (EditText) findViewById(R.id.mob);
        edtReceipt= (EditText) findViewById(R.id.receipt);
        edtEmail= (EditText) findViewById(R.id.email);
        edtNotes= (EditText) findViewById(R.id.note);

        btnPay= (Button) findViewById(R.id.submit);

    }

    @Override
    protected void initData() {


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditAmount=edtCreditAmount.getText().toString();
                customerName=edtCustomeName.getText().toString();
                phoneNo=edtPhoneNo.getText().toString();
                receipt=edtReceipt.getText().toString();
                email=edtEmail.getText().toString();
                notes=edtNotes.getText().toString();
                mSwipeId=Prefs.getMasterId(getApplication());
                mSwipePassword=Prefs.getMasterPass(getApplication());


               // firebase.push().setValue(transactionModel);
                mWisePadController.processCardSale(
                        mSwipeId,
                        mSwipePassword,
                        creditAmount,
                        phoneNo,
                        receipt,
                        email,
                        notes,
                        "store1",
                        "warehouse1",
                        "city1",
                        Constants.isSignatureRequired,
                        Constants.isProduction,
                        Constants.mOrientation,
                        Constants.isSPrinterSupported,
                        Constants.isPrintSignatureOnReceipt);
            }
        });

    }
    private void successDialogPayment(String msg, final String rrNo, final String authCode) {
        final Dialog myDialog = new Dialog(this);

        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialog_card_success);
        myDialog.setCancelable(false);
        final TextView ok = (TextView) myDialog.findViewById(R.id.btn_db_ok);
        final TextView tvMsg = (TextView) myDialog.findViewById(R.id.db_msg);
        final TextView tvTitle = (TextView) myDialog.findViewById(R.id.db_title);
        ImageView ivImg = (ImageView) myDialog.findViewById(R.id.ivImg);
        tvTitle.setText("Payment Successfull!");
        tvMsg.setText(msg);

        ivImg.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bold_font));

     /*   mPicassoClient.load(R.drawable.success).placeholder(R.drawable.image_placeholder_vertical)
                .fit().centerInside().into(ivImg);*/


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,HomeActivity.class);
                i.putExtra("flag",1);
                startActivity(i);
                finish();
               // changePaymentStatus(myDialog, prformaId, rrNo, authCode,amount);
            }
        });


        myDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Constants.IS_DEBUGGING_ON)
           // Logs.v(getPackageName(), ">>>>>>>entered in Activity Result", true, true);

        if (resultCode == RESULT_OK && requestCode == WisePadController.MS_CARDSALE_ACTIVITY_REQUEST_CODE) {
           final DatabaseReference firebase;
            firebase = database.getReference().child("transactions").child(Prefs.getMobile(this));
            String rrNo = data.getExtras().getString("RRNo");
            String authCode = data.getExtras().getString("AuthCode");
            String tvr = data.getExtras().getString("TVR");
            String tsi = data.getExtras().getString("TSI");
            TransactionModel transactionModel;
            transactionModel=new TransactionModel(creditAmount,customerName,phoneNo,email
                    ,receipt,notes,Prefs.getUserId(MainActivity.this),Prefs.getUSERNAME(MainActivity.this),rrNo,authCode);
            firebase.push().setValue(transactionModel);
          //  LogUtil.d("##CARD-REQUESTCODE", requestCode + "--" + resultCode);



            if (data.hasExtra("signatureUpdateStatus")) {
                if (data.getExtras().getBoolean("signatureUpdateStatus")) {
                    Constants.mshowDialog(MainActivity.this, Constants.CARDSALE_DIALOG_MSG, "Status" + "\n\n" + "Approved" + "\n" + "RR No. " + rrNo + "\n" + "Auth Code " + authCode + "\n" + "TVR " + tvr + "\n" + "TSI " + tsi + "\n" + "signature update success", 2);
                } else {
                    Constants.mshowDialog(MainActivity.this, Constants.CARDSALE_DIALOG_MSG, "Status" + "\n\n" + "Approved" + "\n" + "RR No. " + rrNo + "\n" + "Auth Code " + authCode + "\n" + "TVR " + tvr + "\n" + "TSI " + tsi + "\n" + "signature update failed: reason " + data.getExtras().getString("errMsg"), 2);
                }
            } else {
                String receiptDetails = data.getExtras().getString("receiptDetail");
                //String msg="Status" + "\n\n" + "Approved" + "\n" + "RR No. " + RRno + "\n" + "Auth Code " + authCode + "\n" + "TVR " + tvr + "\n" + "TSI " + tsi + "\n\n"+ "Receipt details" +"\n\n"+receiptDetails;
                //Constants.showDialog(CardPayActivity.this, Constants.CARDSALE_DIALOG_MSG,  "Status" +"\n\n"+ "Approved" +"\n"+ "RR No. " + RRno +"\n"+ "Auth Code " + authCode +"\n"+ "TVR " + tvr +"\n"+ "TSI " + tsi +"\n\n"+ "Receipt details" +"\n\n"+ receiptDetails,4,prformaId);
                //Constants.showDialog(CardPayActivity.this, Constants.CARDSALE_DIALOG_MSG,msg , 4, prformaId);

                String msg = "Status :" + " Approved :" + "\n" + "RR No. :" + rrNo + "\n" + "Auth Code :" + authCode + "\n";
                successDialogPayment(msg, rrNo, authCode);
            }

        } else if (resultCode == RESULT_CANCELED) {

           // LogUtil.d("##CARD-REQUESTCODE", requestCode + "--" + resultCode);
            if (data != null && data.hasExtra("errMsg")) {

                Constants.mshowDialog(MainActivity.this, Constants.CARDSALE_DIALOG_MSG, data.getExtras().getString("errMsg"), 1);

            }
        }
    }

}
