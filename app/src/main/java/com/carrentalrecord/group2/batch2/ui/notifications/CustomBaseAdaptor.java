package com.carrentalrecord.group2.batch2.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carrentalrecord.group2.batch2.R;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomBaseAdaptor extends  BaseAdapter {

    Context context;
    String pendingOrders[];
    String constructT;
    LayoutInflater inflater;
    ArrayList<String> notificationNumbers = new ArrayList<>();
    ArrayList<String> selectedViews = new ArrayList<>();


    CustomBaseAdaptor(Context cxt, String[] orders, String constructTType) {

        this.context = cxt;
        this.pendingOrders = orders;
        this.inflater = LayoutInflater.from(cxt);
        this.constructT = constructTType;
    }


    @Override
    public int getCount() {
        return this.pendingOrders.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        String aadharNumber = pendingOrders[position].replaceAll("-", "");
        boolean atleastOneOrder = false;

        if (this.constructT.equals("pendingOrders")) {

            if (aadharNumber.length() == 12) {
                atleastOneOrder = true;

                convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
                TextView name = (TextView) convertView.findViewById(R.id.NameLW);
                TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
                ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
                CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

                name.setText(retriveValue(aadharNumber + "Name"));
                aadharCard.setText(pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                profile.setBackgroundResource(R.drawable.rentigo_icon);
                checkbox.setChecked(false);
                checkbox.setVisibility(View.INVISIBLE);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Aadhar Number", pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                        clipboard.setPrimaryClip(clip);
                        print("Copied Aadhaar Number");
                    }
                });

                return convertView;

            }

        }

        if (this.constructT.equals("completedOrders")) {

            convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
            TextView name = (TextView) convertView.findViewById(R.id.NameLW);
            TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
            ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

            name.setText("Under Development");
            aadharCard.setVisibility(View.INVISIBLE);
            profile.setVisibility(View.INVISIBLE);
            checkbox.setVisibility(View.INVISIBLE);

            return convertView;

        }

