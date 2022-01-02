package com.example.redray_final;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    EditText username,p_add,landmark_fetch,pincode_fetch,email,Blood,Mobile_no;
    CircleImageView profile_image;
    String userId;
    Uri url;
    Button editProfile,sign_out;
    private Activity mActivity;
    private FirebaseStorage mStore;
    private StorageReference p_storage;
    TextView full_name;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mActivity = (Activity)context;
        }
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,null);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mStore = FirebaseStorage.getInstance();
        username=(EditText)v.findViewById(R.id.username1);
        p_add=(EditText)v.findViewById(R.id.P_addr);
        email=(EditText)v.findViewById(R.id.email);
        Blood=(EditText)v.findViewById(R.id.Bloodg);
        Mobile_no=(EditText)v.findViewById(R.id.Mobile_no);
        userId=mAuth.getCurrentUser().getUid();
        editProfile = v.findViewById(R.id.edit_profile);
        sign_out=v.findViewById(R.id.sign_out);
        landmark_fetch=v.findViewById(R.id.lanmark_fetch);
        pincode_fetch=v.findViewById(R.id.pincode_fetch);
        full_name=v.findViewById(R.id.full_name);


        profile_image=(CircleImageView)v.findViewById(R.id.profile_image);

        p_storage = mStore.getReference("images/"+userId);

        p_storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri;
                Glide.with(mActivity).load(url).into(profile_image);
            }
        });

        DocumentReference dr=fStore.collection("Users").document(userId);
        dr.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                full_name.setText(value.getString("username"));
                username.setText(value.getString("username"));
                p_add.setText(value.getString("perment_add"));
                landmark_fetch.setText(value.getString("landmark"));
                pincode_fetch.setText(value.getString("pin_code"));
                email.setText(value.getString("email"));
                Blood.setText(value.getString("bloodgroup"));
                Mobile_no.setText(value.getString("mo_n"));

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),EditProfile.class);
                startActivity(intent);

            }
        });
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle(Html.fromHtml("<font color='gray'>Sign Out</font>"))
                        .setMessage(Html.fromHtml("<font color='gray'>Are you sure you want to Sign out this app</font>"))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAuth.signOut();
                                Intent intent=new Intent(getActivity(),SignIn_activity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });
        return v;
    }

}
