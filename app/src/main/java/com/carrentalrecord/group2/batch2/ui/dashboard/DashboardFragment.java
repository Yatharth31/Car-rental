package com.carrentalrecord.group2.batch2.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.carrentalrecord.group2.batch2.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment implements com.carrentalrecord.group2.batch2.DashboardFragment {


private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        EditText name = binding.NameTextBox;
        EditText aadhaarNumberTextBox = binding.AadhaarTextBox;
        EditText carCompany = binding.CompanyTextBox;
        EditText carModel = binding.ModelTextBox;
        EditText address = binding.addressTextBox;

        Button startDate = binding.startDate;
        Button endDate = binding.endDate;
        Button discard = binding.discard;
        Button addOrder = binding.addOrder;

        CalendarView dateSelector = binding.dateSelect;

        final int[] dateSelection = {0};


        //Demo Starts Here
//
//        storeValue("111122223333","","111122223333",true);
//        storeValue("111122223333"+"Name","Demo Demo","111122223333",true);
//        storeValue("111122223333"+"Address","9404347906","111122223333",true);
//        storeValue("111122223333"+"Model","Maruti Suzuki" + " " + "800","111122223333",true);
//        storeValue("111122223333"+"Period","14/04/2002" + "  to  " + "14/04/2003","111122223333",true);
//        storeValue("111122223333"+"endDate","14/04/2003","111122223333",true);
//
//
//
//        if(valueExist("pendingOrders")){
//
//            String Orders = retriveValue("pendingOrders");
//            Orders = Orders + " " + "111122223333";
//            storeValue("pendingOrders",Orders,"111122223333",false);
//
//        }else{
//
//            storeValue("pendingOrders","111122223333","111122223333",false);
//        }


        //Demo Ends Here




        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateSelection[0] = 0;

                startDate.setText("START DATE");



                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                endDate.setText("END DATE");

                            }
                        },
                        yy, mm, dd);
                datePickerDialog.show();

            }
        });

        int previousTextCount[] = {0};

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String aadhaarNumber = aadhaarNumberTextBox.getText().toString();

                if (aadhaarNumber.length() == 4 && previousTextCount[0] != 5) {
                    aadhaarNumberTextBox.setText(aadhaarNumber + "-");
                } else if (aadhaarNumber.length() == 9 && previousTextCount[0] != 10) {
                    aadhaarNumberTextBox.setText(aadhaarNumber + "-");
                }else if(aadhaarNumber.length() == 5 && previousTextCount[0]==4){


                    aadhaarNumber = aadhaarNumber.substring(0,4) + "-" + aadhaarNumber.substring(4,5);
                    aadhaarNumberTextBox.setText(aadhaarNumber);

                }else if(aadhaarNumber.length()==10 && previousTextCount[0]==9){

                    aadhaarNumber = aadhaarNumber.substring(0,9) + "-" + aadhaarNumber.substring(9,10);
                    aadhaarNumberTextBox.setText(aadhaarNumber);

                }



                aadhaarNumberTextBox.setSelection(aadhaarNumberTextBox.getText().length());

                previousTextCount[0] = aadhaarNumberTextBox.getText().toString().length();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        aadhaarNumberTextBox.addTextChangedListener(textWatcher);


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(startDate.getText().toString()!="START DATE"){

                    endDate.setText("END DATE");


                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);



                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // on below line we are setting date to our edit text.
                                    endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

                                    Date strDate = null;
                                    Date str2Date = null;
                                    try {
                                        strDate = sdf.parse(endDate.getText().toString());
                                        str2Date = sdf2.parse(startDate.getText().toString());


                                        //Enabled this comment to add proper date.

//                        if(new Date().after(str2Date) || str2Date.after(strDate){
//
//
//
//                            //Date Cannot Be Today That's All
//
//                            Toast.makeText(getContext().getApplicationContext(),"Please enter valid dates.",Toast.LENGTH_SHORT).show();
//                            startDate.setText("START DATE");
//                            endDate.setText("END DATE");
//
//                        }
//                        else{
//                            //Something to do Idk
//
//
//                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext().getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            yy, mm, dd);
                    datePickerDialog.show();




                }else{

                    Toast.makeText(getContext().getApplicationContext(),"Please Select Start Date",Toast.LENGTH_SHORT).show();

                }

            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                aadhaarNumberTextBox.setText("");
                carCompany.setText("");
                carModel.setText("");
                address.setText("");
                startDate.setText("START DATE");
                endDate.setText("END DATE");


            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("") || ! name.getText().toString().trim().contains(" ")){

                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid name.",Toast.LENGTH_SHORT).show();

                }else if(valueExist(aadhaarNumberTextBox.getText().toString().replaceAll("-",""))) {

                    Toast.makeText(getContext().getApplicationContext(),"Your order already exist please complete pending orders.",Toast.LENGTH_LONG).show();

                }
                else if(aadhaarNumberTextBox.getText().toString().replace("-","").length() != 12){

                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid aadhaar card no.",Toast.LENGTH_SHORT).show();

                }else if(carCompany.getText().toString().equals("") || carModel.getText().toString().equals("")){

                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid company and model.",Toast.LENGTH_SHORT).show();

                }else if(address.getText().toString().equals("") || address.getText().toString().length()<=5){

                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid address.",Toast.LENGTH_SHORT).show();

                }else if (startDate.getText().toString().contains("START") || endDate.getText().toString().contains("END")){

                    Toast.makeText(getContext().getApplicationContext(),"Enter a valid date.",Toast.LENGTH_SHORT).show();

                }else{

                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-",""),"",aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);
                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-","")+"Name",name.getText().toString(),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);
                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-","")+"Address",address.getText().toString(),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);
                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-","")+"Model",carCompany.getText().toString() + " " + carModel.getText().toString(),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);
                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-","")+"Period",startDate.getText().toString() + "  to  " + endDate.getText().toString(),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);
                    storeValue(aadhaarNumberTextBox.getText().toString().replaceAll("-","")+"endDate",endDate.getText().toString(),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),true);



                    if(valueExist("pendingOrders")){

                        String Orders = retriveValue("pendingOrders");
                        Orders = Orders + " " + aadhaarNumberTextBox.getText().toString().replaceAll("-","");
                        storeValue("pendingOrders",Orders,aadhaarNumberTextBox.getText().toString().replaceAll("-",""),false);

                    }else{

                        storeValue("pendingOrders",aadhaarNumberTextBox.getText().toString().replaceAll("-",""),aadhaarNumberTextBox.getText().toString().replaceAll("-",""),false);
                    }

                    Toast.makeText(getContext().getApplicationContext(),"Order Successfully Recorded.",Toast.LENGTH_SHORT).show();


                }

            }
        });


        return root;
    }

    public void storeValue(String key,String data,String aadhaarNum,Boolean toUpload){

        SharedPreferences localStorage = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor localStorageWriter = localStorage.edit();
        localStorageWriter.putString(key, data);

        if(toUpload) {

            FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
            DatabaseReference storePending = myDatabase.getReference();
            storePending.child("pending").child(aadhaarNum).child(key.replace(aadhaarNum,"")).setValue(data);
        }
        localStorageWriter.apply();

    }

    public boolean valueExist(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        return sh.contains(key);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Toast.makeText(getContext().getApplicationContext(),"WellDone Bois",Toast.LENGTH_SHORT).show();

    }

    public String retriveValue(String key){

        SharedPreferences sh = this.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString(key,"None");
        return s1;

    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}






