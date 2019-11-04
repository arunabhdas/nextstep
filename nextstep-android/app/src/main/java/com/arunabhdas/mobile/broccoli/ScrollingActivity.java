package com.arunabhdas.mobile.broccoli;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = ScrollingActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    private String mImeiOne = null;
    private String mImeiTwo = null;
    private String mImsiOne;
    private String mImsiTwo;
    private TextView mImeiOneText;
    private TextView mImeiTwoText;
    private TextView mImsiOneText;
    private TextView mImsiTwoText;
    private static final int PERMISSION_READ_STATE = 999;
    private String mOperatorISO3;
    TelephonyManager mTel;
    SubscriptionManager mSub= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mImeiOneText = (TextView) findViewById(R.id.imei_one);
        mImeiTwoText = (TextView) findViewById(R.id.imei_two);
        mImsiOneText = (TextView) findViewById(R.id.imsi_one);
        mImsiTwoText = (TextView) findViewById(R.id.imsi_two);

        if (ContextCompat.checkSelfPermission(ScrollingActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }

        mTel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT > 21) {
            mSub = SubscriptionManager.from(mContext);
        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mImeiOne = mTel.getDeviceId(0);
                    mImeiTwo = mTel.getDeviceId(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT > 21 && mSub != null) {
                    try {
                        List<SubscriptionInfo> subscriptionList = mSub.getActiveSubscriptionInfoList();
                        if (subscriptionList != null && subscriptionList.size() > 0) {
                            for (SubscriptionInfo s : subscriptionList) {
                                int subIndex = s.getSimSlotIndex();
                                if (subIndex == 0) {
                                    final int mccOne = s.getMcc();
                                    final int mncOne = s.getMnc();
                                    mImsiOne = mccOne + "...";

                                } else if (subIndex == 1) {
                                    final int mccTwo = s.getMcc();
                                    final int mncTwo = s.getMnc();
                                    mImsiTwo = mccTwo + "...";
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, ex.toString());
                    }

                }

                Snackbar.make(view, " Retrieved IMSI as " + mTel.getSubscriberId(), Snackbar.LENGTH_LONG)
                        .setAction("Info", null).show();

                mImeiOneText.setText("IMEI 1 : " + mImeiOne);
                mImeiTwoText.setText("IMEI 2 : " + mImeiTwo);
                mImsiOneText.setText("IMSI 1 :" + mImsiOne);
                mImsiTwoText.setText("IMSI 2 : " + mImsiTwo);


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_postman) {
            Intent intent = new Intent(ScrollingActivity.this, PostmanActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                } else {
                    // permission denied
                }
                return;
            }

        }
    }
}
