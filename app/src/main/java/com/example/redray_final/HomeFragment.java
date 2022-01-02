package com.example.redray_final;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    List<ModelClass> mList;
    CustomAdepter customAdepter;

    @SuppressLint("InflateParams")
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home,null);
        recyclerView = v.findViewById(R.id.recyclerView);
        customAdepter = new CustomAdepter(mList,getContext());
        recyclerView.setAdapter(customAdepter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return  v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();
        mList.add(new ModelClass(R.drawable.bd_1,"1.Overall health- The donor must be fit and healthy, and should not be suffering from transmittable diseases."));
        mList.add(new ModelClass(R.drawable.bd_2,"2.The reason to donate is simple…it helps save lives. In fact, every two seconds of every day, someone needs blood."));
        mList.add(new ModelClass(R.drawable.bd_3,"3Our blood is our life force: It gives us oxygen, nutrients and infection-fighting agents.."));
        mList.add(new ModelClass(R.drawable.bd_4,"4.People lying in a hospital giving a blood donation. Group of patients in row, selective focus on the smiling young Caucasian brunette."));
        mList.add(new ModelClass(R.drawable.bd_5,"5.Person giving blood, close-up - stock photo"));
        mList.add(new ModelClass(R.drawable.bd_6,"6.Blood bag on hospital stand with copy space - stock photo\n" +
                "Blood bag hanging on hospital stand"));
        mList.add(new ModelClass(R.drawable.bd_7,"7.Bags of donated blood hanging in processing facility of blood bank - stock photo"));
        mList.add(new ModelClass(R.drawable.bd_16,"Regular blood donation is linked to lower blood pressure and a lower risk for heart attacks. “It definitely helps to reduce cardiovascular risk factors,” says Dr. DeSimone."));
        mList.add(new ModelClass(R.drawable.bd_9,"9.Blood Research is a peer-reviewed open-access journal and delivers important clinical, translational and basic study results in hematology and related fields to the readers worldwide. "));
        mList.add(new ModelClass(R.drawable.bd_10,"10.On an average our country requires 5 crore units of blood every year, out of which only a meager of 2.5 Crore unit of blood is available."));
        mList.add(new ModelClass(R.drawable.bd_11,"11.Just 1 donation can save up to 3 lives. The average red blood cell transfusion is 3 pints (or 3 whole-blood donations)."));
        mList.add(new ModelClass(R.drawable.bd_12,"12.14th June is being celebrated as World Blood Donor Day across the globe. This year Greece will be the local host for the event and theme for the day will be “Blood Connects Us All."));
        mList.add(new ModelClass(R.drawable.bd_13,"13.To donate blood or platelets, you must be in good general health, weigh at least 110 pounds, and be at least 16 years old. "));
        mList.add(new ModelClass(R.drawable.bd_14,"14.More than 200 000 units of whole blood had to be thrown away after Americans donated 500 000 extra units in September and October. Donated blood is discarded if it remains unused after 42 days."));
        mList.add(new ModelClass(R.drawable.bd_15,"15.Red cells are stored in refrigerators at 6ºC for up to 42 days. "));

        mList.add(new ModelClass(R.drawable.bd_17,"17.Giving blood is part of a heart-healthy lifestyle and donating on a regular basis has proven health benefits."));
    }
}
