package com.example.wave_20;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ПОДАРУНКОВИЙ on 12.03.2017.
 */
public class Fragment_proove extends Fragment {
    EventBus myEventBus;
    TextView prooveText;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_proove, null);

        myEventBus = EventBus.getDefault();
        EventBus.getDefault().register(this);

        prooveText = (TextView) v.findViewById(R.id.textViewProove) ;
        EventBus.getDefault().post(new DataEvent("send me total"));
         final ImageView button_next = (ImageView) v.findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("new buy");

                myRef.setValue(prooveText.getText().toString());

                prooveText.setText("Замовлення прийнято \n очікуйте");
                button_next.setVisibility(View.GONE);
            }
        });


        return v;
    }
    @Subscribe
    public void onGlobalEvent(TotalDataEvent event){
        Log.d("eventbus",event.message + " received");
        String res = event.message;
        res = res.substring(1, res.length() - 1);
        prooveText.setText(prooveText.getText()+" "+res);

    }
    @Override
    public void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}

