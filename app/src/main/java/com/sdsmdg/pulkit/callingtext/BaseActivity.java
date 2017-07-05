package com.sdsmdg.pulkit.callingtext;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class BaseActivity extends FragmentActivity implements ActionBar.TabListener, GifFragment.onImageselectionListener {


    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    Button btn_settings;
    public static String mName, mNumber;
    public static Boolean calledByapp = false;
    private TabLayout tabLayout;
    public static int[] imageIds;
    SessionManager session;  //session manager class
    public static String receiver = "7248187747";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //to initialise all the global variables
        initVariables();
        session.checkLogIn();//check login session
        setListenersAndAdapters();
        setImageIds();

        TelephonyManager telephoneManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = telephoneManager.getLine1Number();
        Log.e("MY BA NO.", "PHONE NO." + mPhoneNumber);

        int pg_number = 0;

        if (getIntent().getExtras() != null) {
            try {
                pg_number = Integer.parseInt(getIntent().getExtras().getString("pagenumber"));

            } catch (NumberFormatException num) {
                Log.i("EXCEpTION", num.toString());
            }
        }
        viewPager.setCurrentItem(pg_number);



        tabLayout.setupWithViewPager(viewPager);
        startService(new Intent(this, BackgroundService.class));
    }

    private void call(String s) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + s));
        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BaseActivity.this, "yourActivity is not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onImageSelection(String position) {
        Log.e("in Imageselection", "in !!!!");
        Log.e("in null", getSupportFragmentManager().getFragments().get(0).getTag());
        NewFragment newFragment = (NewFragment)
                getSupportFragmentManager().getFragments().get(2
                );

        if (newFragment != null) {
            Log.e("in null", "in null");
            newFragment.setImage(Integer.parseInt(position));
        }
    }

    /**
     * This function sets up adapters and click listeners
     */
    private void setListenersAndAdapters(){
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(BaseActivity.this, Settings.class);
                startActivity(settingsIntent);
            }
        });

        viewPager.setAdapter(mAdapter);

    }

    /**
     * This function initializes various new variables
     */
    private void initVariables(){
        //session class instance
        session = new SessionManager(getApplicationContext());
        btn_settings = (Button) findViewById(R.id.btn_settings);
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    /**
     * This function sets up the image ids.
     */
    private void setImageIds(){
        imageIds=new int[14];
        imageIds[0] = R.drawable.birthday;
        imageIds[1] = R.drawable.confused;
        imageIds[2] = R.drawable.funny;
        imageIds[3] = R.drawable.embares;
        imageIds[4] = R.drawable.angry;
        imageIds[5] = R.drawable.machau;
        imageIds[6] = R.drawable.sorry;
        imageIds[7] = R.drawable.hii;
        imageIds[8] = R.drawable.hello;
        imageIds[9] = R.drawable.love;
        imageIds[10] = R.drawable.compliment;
        imageIds[11] = R.drawable.happy;
        imageIds[12] = R.drawable.sad;
        imageIds[13] = R.drawable.crying;
    }


    public static String getmName() {
        return mName;
    }

    public static void setmName(String mName) {
        BaseActivity.mName = mName;
    }

    public static String getmNumber() {
        return mNumber;
    }

    public static void setmNumber(String mNumber) {
        BaseActivity.mNumber = mNumber;
    }

}
