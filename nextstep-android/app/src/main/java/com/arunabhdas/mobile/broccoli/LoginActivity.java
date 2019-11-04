package com.arunabhdas.mobile.broccoli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arunabhdas.mobile.broccoli.R;


public class LoginActivity extends Activity {
    private TextView vRegisterUser;
    private CardView vEmailSignInButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initViews();
        initListeners();

    }
    public void initViews() {
        vRegisterUser = (TextView) findViewById(R.id.register_user);
        vEmailSignInButton = (CardView) findViewById(R.id.email_sign_in_button);
    }

    public void initListeners() {
        vEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(mContext, MainActivity.class);
                startActivity(i);

            }
        });

        vRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(mContext, RegisterActivity.class);
                startActivity(i);

            }

            // TODO replace with alcatel login page?
        });


    }

}
