package com.example.redray_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class

MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navView;
    Button NoInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main2);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        NoInternetConnection = findViewById(R.id.no_internet_connection);


        if(null==savedInstanceState) {
            loadFragment(new HomeFragment());
        }

        if(!haveNetworkConnection()){
            Toast.makeText(MainActivity2.this, "Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
            NoInternetConnection.setVisibility(View.VISIBLE);
        }
    }

    private boolean loadFragment(Fragment fragment){


        if(!haveNetworkConnection()){

            Toast.makeText(MainActivity2.this, "Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
            NoInternetConnection.setVisibility(View.VISIBLE);
        }
        else
            NoInternetConnection.setVisibility(View.GONE);

        if(fragment!= null)
        {
            getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.nav_host_fragment,fragment).
                    commit();
            return true;
        }

        return false;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.navigation_home:
                 fragment = new HomeFragment();
                break;

            case R.id.donor_search:
                fragment = new DonorSearchFragment();
                break;

            case R.id.help:
                fragment = new HelpFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if(navView.getSelectedItemId()==R.id.navigation_home) {

            new AlertDialog.Builder(MainActivity2.this)
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
        else {
            navView.setSelectedItemId(R.id.navigation_home);
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