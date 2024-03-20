package com.carrentalrecord.group2.batch2.ui.home;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.carrentalrecord.group2.batch2.R;
import com.carrentalrecord.group2.batch2.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HomeFragment extends Fragment {



private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        try {

            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        }catch (Exception e){

            System.out.println(e.getMessage());
        }



        Dialog popUpScreen;
        popUpScreen = new Dialog(getContext());




        final Button search = binding.searchRecord;
        final EditText queryAadharfield = binding.editTextAadharNumber;
        final ImageView profileImage = binding.profileImage;
        final LinearLayout resultLayout = binding.queryResultLayout;
        final LinearLayout searchLayout = binding.searchContainingLayout;
        final Button completeOrder = binding.completeOrder;
        final Button discardResult = binding.discardResult;

        TextView nameLabel = binding.nameLabel;
        TextView aadhaarCardLabel = binding.aadhaarCardLabel;
        TextView addressLabel = binding.addressLabel;
        TextView carTypeLabel = binding.carTypeLabel;
        TextView commissionPeriodLabel = binding.commissionPeriodLabel;

        final int[] previousTextCount = {0};


        //Demo Runs Data For The Project

//        storeValue("111111111111","");
//        storeValue("111111111111"+"Name","Anurag Tekam");
//        storeValue("111111111111"+"Address","Near Vit Chowk Bibwewadi");
//        storeValue("111111111111"+"Model","Maruti Suzuki 800");
//        storeValue("111111111111"+"Period","24-02-2023  to  29-02-2023");



        discardResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);

            }
        });


        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);

                Toast.makeText(requireContext().getApplicationContext(),"Transaction Successfully Completed",Toast.LENGTH_SHORT).show();


                String pendingOrders = retriveValue("pendingOrders");
                ArrayList<String> pO = new ArrayList<>();
                pO.addAll(Arrays.asList(pendingOrders.split(" ")));
                pO.remove(queryAadharfield.getText().toString().replaceAll("-",""));
                pendingOrders = "";

                for(String s: pO){

                    pendingOrders = pendingOrders + s + " ";

                }

                pendingOrders = pendingOrders.trim();
                storeValue("pendingOrders",pendingOrders);

                databaseCompleteOrder(queryAadharfield.getText().toString().replaceAll("-",""));
                removeRecord(queryAadharfield.getText().toString().replaceAll("-",""));




            }
        });



        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    String aadhaarNumber = queryAadharfield.getText().toString();

                    if (aadhaarNumber.length() == 4 && previousTextCount[0] != 5) {
                        queryAadharfield.setText(aadhaarNumber + "-");
                    } else if (aadhaarNumber.length() == 9 && previousTextCount[0] != 10) {
                        queryAadharfield.setText(aadhaarNumber + "-");
                    }else if(aadhaarNumber.length() == 5 && previousTextCount[0]==4){


                        aadhaarNumber = aadhaarNumber.substring(0,4) + "-" + aadhaarNumber.substring(4,5);
                        queryAadharfield.setText(aadhaarNumber);

                    }else if(aadhaarNumber.length()==10 && previousTextCount[0]==9){

                        aadhaarNumber = aadhaarNumber.substring(0,9) + "-" + aadhaarNumber.substring(9,10);
                        queryAadharfield.setText(aadhaarNumber);

                    }


                    queryAadharfield.setSelection(queryAadharfield.getText().length());

                    previousTextCount[0] = queryAadharfield.getText().toString().length();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        queryAadharfield.addTextChangedListener(textWatcher);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryNumber =  queryAadharfield.getText().toString();
                aadhaarCardLabel.setText("Aadhaar Number : " + queryNumber);
                queryNumber = queryNumber.replaceAll("-",
                        "");


                if(queryNumber.length() == 12){
                    if(valueExist(queryNumber)){

                        nameLabel.setText("Name : " + retriveValue(queryNumber+"Name"));
                        //aadhaarCardLabel.setText("Aadhaar Number : " + queryNumber);
                        addressLabel.setText("Address : " + retriveValue(queryNumber + "Address"));
                        carTypeLabel.setText("Car Model : " + retriveValue(queryNumber+"Model"));
                        commissionPeriodLabel.setText("Period : " + retriveValue(queryNumber+"Period"));

                        resultLayout.setVisibility(View.VISIBLE);
                        searchLayout.setVisibility(View.GONE);

                        //popUpScreen.setContentView(resultLayout);



//                        Glide.with(getContext())
//                                .load("https://images.unsplash.com/profile-1446404465118-3a53b909cc82?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=3ef46b07bb19f68322d027cb8f9ac99f")
//                                .into(profileImage);
//                        profileImage.setVisibility(View.VISIBLE);


                        if(valueExist(queryNumber+"Image")){

                            Glide.with(getContext())
                                    .load(queryNumber+"Image")
                                    .into(profileImage);
                            profileImage.setVisibility(View.VISIBLE);


                        }else{

                            profileImage.setBackgroundResource(R.drawable.rentigo_icon);
                            profileImage.setVisibility(View.VISIBLE);
                        }




                    }else{
                        resultLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext().getApplicationContext(),"The query doesn't exist in records.",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    resultLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid aadhaar card no.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;




    }



    public void storeValue(String key,String data){

        SharedPreferences localStorage = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor localStorageWriter = localStorage.edit();
        localStorageWriter.putString(key, data);

        localStorageWriter.apply();

    }

    public void removeRecord(String key){

        SharedPreferences localStorage = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor localStorageWriter = localStorage.edit();


        localStorageWriter.remove(key);
        localStorageWriter.apply();

    }


    public String retriveValue(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString(key,"");
        return s1;

    }

    public boolean valueExist(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        return sh.contains(key);

    }

    public void databaseCompleteOrder(String aadharNumber){

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference pending = mDatabase.getReference("pending");
        DatabaseReference completedOrders = mDatabase.getReference("completed");
        String temp[] = new String[1];

        int allClear = 0;

        completedOrders.child("currentOrderId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    temp[0] = String.valueOf(task.getResult().getValue());
                    completedOrders.child("currentOrderId").setValue(String.valueOf(Integer.parseInt(temp[0]) +  1));
                }
            }
        });

        pending.child(aadharNumber).child("Name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeInCompleted(aadharNumber,String.valueOf(task.getResult().getKey()),String.valueOf(task.getResult().getValue()),temp[0]);

                }
            }
        });

        pending.child(aadharNumber).child("Address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeInCompleted(aadharNumber,String.valueOf(task.getResult().getKey()),String.valueOf(task.getResult().getValue()),temp[0]);

                }
            }
        });

        pending.child(aadharNumber).child("Model").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeInCompleted(aadharNumber,String.valueOf(task.getResult().getKey()),String.valueOf(task.getResult().getValue()),temp[0]);

                }
            }
        });

        pending.child(aadharNumber).child("Period").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeInCompleted(aadharNumber,String.valueOf(task.getResult().getKey()),String.valueOf(task.getResult().getValue()),temp[0]);

                }
            }
        });

        pending.child(aadharNumber).child("endDate").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(getContext().getApplicationContext(),"Firebase : No Value Error",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeInCompleted(aadharNumber,String.valueOf(task.getResult().getKey()),String.valueOf(task.getResult().getValue()),temp[0]);

                }
            }
        });


        pending.child(aadharNumber).removeValue();


    }

    public void storeInCompleted(String aadharNumber, String key,String data,String orderId){

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference completed = mDatabase.getReference("completed");
        completed.child(orderId).child(aadharNumber).child(key).setValue(data);

    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}