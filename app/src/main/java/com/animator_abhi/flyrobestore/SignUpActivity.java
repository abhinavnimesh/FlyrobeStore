package com.animator_abhi.flyrobestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {
    String[] city=new String[]{"City1","city1","city1","city1","city1"};
    String[] warehouse=new String[]{"Store1","Store1","Store1","Store1","Store1",};

    Spinner s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        s1= (Spinner) findViewById(R.id.citySpinner);
        s2= (Spinner) findViewById(R.id.storeSpinner);
        ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,
              R.layout.spinner_text, city);
        ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(this,
                R.layout.spinner_text, warehouse);
        s1.setAdapter(adapter1);
        s2.setAdapter(adapter2);
    }
}
