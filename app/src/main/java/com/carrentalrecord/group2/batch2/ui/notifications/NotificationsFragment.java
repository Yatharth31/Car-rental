package com.carrentalrecord.group2.batch2.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.carrentalrecord.group2.batch2.MainActivity;
import com.carrentalrecord.group2.batch2.R;
import com.carrentalrecord.group2.batch2.databinding.FragmentNotificationsBinding;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;

ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

    binding = FragmentNotificationsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


    Button notifyButton = binding.notifyButton;
    Button selectAllButton = binding.selectAllButton;
    WebView webView = binding.webview;


    ArrayList<String> numbers = new ArrayList<>();

    notifyButton.setVisibility(View.GONE);
    selectAllButton.setVisibility(View.GONE);


    listView = binding.customListView;

        final CustomBaseAdaptor[] customBaseAdaptor = {new CustomBaseAdaptor(getContext().getApplicationContext(), retriveValue("pendingOrders").split(" "), "pendingOrders")};

    listView.setAdapter(customBaseAdaptor[0]);
    listView.setClickable(true);




    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

            Toast.makeText(getContext().getApplicationContext(),"Got Here",Toast.LENGTH_SHORT);

            TextView Name = (TextView) view.findViewById(R.id.aadharCardLW);
            CheckBox notifyit = (CheckBox) view.findViewById(R.id.checkBoxLW);

            if(notifyit.isChecked()){

                if(onlyDigits(retriveValue(Name.getText().toString().replaceAll("-","") + "Address"))){

                    numbers.add(retriveValue(Name.getText().toString().replaceAll("-","") + "Address"));

                }

            }else{

                if(onlyDigits(retriveValue(Name.getText().toString().replaceAll("-","") + "Address"))){

                    numbers.remove(retriveValue(Name.getText().toString().replaceAll("-","") + "Address"));

                }

            }
        }

    });


        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                boolean connected;
                for(String number : customBaseAdaptor[0].notificationNumbers){

                    connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                    if(! connected) {

                        Toast.makeText(getContext().getApplicationContext(), "No Internet Connection! SMS Not Sent.", Toast.LENGTH_SHORT).show();

                    }

                    Toast.makeText(getContext().getApplicationContext(), "Sending SMS", Toast.LENGTH_SHORT).show();



                    webView.loadUrl("https://www.fast2sms.com/dev/bulkV2?authorization=s7QM0kG8zI8dj9UGUDsJ0cm3Y7veZXKvtOWMkUVEliv18vzjecqviGI55uWd&message=" +  "Your%20car%20rent%20date%20is%20long%20due%20please%20renew%20or%20complete%20the%20order%20immediately." + " +&language=english&route=q&numbers=" + number);
                    SystemClock.sleep(5000);

                }
//
//                ArrayList<String> Orders =  customBaseAdaptor.getAllSelected();
//
//                for(String order:Orders){
//                    Toast.makeText(getContext().getApplicationContext(),order,Toast.LENGTH_SHORT);
//
//                    RequestQueue volleyQueue = Volley.newRequestQueue(getContext());
//
//                    String url = "https://www.fast2sms.com/dev/bulkV2?authorization=s7QM0kG8zI8dj9UGUDsJ0cm3Y7veZXKvtOWMkUVEliv18vzjecqviGI55uWd&message= + " +  "Your%20car%20rent%20date%20is%20long%20due%20please%20renew%20or%20complete%20the%20order%20immediately." +" +&language=english&route=q&numbers=" + order;
//
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//
//                            Request.Method.GET,
//
//                            url,
//                            null,
//                            (Response.Listener<JSONObject>) response -> {
//                                String returnMessage;
//                                try {
//                                   returnMessage  = response.getString("message");
//                                   if (returnMessage.equals("Message sent successfully")){
//                                       Toast.makeText(getContext().getApplicationContext(),"SMS sent Succesfully.",Toast.LENGTH_SHORT).show();
//                                   }else{
//                                       Toast.makeText(getContext().getApplicationContext(),"SMS not sent.",Toast.LENGTH_SHORT).show();
//                                   }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            },
//                            (Response.ErrorListener) error -> {
//                                Toast.makeText(getContext(), "Some error occurred! Cannot send messages", Toast.LENGTH_LONG).show();
//                                // log the error message in the error stream
//                                Log.e("MainActivity", "error: ${error.localizedMessage}");
//                            }
//                    );
//
//                }

            }
        }
        );

        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {

                        customBaseAdaptor[0] = new CustomBaseAdaptor(requireContext().getApplicationContext(), retriveValue("pendingOrders").split(" "), "selectAll");

                        listView.setAdapter(customBaseAdaptor[0]);
                        listView.invalidateViews();

                    }catch (Exception e){

                        Toast.makeText(getContext().getApplicationContext(),"No Items To Select",Toast.LENGTH_SHORT).show();
                    }

            }
        });


    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        String[] arraySpinner = new String[] {
                "Pending Orders","Completed Orders","Due Orders"
        };

        Spinner filterRecords = binding.filterRecords;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterRecords.setAdapter(adapter);

        filterRecords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                notifyButton.setVisibility(View.GONE);
                selectAllButton.setVisibility(View.GONE);

                if(filterRecords.getSelectedItem().toString().equals("Pending Orders")) {

                    customBaseAdaptor[0] = new CustomBaseAdaptor(getContext().getApplicationContext(), retriveValue("pendingOrders").split(" "), "pendingOrders");


                    listView.setAdapter(customBaseAdaptor[0]);
                    listView.invalidateViews();


                }
                else if(filterRecords.getSelectedItem().toString().equals("Completed Orders")){

                    customBaseAdaptor[0] = new CustomBaseAdaptor(getContext().getApplicationContext(), retriveValue("completedOrders").split(" "), "completedOrders");

                    listView.setAdapter(customBaseAdaptor[0]);
                    listView.invalidateViews();

                }else{
                    customBaseAdaptor[0] = new CustomBaseAdaptor(getContext().getApplicationContext(), retriveValue("pendingOrders").split(" "), "dueOrders");
                    listView.setAdapter(customBaseAdaptor[0]);
                    listView.invalidateViews();

                    notifyButton.setVisibility(View.VISIBLE);
                    selectAllButton.setVisibility(View.VISIBLE);

                }


                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                // Nothing to change
            }

        });



    return root;

    }

    public String retriveValue(String key){

        SharedPreferences sh = getContext().getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString(key,"");
        return s1;

    }


    public static boolean
    onlyDigits(String str)
    {
        int i = 0;
        for (i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return i == 10;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}