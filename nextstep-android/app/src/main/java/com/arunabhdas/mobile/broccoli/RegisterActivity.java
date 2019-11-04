package com.arunabhdas.mobile.broccoli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.arunabhdas.mobile.broccoli.R;

public class RegisterActivity extends Activity{
    private CardView vRegisterButton;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        initView();
        initListner();

    }
    public void initView() {
        vRegisterButton = (CardView) findViewById(R.id.register_button);
    }
    private void initListner() {
        vRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
