package com.example.redray_final;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private  static  int SPLASH_SCREEN = 4000;
    ImageView logo,splashImg;
    TextView appName;
    LottieAnimationView lottieAnimationView;
    private FirebaseAuth mAuth;
    String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.splashImage);
        appName = findViewById(R.id.textView);
        lottieAnimationView= findViewById(R.id.lottie);

        splashImg.animate().translationY(-2500).setDuration(1000).setStartDelay(7000);
        logo.animate().translationY(2500).setDuration(1000).setStartDelay(7000);
        appName.animate().translationY(2500).setDuration(1000).setStartDelay(7000);
        lottieAnimationView.animate().translationY(2500).setDuration(1000).setStartDelay(7000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,SignIn_activity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

        if(mAuth.getCurrentUser()!=null) {
            Uid = mAuth.getCurrentUser().getUid();
            DocumentReference dr = db.collection("Users").document(Uid);
            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
            DocumentReference dr1 = db.collection("hospital").document(Uid);
            dr1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }
    }
}