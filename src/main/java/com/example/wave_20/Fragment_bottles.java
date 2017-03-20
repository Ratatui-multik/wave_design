package com.example.wave_20;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

public class Fragment_bottles extends Fragment {
 int price_buy = 30;
 int price_return = 5;
    TextView returned;
    TextView buy;
    TextView amount;
    ImageView button_next;
    String uah;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottles, null);

        uah = " грн";
        amount = (TextView)v.findViewById(R.id.textViewAmount);
         buy = (TextView)v.findViewById(R.id.textViewBuy);
         returned = (TextView)v.findViewById(R.id.textViewReturn);
        ImageView plus_buy = (ImageView)v.findViewById(R.id.imageViewPlusBuy);
        ImageView minus_buy = (ImageView)v.findViewById(R.id.imageViewMinusBuy);
        ImageView plus_returned = (ImageView)v.findViewById(R.id.imageViewPlusReturn);
        ImageView minus_return = (ImageView)v.findViewById(R.id.imageViewMinusReturn);

        button_next = (ImageView) v.findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EventBus.getDefault().post(new DataEvent("замовлено бутлів: "+buy.getText()+"\nбутлів на повернення: "+returned.getText()+"\nсумма замовлення: "+amount.getText()));
            }
        });

        View.OnClickListener mylistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageViewMinusBuy:
                        if(Integer.parseInt(amount.getText().toString())>price_buy) {
                            amount.setText(String.valueOf(Integer.parseInt(amount.getText().toString()) - price_buy));
                        }
                        if(Integer.parseInt(buy.getText().toString())>0) {
                            buy.setText(String.valueOf(Integer.parseInt(buy.getText().toString()) - 1));
                        }

                        break;

                    case R.id.imageViewPlusBuy:

                        amount.setText(String.valueOf(Integer.parseInt(amount.getText().toString())+price_buy));

                        buy.setText(String.valueOf(Integer.parseInt(buy.getText().toString()) + 1));

                        break;

                    case R.id.imageViewMinusReturn:
                        if(Integer.parseInt(returned.getText().toString())>0) {
                            amount.setText(String.valueOf(Integer.parseInt(amount.getText().toString()) + price_return));


                            returned.setText(String.valueOf(Integer.parseInt(returned.getText().toString()) - 1));
                        }

                        break;

                    case R.id.imageViewPlusReturn:

                        if(Integer.parseInt(amount.getText().toString())>price_return) {
                            amount.setText(String.valueOf(Integer.parseInt(amount.getText().toString()) - price_return));


                            returned.setText(String.valueOf(Integer.parseInt(returned.getText().toString()) + 1));
                        }
                        break;



                }

            }
        };

        plus_buy.setOnClickListener(mylistener);
        minus_buy.setOnClickListener(mylistener);
        plus_returned.setOnClickListener(mylistener);
        minus_return.setOnClickListener(mylistener);
        return v;
    }
}