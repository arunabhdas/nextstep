package com.arunabhdas.mobile.flopro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.arunabhdas.mobile.broccoli.R;

/**
 * Created by Das on 9/1/17.
 */

public class FloProvSettingsActivity extends Activity {
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_flo_settings);
    }
}
