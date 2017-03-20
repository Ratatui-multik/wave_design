package com.example.wave_20;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ПОДАРУНКОВИЙ on 12.03.2017.
 */
public class Fragment_end extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end, null);
        ImageView button_next = (ImageView) v.findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EventBus.getDefault().post(new DataEvent("i am done - fend say"));
            }
        });

        return v;
    }
}

