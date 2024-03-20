package com.carrentalrecord.group2.batch2.ui.dashboard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.carrentalrecord.group2.batch2.R;

import java.util.Calendar;

public class selectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);

    }


    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


//        Button b1;
//        Button b2 = binding.endDate;
//
//
//        if(b1.getText().toString() == "START DATE"){
//
//            b1.setText(i1+"/"+i2+"/"+i);
//
//        }else{
//
//            b2.setText(i1+"/"+i2+"/"+i);
//
//        }



    }

}
