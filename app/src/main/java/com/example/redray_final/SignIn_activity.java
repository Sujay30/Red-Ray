package com.example.redray_final;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignIn_activity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView gosignUp;
    Button sign_me_in;
    Button NoInternetConnection;
    EditText username, add, landmark1, password, email, bloodg, no, pin;
    private FirebaseAuth mAuth;
    String Uid;/***********************/


    /***********************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_sign_in_activity);
        gosignUp = findViewById(R.id.gosignup);
        sign_me_in = findViewById(R.id.sign_me_in);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);



        if (mAuth.getCurrentUser() != null) {
            finish();
        }


        gosignUp = findViewById(R.id.gosignup);
        sign_me_in = findViewById(R.id.sign_me_in);
        username = findViewById(R.id.username1);
        add = findViewById(R.id.add);                                           
        landmark1 = findViewById(R.id.landmark);
        pin = findViewById(R.id.pin);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        bloodg = findViewById(R.id.bloodg);
        no = findViewById(R.id.no);
        NoInternetConnection = findViewById(R.id.no_internet_connection);

        gosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn_activity.this, SignUp_activity.class);
                startActivity(intent);
                finish();
            }
        });
        sign_me_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String usern = username.getText().toString().toUpperCase();
                String pass = password.getText().toString().toUpperCase();
                String p_add = add.getText().toString().toUpperCase();
                String land_m = landmark1.getText().toString().toUpperCase();
                String pin_code = pin.getText().toString().toUpperCase();
                String email_id = email.getText().toString();
                String bg = bloodg.getText().toString().toUpperCase().toUpperCase();
                String mo_n = no.getText().toString().toUpperCase();

                if(!haveNetworkConnection()){

                    Toast.makeText(SignIn_activity.this, "Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                    NoInternetConnection.setVisibility(View.VISIBLE);
                }
                else

                    NoInternetConnection.setVisibility(View.GONE);

                if (usern.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    username.setError("Please Enter UserName");
                } else if (pass.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    password.setError("Please Enter Password");
                } else if (p_add.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    add.setError("Please Enter Permanent address");
                } else if (land_m.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    landmark1.setError("Please Enter Landmark");
                } else if (pin_code.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    pin.setError("Please Enter PinCode");
                } else if (email_id.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    email.setError("Please Enter Email-ID");
                } else if (bg.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    bloodg.setError("Please Enter Blood-Group");
                } else if (mo_n.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    no.setError("Please Enter Mobile Number");
                } else if (pass.length() < 6) {
                    progressBar.setVisibility(View.GONE);
                    password.setError("Password at least 6 Characters");
                } else if (!(bg.equals("A+")) && !(bg.equals("A-")) && !(bg.equals("B+")) && !(bg.equals("B-")) && !(bg.equals("AB+")) &&
                        !(bg.equals("AB-")) && !(bg.equals("O+")) && !(bg.equals("O-"))) {
                    progressBar.setVisibility(View.GONE);
                    bloodg.setError("Please Enter Valid Blood Group");
                } else if ((pin_code.length() < 6) || (pin_code.length() > 6)) {
                    progressBar.setVisibility(View.GONE);
                    pin.setError("Pin Code have 6 Characters");
                } else if (mo_n.length() < 10 || mo_n.length() > 10) {
                    progressBar.setVisibility(View.GONE);
                    no.setError("Mobile Number have 10 digits");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                    progressBar.setVisibility(View.GONE);
                    email.setError("Please Enter a valid Email Address");
                } else {

                    mAuth.createUserWithEmailAndPassword(email_id, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Map<String, Object> data = new HashMap<>();
                                data.put("username", usern);
                                data.put("password", pass);
                                data.put("perment_add", p_add);

                                db.collection(bg).document(pin_code).collection(land_m).document(mo_n).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignIn_activity.this, "Login Success", Toast.LENGTH_LONG).show();
                                    }
                                });
                                String userid = mAuth.getCurrentUser().getUid();
                                Map<String, Object> data_user = new HashMap<>();
                                data_user.put("username", usern);
                                data_user.put("password", pass);
                                data_user.put("perment_add", p_add);
                                data_user.put("landmark", land_m);
                                data_user.put("pin_code", pin_code);
                                data_user.put("email", email_id);
                                data_user.put("bloodgroup", bg);
                                data_user.put("mo_n", mo_n);
                                db.collection("Users").document(userid).set(data_user);

                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(SignIn_activity.this, MainActivity2.class);
                                startActivity(intent);
                                finish();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignIn_activity.this, "Login fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void ShowHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {

            if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);


                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

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