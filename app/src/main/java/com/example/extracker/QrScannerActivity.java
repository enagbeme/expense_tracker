package com.example.extracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScannerActivity extends AppCompatActivity {

    EditText scan_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        //set prompt text
        IntentIntegrator intentIntegrator = new IntentIntegrator(QrScannerActivity.this);
        intentIntegrator.setPrompt("To turn on flash, press volume up key");

        //set beep
        intentIntegrator.setBeepEnabled(true);

        //lock orientation
        intentIntegrator.setOrientationLocked(true);

        //set capture activity
        intentIntegrator.setCaptureActivity(Capture.class);

        //Initiate Scan
        intentIntegrator.initiateScan();


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        //Check if result content is not null
        if(intentResult.getContents()!=null){

            scan_result = findViewById(R.id.scanText);

            //set scan result content to textbox
            String result = intentResult.getContents();
            scan_result.setText(result);

        }else{
            //when result content is null, display toast
            Toast.makeText(getApplicationContext(), "oops i got nothing", Toast.LENGTH_SHORT).show();
        }
    }
}