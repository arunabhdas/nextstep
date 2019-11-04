package com.arunabhdas.mobile.flopro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.joaquimley.faboptions.FabOptions;
import com.arunabhdas.mobile.broccoli.R;

/**
 * Created by Das on 9/8/17.
 */

public class FloProvActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView eidTextView;
    private Context mContext;
    private FabOptions mFabOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flo_prov);
        mContext = this;
        mFabOptions = (FabOptions) findViewById(R.id.fab_options);
        mFabOptions.setButtonsMenu(R.menu.menu_floprov);
        mFabOptions.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.faboptions_first:
                mFabOptions.setButtonColor(R.id.faboptions_first, R.color.colorPrimary);
                // Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
                final Intent intentOne = new Intent(this, FloProvScanActivity.class);
                startActivity(intentOne);
                break;

            case R.id.faboptions_second:
                mFabOptions.setButtonColor(R.id.faboptions_second, R.color.colorPrimary);
                // Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
                final Intent intentTwo = new Intent(this, FloProvScanActivity.class);
                startActivity(intentTwo);
                break;


            case R.id.faboptions_third:
                mFabOptions.setButtonColor(R.id.faboptions_third, R.color.colorPrimary);
                // Toast.makeText(this, "Download", Toast.LENGTH_SHORT).show();
                final Intent intentThree = new Intent(this, FloProvScanActivity.class);
                startActivity(intentThree);
                break;


            case R.id.faboptions_fourth:
                mFabOptions.setButtonColor(R.id.faboptions_fourth, R.color.colorPrimary);
                // Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                final Intent intentFour = new Intent(this, FloProvScanActivity.class);
                startActivity(intentFour);
                break;

            case R.id.faboptions_fifth:
                mFabOptions.setButtonColor(R.id.faboptions_fifth, R.color.colorPrimary);
                // Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                final Intent intentFive = new Intent(this, FloProvScanActivity.class);
                startActivity(intentFive);
                break;

            case R.id.faboptions_sixth:
                mFabOptions.setButtonColor(R.id.faboptions_sixth, R.color.colorPrimary);
                // Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                final Intent intentSix = new Intent(this, FloProvScanActivity.class);
                startActivity(intentSix);
                break;

            default:
                // no-op
        }
    }

}
