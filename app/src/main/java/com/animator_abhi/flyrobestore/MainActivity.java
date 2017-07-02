package com.animator_abhi.flyrobestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
 Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proceed= (Button) findViewById(R.id.submit);
        findViewById(R.id.submit).getBackground().setLevel(0);
    }
}
