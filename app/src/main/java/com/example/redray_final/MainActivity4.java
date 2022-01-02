package com.example.redray_final;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity4 extends AppCompatActivity {
    TextView username1,username2,username3,username4,username5,username6,username7,username8,username9,username10;
    TextView no1,no2,no3,no4,no5,no6,no7,no8,no9,no10;
    ImageButton i1,i2,i3,i4,i5,i6,i7,i8,i9,i10;
    Button more_data;
    int l=0,max;
    String [] name=new String[100];
    String [] no=new String[100];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        username1=findViewById(R.id.user1);
        username2=findViewById(R.id.user2);
        username3=findViewById(R.id.user3);
        username4=findViewById(R.id.user4);
        username5=findViewById(R.id.user5);
        username6=findViewById(R.id.user6);
        username7=findViewById(R.id.user7);
        username8=findViewById(R.id.user8);
        username9=findViewById(R.id.user9);
        username10=findViewById(R.id.user10);
        no1=findViewById(R.id.no1);
        no2=findViewById(R.id.no2);
        no3=findViewById(R.id.no3);
        no4=findViewById(R.id.no4);
        no5=findViewById(R.id.no5);
        no6=findViewById(R.id.no6);
        no7=findViewById(R.id.no7);
        no8=findViewById(R.id.no8);
        no9=findViewById(R.id.no9);
        no10=findViewById(R.id.no10);
        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);
        i5=findViewById(R.id.i5);
        i6=findViewById(R.id.i6);
        i7=findViewById(R.id.i7);
        i8=findViewById(R.id.i8);
        i9=findViewById(R.id.i9);
        i10=findViewById(R.id.i10);
        more_data=findViewById(R.id.more_data);
        show_data();
        more_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(max>l) {
                    username1.setVisibility(View.GONE);
                    username2.setVisibility(View.GONE);
                    username3.setVisibility(View.GONE);
                    username4.setVisibility(View.GONE);
                    username5.setVisibility(View.GONE);
                    username6.setVisibility(View.GONE);
                    username7.setVisibility(View.GONE);
                    username8.setVisibility(View.GONE);
                    username9.setVisibility(View.GONE);
                    username10.setVisibility(View.GONE);
                    no1.setVisibility(View.GONE);
                    no2.setVisibility(View.GONE);
                    no3.setVisibility(View.GONE);
                    no4.setVisibility(View.GONE);
                    no5.setVisibility(View.GONE);
                    no6.setVisibility(View.GONE);
                    no7.setVisibility(View.GONE);
                    no8.setVisibility(View.GONE);
                    no9.setVisibility(View.GONE);
                    no10.setVisibility(View.GONE);
                    i1.setVisibility(View.GONE);
                    i2.setVisibility(View.GONE);
                    i3.setVisibility(View.GONE);
                    i4.setVisibility(View.GONE);
                    i5.setVisibility(View.GONE);
                    i6.setVisibility(View.GONE);
                    i7.setVisibility(View.GONE);
                    i8.setVisibility(View.GONE);
                    i9.setVisibility(View.GONE);
                    i10.setVisibility(View.GONE);
                    show_data();
                }
            }
        });

    }

    public void show_data(){
        Intent intent=getIntent();
        String s=intent.getStringExtra("max");
        max=Integer.parseInt(s);
        for(int j=l;j<l+10;j++) {
            name[j]= intent.getStringExtra("username"+j);
            no[j] = intent.getStringExtra("no"+j);
        }

        if(name[l]!=null){
            username1.setText(name[l]);
            no1.setText(no[l]);
            username1.setVisibility(View.VISIBLE);
            no1.setVisibility(View.VISIBLE);
            i1.setVisibility(View.VISIBLE);
            call_num(i1,no[l]);
            l++;

        }
        if(name[l]!=null){
            username2.setText(name[l]);
            no2.setText(no[l]);
            username2.setVisibility(View.VISIBLE);
            no2.setVisibility(View.VISIBLE);
            i2.setVisibility(View.VISIBLE);
            call_num(i2,no[l]);
            l++;
        }
        if(name[l]!=null){
            username3.setText(name[l]);
            no3.setText(no[l]);
            username3.setVisibility(View.VISIBLE);
            no3.setVisibility(View.VISIBLE);
            i3.setVisibility(View.VISIBLE);
            call_num(i3,no[l]);
            l++;
        }
        if(name[l]!=null){
            username4.setText(name[l]);
            no4.setText(no[l]);
            username4.setVisibility(View.VISIBLE);
            no4.setVisibility(View.VISIBLE);
            i4.setVisibility(View.VISIBLE);
            call_num(i4,no[l]);
            l++;
        }
        if(name[l]!=null){
            username5.setText(name[l]);
            no5.setText(no[l]);
            username5.setVisibility(View.VISIBLE);
            no5.setVisibility(View.VISIBLE);
            i5.setVisibility(View.VISIBLE);
            call_num(i5,no[l]);
            l++;
        }
        if(name[l]!=null){
            username6.setText(name[l]);
            no6.setText(no[l]);
            username6.setVisibility(View.VISIBLE);
            no6.setVisibility(View.VISIBLE);
            i6.setVisibility(View.VISIBLE);
            call_num(i6,no[l]);
            l++;
        }
        if(name[l]!=null){
            username7.setText(name[l]);
            no7.setText(no[l]);
            username7.setVisibility(View.VISIBLE);
            no7.setVisibility(View.VISIBLE);
            i7.setVisibility(View.VISIBLE);
            call_num(i7,no[l]);
            l++;
        }
        if(name[l]!=null){
            username8.setText(name[l]);
            no8.setText(no[l]);
            username8.setVisibility(View.VISIBLE);
            no8.setVisibility(View.VISIBLE);
            i8.setVisibility(View.VISIBLE);
            call_num(i8,no[l]);
            l++;
        }
        if(name[l]!=null){
            username9.setText(name[l]);
            no9.setText(no[l]);
            username9.setVisibility(View.VISIBLE);
            no9.setVisibility(View.VISIBLE);
            i9.setVisibility(View.VISIBLE);
            call_num(i9,no[l]);
            l++;
        }
        if(name[l]!=null){
            username10.setText(name[l]);
            no10.setText(no[l]);
            username10.setVisibility(View.VISIBLE);
            no10.setVisibility(View.VISIBLE);
            i10.setVisibility(View.VISIBLE);
            call_num(i10,no[l]);
            l++;

        }
        if(max==l){
            more_data.setVisibility(View.GONE);
            Toast.makeText(MainActivity4.this, "No More Donors Available", Toast.LENGTH_SHORT).show();
        }


    }

    private void call_num(ImageButton i1, String s) {
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Build.VERSION.SDK_INT >22){
                        if(ActivityCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity4.this,new String[]{Manifest.permission.CALL_PHONE},101);

                        }

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + s));
                        startActivity(intent);
                    }
                    else {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + s));
                        startActivity(intent);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(MainActivity4.this,"failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}