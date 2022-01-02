package com.example.redray_final;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button UpdateButton;
    private ImageView BackButton;
    FirebaseFirestore fStore;

    Button NoInternetConnection;

    FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference,p_storage;
    private Uri imageUri;
    String userid_edit;
    EditText username1,add,landmark,pincode,no;
    Uri url;
    public String username_after,password_after,add_after,bg,pincode_after,landmark_after,mobileno;
    public String username_before,add_before,landmark_before,pin_before;
    TextView change_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        profileImageView = findViewById(R.id.EP_image);
        username1=findViewById(R.id.username1);
        add=findViewById(R.id.add);
        no=findViewById(R.id.no);
        UpdateButton=findViewById(R.id.update);
        landmark=findViewById(R.id.landmark);
        pincode=findViewById(R.id.pincode);
        change_pass=findViewById(R.id.change_pass);
        BackButton = findViewById(R.id.back_btn);
        FloatingActionButton profileChangeBtn = findViewById(R.id.fab_camera);


        //init

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        userid_edit=mAuth.getCurrentUser().getUid();

        NoInternetConnection = findViewById(R.id.no_internet_connection);

        p_storage=storage.getReference("images/"+userid_edit);
        p_storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri;
                Glide.with(EditProfile.this).load(url).into(profileImageView);
            }
        });

        DocumentReference dr=fStore.collection("Users").document(userid_edit);
        dr.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username1.setText(value.getString("username"));
                add.setText(value.getString("perment_add"));
                landmark.setText(value.getString("landmark"));
                pincode.setText(value.getString("pin_code"));
                bg=value.getString("bloodgroup");
                mobileno=value.getString("mo_n");

                username_before=username1.getText().toString();
                add_before=add.getText().toString();
                landmark_before=landmark.getText().toString();
                pin_before=pincode.getText().toString();

            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetpass=new EditText(EditProfile.this);
                AlertDialog.Builder passreset=new AlertDialog.Builder(EditProfile.this);

                passreset.setTitle(Html.fromHtml("<font color='gray'>Change Password</font>"));
                passreset.setMessage(Html.fromHtml("<font color='gray'>Enter your Email Id</font>"));
                resetpass.setTextColor(Color.rgb(128,128,128));
                passreset.setView(resetpass);
                passreset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail=resetpass.getText().toString();
                        if(mail==null){
                            resetpass.setError("Please enter Email Id");
                        }
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this,"Reset Link send on your Email Id",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this,"Reset Link is not send",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passreset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passreset.create().show();
            }
        });


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username_after=username1.getText().toString().toUpperCase();
                add_after=add.getText().toString().toUpperCase();
                landmark_after=landmark.getText().toString().toUpperCase();
                pincode_after=pincode.getText().toString().toUpperCase();

                Map<String,Object> data=new HashMap<>();
                data.put("perment_add",add_after);
                data.put("username",username_after);

                Map<String,Object> data1=new HashMap<>();
                data1.put("landmark",landmark_after);
                data1.put("perment_add",add_after);
                data1.put("pin_code",pincode_after);
                data1.put("username",username_after);

                if(!haveNetworkConnection()){
                    NoInternetConnection.setVisibility(View.VISIBLE);
                }
                if(username_after.isEmpty()){
                    username1.setError("Please Enter User Name");
                }
                else if(add_after.isEmpty()){
                    add.setError("Please Enter Address");
                }
                else if(landmark_after.isEmpty()){
                    landmark.setError("Please Enter Landmark");
                }
                else if(pincode_after.isEmpty()){
                    pincode.setError("Please Enter Pin Code");
                }
                else if(pincode_after.length()<6 || pincode_after.length()>6){
                    pincode.setError("Pincode must be 6 letters");
                }
                else if(!(landmark_before.equals(landmark_after)) || !(pin_before.equals(pincode_after))){

                    fStore.collection(bg).document(pincode_after).collection(landmark_after).document(mobileno).set(data);
                    fStore.collection(bg).document(pin_before).collection(landmark_before).document(mobileno).delete();
                    fStore.collection("Users").document(userid_edit).update(data1);
                }
                else if(!(username_after.equals(username_before)) || !(add_after.equals(add_before))){
                    fStore.collection(bg).document(pincode_after).collection(landmark_after).document(mobileno).set(data);
                    fStore.collection("Users").document(userid_edit).update(data1);
                }
            }
        });


        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                 imageUri = data.getData();
                profileImageView.setImageURI(imageUri);
                uploadPictures();
            }
        }
    }


    private void uploadPictures() {
        if(imageUri!=null){
            StorageReference reference=storageReference.child("images/"+ userid_edit);
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfile.this,"suceesfully upload",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfile.this,"Sorry, image not upload",Toast.LENGTH_LONG).show();
                }
            });
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
