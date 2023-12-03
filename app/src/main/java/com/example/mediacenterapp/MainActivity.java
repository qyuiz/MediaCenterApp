package com.example.mediacenterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int mNowFragment;

    private LinearLayout mSideMenuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSideMenuBar = findViewById(R.id.side_menu_bar);
    }

    public int getNowFragmentButtonId(){ return mNowFragment; }
    public void setNowFragmentButtonId(int resourceId){
        mNowFragment = resourceId;
    }

    public LinearLayout getSideMenuBar(){return mSideMenuBar;}
}