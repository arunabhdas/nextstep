package com.arunabhdas.mobile.broccoli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.arunabhdas.mobile.broccoli.R;

/**
 * Created by Das on 9/6/17.
 */

public class SettingsActivity extends Activity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_settings);
    }
}
