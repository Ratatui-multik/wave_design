package com.example.wave_20;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ПОДАРУНКОВИЙ on 12.03.2017.
 */
public class Fragment_location extends Fragment {

    EditText nameEdit;
    EditText telephonEdit;
    EditText cityEdit;
    EditText adressEdit;
    RadioButton rB;

    int hour;
    int day;
    int month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, null);
        Typeface keys = Typeface.createFromAsset(inflater.getContext().getAssets(),   getString(R.string.digit_keyboard_font));
        ImageView button_next = (ImageView) v.findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EventBus.getDefault().post(new DataEvent("\nТелефон: "+telephonEdit.getText()+"\nАдреса: "+adressEdit.getText()+"\nБажаний час: "
                        +String.valueOf(hour)+":00;"+String.valueOf(day)+"."+String.valueOf(month)));
            }
        });


        nameEdit = (EditText)v.findViewById(R.id.editName);
        nameEdit.setTypeface(keys);
        telephonEdit = (EditText) v.findViewById(R.id.editPhone);
        telephonEdit.setTypeface(keys);
        cityEdit = (EditText) v.findViewById(R.id.editCity);
        cityEdit.setTypeface(keys);
        adressEdit = (EditText) v.findViewById(R.id.editAdres);
adressEdit.setTypeface(keys);
        TextView text1 = (TextView)v.findViewById(R.id.textView);
        TextView text2 = (TextView)v.findViewById(R.id.textView2);
        TextView text3 = (TextView)v.findViewById(R.id.textView3);
        TextView text4 = (TextView)v.findViewById(R.id.textView4);
        text1.setTypeface(keys);
        text2.setTypeface(keys);
        text3.setTypeface(keys);
        text4.setTypeface(keys);

        rB = (RadioButton)v.findViewById(R.id.radioButton2);
        rB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                Log.d("dialog","checked rB");
                PickerDialog pd = PickerDialog.newInstance(5,12, 8);
                new PickerDialog();

                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        hour = selectedYear;
                        day = selectedDay;
                        month = selectedMonth;
                        Toast.makeText(getActivity(),"hour : " + selectedYear + " Month :" + selectedMonth + " Day:" + selectedDay,
                                Toast.LENGTH_LONG ).show();


                    }
                });
                pd.show(getFragmentManager(), "MonthYearPickerDialog");}
            }
        });
















        return v;
    }
    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView) {
                ((TextView)v).setTypeface(font);
            } else if(v instanceof ViewGroup) {
                setFont((ViewGroup) v, font);
            }
        }
    }
}

