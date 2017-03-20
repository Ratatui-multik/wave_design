package com.example.wave_20;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    Fragment_start startsWindow;
    Fragment_bottles bottlesWindow;
    Fragment_location locationWindow;
    Fragment_special specialWindow;
    Fragment_proove prooveWindow;
    Fragment_end endWindow;
    ArrayList<Fragment> fragmentStack;
    ArrayList<String> dataStack;

    FragmentTransaction fTrans;
    EventBus myEventBus;

    int fCounter;
    String globalTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);

        startsWindow = new Fragment_start();
        bottlesWindow = new Fragment_bottles();
        locationWindow = new Fragment_location();
        specialWindow = new Fragment_special();
        prooveWindow = new Fragment_proove();
        endWindow = new Fragment_end();




        myEventBus = EventBus.getDefault();
        EventBus.getDefault().register(this);

        fragmentStack = new ArrayList<>();
        fragmentStack.add(startsWindow);
        fragmentStack.add(bottlesWindow);
        fragmentStack.add(locationWindow);
        fragmentStack.add(specialWindow);
        fragmentStack.add(prooveWindow);
        fragmentStack.add(endWindow);

        drawStartFragment();

        //getActionBar().hide()
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        try{

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(fragmentStack.get(fCounter));
                dataStack.remove(fCounter-1);
                ft.add(R.id.frgmCont,fragmentStack.get(fCounter-1));
                fCounter--;

                ft.commit();


        }catch (Exception e){}

    }

    @Override
    public void onStart(){
        super.onStart();


    }
    @Subscribe
    public void onEvent(DataEvent event){
        if(event.message.equals("send me total")){sendTotal();}
        else {
            Log.d("eventbus", event.message);
            dataStack.add(event.message);
            globalTotal += event.message + "\n";
            changeFragment();
        }
    }
    private void drawStartFragment() {
        fTrans = getFragmentManager().beginTransaction();
        fCounter=0;
        dataStack = new ArrayList<>();
        globalTotal="";
        fTrans.add(R.id.frgmCont,fragmentStack.get(fCounter));
        fTrans.commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        switch (id) {
            case R.id.itemMenuCall:
                callHuman();
                return true;
            case R.id.itemMenuMail:
                sendMail();
                return true;
            case R.id.itemMenuSite:
                goToSite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Kalevych_tech@ukr.net"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Тема:");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Текст:");
        emailIntent.setType("text/plain");
        startActivity(emailIntent);
    }

    public void goToSite() {
    }

    public void callHuman() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0377778888"));

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);


    }


    private void sendTotal() {
        EventBus.getDefault().post(new TotalDataEvent(dataStack.toString()));
    }

    public void changeFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(fragmentStack.get(fCounter));

        ft.add(R.id.frgmCont,fragmentStack.get(fCounter+1));
        fCounter++;

        ft.commit();

    }



    @Override
    public void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

}