//        if(this.constructT.equals("dueOrders")){
//
//
//                   //new Date().after(str2Date
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date endDate = null;
//            try {
//                endDate = sdf.parse(retriveValue(aadharNumber + "endDate"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//            if (aadharNumber.length() == 12){
//
//
//                if(new Date().after(endDate)) {
//
//                    convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
//                    TextView name = (TextView) convertView.findViewById(R.id.NameLW);
//                    TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
//                    ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
//                    CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);
//
//                    name.setText(retriveValue(aadharNumber + "Name"));
//                    aadharCard.setText(pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
//                    profile.setBackgroundResource(R.drawable.rentigo_icon);
//                    checkbox.setChecked(false);
//
//                    convertView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ClipboardManager clipboard = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
//                            ClipData clip = ClipData.newPlainText("Aadhar Number", pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
//                            clipboard.setPrimaryClip(clip);
//                        }
//                    });
//
//                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                            if(b){
//
//                                try {
//                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){
//
//                                        notificationNumbers.add(retriveValue(pendingOrders[position] + "Address"));
//
//                                    }
//
//                                }catch (Exception e){
//
//                                    e.printStackTrace();
//                                }
//
//                            }else{
//
//                                try {
//
//                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){
//
//                                        notificationNumbers.remove(retriveValue(pendingOrders[position] + "Address"));
//
//                                    }
//
//                                }catch (Exception e){
//
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                        }
//                    });
//
//                    return convertView;
//
//                }
//
//            } else {
//
//                convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
//                TextView name = (TextView) convertView.findViewById(R.id.NameLW);
//                TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
//                ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
//                CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);
//
//                name.setText("No Records Found");
//                aadharCard.setVisibility(View.INVISIBLE);
//                profile.setVisibility(View.INVISIBLE);
//                checkbox.setVisibility(View.INVISIBLE);
//
//                return convertView;
//
//            }
//
//
//        }


        if(constructT.equals("dueOrders")){

            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date endDate = null;
            try {
                endDate = sdf.parse(retriveValue(aadharNumber + "endDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (aadharNumber.length() == 12){

                if(new Date().after(endDate)){

                    convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
                    TextView name = (TextView) convertView.findViewById(R.id.NameLW);
                    TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
                    ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
                    CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

                    name.setText(retriveValue(aadharNumber + "Name"));
                    aadharCard.setText(pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                    profile.setBackgroundResource(R.drawable.rentigo_icon);
                    checkbox.setChecked(false);

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Aadhar Number", pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                            clipboard.setPrimaryClip(clip);
                            print("Copied Aadhaar Number");
                        }
                    });


                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if(b){

                                try {
                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){

                                        notificationNumbers.add(retriveValue(pendingOrders[position] + "Address"));

                                    }else{

                                        print("Number not provided");

                                    }

                                }catch (Exception e){

                                    e.printStackTrace();
                                }

                            }else{

                                try {

                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){

                                        notificationNumbers.remove(retriveValue(pendingOrders[position] + "Address"));

                                    }else{
                                        print("Number not provided.");
                                    }

                                }catch (Exception e){

                                    e.printStackTrace();
                                }

                            }

                        }
                    });


                    return convertView;

                }

            } else {

                convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
                TextView name = (TextView) convertView.findViewById(R.id.NameLW);
                TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
                ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
                CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

                name.setText("No Records Found");
                aadharCard.setVisibility(View.INVISIBLE);
                profile.setVisibility(View.INVISIBLE);
                checkbox.setVisibility(View.INVISIBLE);

                return convertView;

            }


        }

        if(constructT.equals("selectAll")){

            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date endDate = null;
            try {
                endDate = sdf.parse(retriveValue(aadharNumber + "endDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (aadharNumber.length() == 12){

                if(new Date().after(endDate)){

                    convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
                    TextView name = (TextView) convertView.findViewById(R.id.NameLW);
                    TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
                    ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
                    CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

                    name.setText(retriveValue(aadharNumber + "Name"));
                    aadharCard.setText(pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                    profile.setBackgroundResource(R.drawable.rentigo_icon);
                    checkbox.setChecked(true);

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Aadhar Number", pendingOrders[position].substring(0, 4) + "-" + pendingOrders[position].substring(4, 8) + "-" + pendingOrders[position].substring(8));
                            clipboard.setPrimaryClip(clip);
                            print("Copied Aadhar Number");
                        }
                    });


                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if(b){

                                try {
                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){

                                        notificationNumbers.add(retriveValue(pendingOrders[position] + "Address"));

                                    }

                                }catch (Exception e){

                                    e.printStackTrace();
                                }

                            }else{

                                try {

                                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){

                                        notificationNumbers.remove(retriveValue(pendingOrders[position] + "Address"));

                                    }

                                }catch (Exception e){

                                    e.printStackTrace();
                                }

                            }

                        }
                    });

                    if(onlyDigits(retriveValue(pendingOrders[position] + "Address"))){

                        notificationNumbers.add(retriveValue(pendingOrders[position] + "Address"));

                    }


                    return convertView;

                }

            } else {

                convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
                TextView name = (TextView) convertView.findViewById(R.id.NameLW);
                TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
                ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
                CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

                name.setText("No Records Found");
                aadharCard.setVisibility(View.INVISIBLE);
                profile.setVisibility(View.INVISIBLE);
                checkbox.setVisibility(View.INVISIBLE);

                return convertView;

            }


        }
        if(position==0) {
            convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
            TextView name = (TextView) convertView.findViewById(R.id.NameLW);
            TextView aadharCard = (TextView) convertView.findViewById(R.id.aadharCardLW);
            ImageView profile = (ImageView) convertView.findViewById(R.id.imageLW);
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxLW);

            name.setText("No Records Found");
            aadharCard.setVisibility(View.INVISIBLE);
            profile.setVisibility(View.INVISIBLE);
            checkbox.setVisibility(View.INVISIBLE);

            return convertView;

        }else{
            convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
            RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
            params.height = 0;
            return  convertView;
        }

    }

    public ArrayList<String> getAllSelected(){

        return this.selectedViews;

    }

    public void setAll(){

    }


    public String retriveValue(String key){

        SharedPreferences sh = this.context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
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

    public void print(String message){

        Toast.makeText(this.context.getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
}
