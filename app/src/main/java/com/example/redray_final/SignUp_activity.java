package com.example.redray_final;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_activity extends AppCompatActivity {
    Button sign_me_up;
    ProgressBar progressBar;
    EditText username,up_email,up_pass;

    Button NoInternetConnection;

    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    TextView forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_sign_up_activity);
        sign_me_up= findViewById(R.id.sign_me_up);

        up_email=findViewById(R.id.up_email);
        up_pass=findViewById(R.id.up_pass);
        progressBar = findViewById(R.id.progressBar);

        forgot_pass=findViewById(R.id.forgot_pass);
        mAuth = FirebaseAuth.getInstance();
        NoInternetConnection = findViewById(R.id.no_internet_connection);


        sign_me_up.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email_id=up_email.getText().toString().toUpperCase();
                String password=up_pass.getText().toString().toUpperCase();

                if(!haveNetworkConnection()){
                    NoInternetConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUp_activity.this, "Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                }
                else

                    NoInternetConnection.setVisibility(View.GONE);


                if(email_id.isEmpty())
                {
                    progressBar.setVisibility(View.GONE);
                    up_email.setError("Please Enter Email Address");
                }
                else if(password.isEmpty())
                {
                    progressBar.setVisibility(View.GONE);
                    up_pass.setError("Please Enter Password");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches())
                {
                    progressBar.setVisibility(View.GONE);
                    up_email.setError("Please Enter Valid Email Address");
                }
                else if(password.length()<6)
                {
                    progressBar.setVisibility(View.GONE);
                    up_pass.setError("Password have at least 6 characters");
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email_id, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userid=mAuth.getCurrentUser().getUid();
                                        fstore=FirebaseFirestore.getInstance();
                                            DocumentReference dr = fstore.collection("Users").document(userid);
                                            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot=task.getResult();
                                                        if(documentSnapshot.exists()) {
                                                            progressBar.setVisibility(View.GONE);
                                                            Intent intent = new Intent(SignUp_activity.this, MainActivity2.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });
                                            DocumentReference dr1 = fstore.collection("hospital").document(userid);
                                            dr1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot=task.getResult();
                                                        if(documentSnapshot.exists()) {
                                                            progressBar.setVisibility(View.GONE);
                                                            Intent intent = new Intent(SignUp_activity.this, MainActivity3.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }

                                                }
                                            });


                                    } else {

                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp_activity.this, "Sign In Fail", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }

        });
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!haveNetworkConnection()){
                    NoInternetConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUp_activity.this, "Something went wrong Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                }
                else {
                    NoInternetConnection.setVisibility(View.GONE);

                    EditText resetpass = new EditText(SignUp_activity.this);
                    AlertDialog.Builder passreset = new AlertDialog.Builder(SignUp_activity.this);
                    passreset.setTitle(Html.fromHtml("<font color='gray'>Password Forgot</font>"));
                    passreset.setMessage(Html.fromHtml("<font color='gray'>Enter your Email Id</font>"));
                    resetpass.setTextColor(Color.rgb(128, 128, 128));
                    passreset.setView(resetpass);
                    passreset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            progressBar.setVisibility(View.VISIBLE);

                            String mail = resetpass.getText().toString();

                            if (mail.isEmpty()) {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUp_activity.this, "Enter your Email Id", Toast.LENGTH_LONG).show();

                            } else {

                                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp_activity.this, "Reset Link send on your Email Id", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp_activity.this, "Reset Link is not send", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    });
                    passreset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    passreset.create().show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SignUp_activity.this,SignIn_activity.class);
        startActivity(intent);
    }

    public void ShowHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {

            if (up_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);


                //Show Password
                up_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                up_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}