package com.arunabhdas.mobile.broccoli;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arunabhdas.mobile.adapter.OnboardingPageAdapter;
import com.arunabhdas.mobile.adapter.TutorialPageAdapter;


public class OnboardingActivity extends Activity {
    private ViewPager vOnboardingViewPager;
    private TutorialPageAdapter mOnboardingPageAdapter;
    private Button vExploreButton;
    private Button vRegisterButton;
    private Button vLoginButton;
    private RelativeLayout vButtonsLayout;
    private ImageView vOnboardingPageNumber;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mContext = this;
        initView();
        initPager();
        initListeners();
        requestLocationPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        vOnboardingViewPager = (ViewPager) findViewById(R.id.onboarding_pager);
        vOnboardingPageNumber = (ImageView) findViewById(R.id.onboarding_page_number);
        // vExploreButton = (Button) findViewById(R.id.onboarding_explore_button);
        vRegisterButton = (Button) findViewById(R.id.onboarding_register_button);
        vLoginButton = (Button) findViewById(R.id.onboarding_login_button);
        vButtonsLayout = (RelativeLayout) findViewById(R.id.onboarding_button_container);
        vButtonsLayout.setVisibility(View.GONE);
        vOnboardingPageNumber.setImageResource(R.drawable.onboarding_dots_flo);
        // TODO : ACCESSNOW ((ImageView) findViewById(R.id.virtual_toolbar)).setImageResource(mLogoResId);
    }

    public void initListeners() {

        vRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        vLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void initPager() {
        mOnboardingPageAdapter = new TutorialPageAdapter(this);
        vOnboardingViewPager.setAdapter(mOnboardingPageAdapter);
        vOnboardingViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == TutorialPageAdapter.Onboarding_1 ) {
                    vOnboardingPageNumber.setVisibility(View.GONE);
                    // vOnboardingPageNumber.setImageResource(R.drawable.onboarding_dots_flo);
                    vButtonsLayout.setVisibility(View.GONE);
                } else if (position == TutorialPageAdapter.Onboarding_2) {
                    vOnboardingPageNumber.setVisibility(View.GONE);
                    //vOnboardingPageNumber.setImageResource(R.drawable.onboarding_dots_usage);

                } else if (position == TutorialPageAdapter.Onboarding_3) {
                    vOnboardingPageNumber.setVisibility(View.GONE);
                    vButtonsLayout.setVisibility(View.GONE);
                } else if (position == TutorialPageAdapter.Onboarding_4) {
                    vOnboardingPageNumber.setVisibility(View.GONE);
                    vButtonsLayout.setVisibility(View.GONE);
                } else if (position == TutorialPageAdapter.Onboarding_5) {
                    vOnboardingPageNumber.setVisibility(View.GONE);
                    vButtonsLayout.setVisibility(View.VISIBLE);
                }
                mOnboardingPageAdapter.setCurrentFragmentIndex(position);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        final int getPreviousIndex  = mOnboardingPageAdapter.getPreviousItemIndex();
        final int getCurrentIndex = mOnboardingPageAdapter.getCurrentFragmentIndex();
        if(getCurrentIndex == 0) {
            //if on first page then exit the app
            super.onBackPressed();
            finish();
        } else {
            //otherwise go to the previous stack
            vOnboardingViewPager.setCurrentItem(getPreviousIndex);
        }
    }

    public void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // TODO Show an explanation to the user

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
        }
    }
}
