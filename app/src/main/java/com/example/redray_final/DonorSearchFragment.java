package com.example.redray_final;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class DonorSearchFragment extends Fragment {
    /******************/ EditText Patient_bg,hos_add,hos_land,hos_pin,hos_id;
    Button search_donor;
    FirebaseAuth mAuth;
    public int i=0,m,x=0;
    FirebaseFirestore fStore;/**********/
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fregment_search_donor,null);
        /***************/
        Patient_bg=(EditText)v.findViewById(R.id.Patient_bg);
        hos_add=(EditText)v.findViewById(R.id.hos_add);
        hos_land=(EditText)v.findViewById(R.id.hos_land);
        hos_pin=(EditText)v.findViewById(R.id.hos_pin);
        hos_id=(EditText)v.findViewById(R.id.hos_id);
        search_donor=(Button)v.findViewById(R.id.search_donor_button);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        /***********/
        /************/

        search_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p_bg=Patient_bg.getText().toString().toUpperCase();
                String h_add=hos_add.getText().toString();
                String h_land=hos_land.getText().toString();
                String h_pin=hos_pin.getText().toString();
                String h_id=hos_id.getText().toString();
                /*************************/


                if(p_bg.isEmpty()){
                    Patient_bg.setError("Please Enter BloodGroup Type");
                }
                else if(h_add.isEmpty()){
                    hos_add.setError("Please Enter Hospital Address");
                }
                else if(h_land.isEmpty()){
                    hos_land.setError("Please Enter Hospital Landmark");
                }
                else if(h_pin.isEmpty()){
                    hos_pin.setError("Please Enter Pin code");
                }
                else if(h_id.isEmpty()){
                    hos_id.setError("Please Enter Hospital Id");
                }
                else if(!(p_bg.equals("A+")) && !(p_bg.equals("A-")) && !(p_bg.equals("B+")) && !(p_bg.equals("B-")) && !(p_bg.equals("AB+")) &&
                        !(p_bg.equals("AB-")) && !(p_bg.equals("O+")) && !(p_bg.equals("O-"))){
                    Patient_bg.setError("Please Enter Valid Blood Group");
                }
                else {

                    DocumentReference dr=fStore.collection("Random_number").document(h_id);
                    dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot=task.getResult();
                                if(documentSnapshot.exists()){

                                    fStore.collection("Random_number").document(h_id).delete();
                                    String [] name=new String[100];
                                    String [] phone=new String[100];
                                    int [] no=new int[100];

                                    CollectionReference cr = fStore.collection(p_bg).document(h_pin).collection(h_land);
                                    cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                String s=documentSnapshot.getId();
                                                String username=documentSnapshot.getString("username");
                                                name[i]=username;
                                                phone[i]=s;
                                                i++;

                                            }
                                            Intent intent = new Intent(getActivity(), MainActivity4.class);
                                            intent.putExtra("max",i+"");
                                            for (int j = 0; j < i; j++) {
                                                intent.putExtra("username" + j, name[j]);
                                                intent.putExtra("no" + j, phone[j]);
                                            }
                                            startActivity(intent);

                                        }

                                    });
                                }
                                else {
                                    hos_id.setError("Please Enter Valid Hospital Id");
                                }
                            }
                        }
                    });


                }
                /*************************/
            }
        });

        /*************/
        return v;
    }



}

