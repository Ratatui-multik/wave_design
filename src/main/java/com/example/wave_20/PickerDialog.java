package com.example.wave_20;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

/**
 * Created by ПОДАРУНКОВИЙ on 13.03.2017.
 */
public class PickerDialog extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int daysOfMonth = 31;

    private NumberPicker monthPicker;
    private NumberPicker hourPicker;
    private NumberPicker dayPicker;

    private Calendar cal = Calendar.getInstance();

    public static final String MONTH_KEY = "monthValue";
    public static final String DAY_KEY = "dayValue";
    public static final String HOUR_KEY = "hourValue";

    int monthVal = -1 , dayVal = -1 , hourVal =-1 ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if(extras != null){
            monthVal = extras.getInt(MONTH_KEY , -1);
            dayVal = extras.getInt(DAY_KEY , -1);
            hourVal = extras.getInt(HOUR_KEY , -1);
        }
    }

    public static PickerDialog newInstance(int monthIndex , int daysIndex , int hourIndex) {
      PickerDialog f = new PickerDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(MONTH_KEY, monthIndex);
        args.putInt(DAY_KEY, daysIndex);
        args.putInt(HOUR_KEY, hourIndex);
        f.setArguments(args);

        return f;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //getDialog().setTitle("Select your Birthday Date");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.picker, null);
        monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        hourPicker = (NumberPicker) dialog.findViewById(R.id.picker_hour);
        dayPicker = (NumberPicker) dialog.findViewById(R.id.picker_day);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);


        if(monthVal != -1)// && (monthVal > 0 && monthVal < 13))
            monthPicker.setValue(monthVal);
        else
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        monthPicker.setDisplayedValues(new String[]{"Jan","Feb","Mar","Apr","May","June","July",
                "Aug","Sep","Oct","Nov","Dec"});


        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(daysOfMonth);

        if(dayVal != -1)
            dayPicker.setValue(dayVal);
        else
            dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal){
                    case 1:case 3:case 5:
                    case 7:case 8:case 10:
                    case 12:
                        daysOfMonth = 31;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;
                    case 2:
                        daysOfMonth = 28;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;

                    case 4:case 6:
                    case 9:case 11:
                        daysOfMonth = 30;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;
                }

            }
        });

        int maxHour = 23;
        final int minHour = 0;
        int arraySize = maxHour - minHour;

        String[] tempArray = new String[arraySize];
        tempArray[0] = "1";
        int tempYear = minHour+1;

        for(int i=0 ; i < arraySize; i++){
            if(i != 0){
                tempArray[i] = " " + tempYear + "";
            }
            tempYear++;
        }
        Log.i("", "onCreateDialog: " + tempArray.length);
        hourPicker.setMinValue(minHour+1);
        hourPicker.setMaxValue(maxHour);
        hourPicker.setDisplayedValues(tempArray);

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    if(isLeapYear(picker.getValue())){
                        daysOfMonth = 29;
                        dayPicker.setMaxValue(daysOfMonth);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int year = hourPicker.getValue();

                        listener.onDateSet(null, year, monthPicker.getValue(), dayPicker.getValue());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       PickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static boolean isLeapYear2(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }
}
