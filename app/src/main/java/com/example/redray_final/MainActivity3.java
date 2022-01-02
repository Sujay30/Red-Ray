package com.example.redray_final;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    TextView number;
    Button rndnum_btn;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        number=findViewById(R.id.random_number);
        rndnum_btn=findViewById(R.id.generate_code);
        fstore=FirebaseFirestore.getInstance();

        rndnum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int num=(int)(Math.random()*9000)+1000;
                String id=""+num;
                number.setText(id);
                Map<String,String> data=new HashMap<>();
                data.put("Number",id.toString());
                fstore.collection("Random_number").document(id).set(data);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity3.this)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle(Html.fromHtml("<font color='gray'>Exit</font>"))
                .setMessage(Html.fromHtml("<font color='gray'>Are you sure you want to exit this app</font>"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}