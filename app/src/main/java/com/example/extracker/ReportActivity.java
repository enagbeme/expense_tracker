package com.example.extracker;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.extracker.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    private AppCompatButton btn_ex_report, btn_bud_report;
    ImageButton btn_back;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private static final int PERMISSION_REQUEST_CODE = 200;
    int pageHeight = 1120;
    int pageWidth = 792;

    public static final String SHARED_PREFERS="sharedPrefs";
    String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btn_ex_report = findViewById(R.id.btn_ex_report);
        btn_bud_report = findViewById(R.id.btn_bud_report);
        btn_back = findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                SharedPreferences preferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("UserName", users.getUsername());
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
        user_name = sharedPreferences.getString("UserName","");



        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }



        btn_ex_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();


        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        title.setTextSize(30);
        title.setColor(Color.GREEN);


        canvas.drawText("EX TRACKER", 209, 60, title);
        canvas.drawText("Project By LYNN AND DAISY", 209, 100, title);


        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setColor(Color.GREEN);
        title.setTextSize(40);


        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("USERNAME: " + user_name.toUpperCase(Locale.ROOT), 396, 560, title);



        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(), "ExReport.pdf");

        try {

            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(ReportActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            e.printStackTrace();
        }

        pdfDocument.close();


    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}