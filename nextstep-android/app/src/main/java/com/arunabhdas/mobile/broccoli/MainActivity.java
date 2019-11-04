package com.arunabhdas.mobile.broccoli;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.arunabhdas.mobile.fragments.FragmentDrawerEight;
import com.arunabhdas.mobile.fragments.FragmentDrawerFive;
import com.arunabhdas.mobile.fragments.FragmentDrawerFour;
import com.arunabhdas.mobile.fragments.FragmentDrawerOne;
import com.arunabhdas.mobile.fragments.FragmentDrawerSeven;
import com.arunabhdas.mobile.fragments.FragmentDrawerSix;
import com.arunabhdas.mobile.fragments.FragmentDrawerThree;
import com.arunabhdas.mobile.fragments.FragmentDrawerTwo;
import com.arunabhdas.mobile.fragments.FragmentDrawerZero;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    private String mImeiOne = null;
    private String mImeiTwo = null;
    private String mImsiOne;
    private String mImsiTwo;
    private String mIccid;
    private TextView mTitleText;
    private TextView vImeiOneText;
    private TextView vImeiTwoText;
    private TextView vImsiOneText;
    private TextView vImsiTwoText;
    private TextView vIccidTextView;

    FloatingActionButton mFabRefresh;
    BottomSheetLayout vBottomSheet;
    private static final int PERMISSION_READ_STATE = 999;
    private String mOperatorISO3;
    TelephonyManager mTel;
    SubscriptionManager mSub= null;
    Fragment vFragment = null;
    Class vFragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        vBottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        vBottomSheet.setPeekOnDismiss(true);


        // mFabRefresh = (FloatingActionButton) findViewById(R.id.fab_refresh);




        setSupportActionBar(toolbar);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }

        mTel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT > 21) {
            mSub = SubscriptionManager.from(mContext);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        vFragmentClass = FragmentDrawerZero.class;
        initFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                // final Intent lpaIntent = new Intent(this, FloProvSettingsActivity.class);
                // this.startActivity(lpaIntent);
                showMenuSheet(MenuSheetView.MenuType.LIST);

                // finish();
                return true;
            case R.id.action_settings:
                final Intent settingsIntent = new Intent(this, com.arunabhdas.mobile.flopro.FloProvActivity.class);
                this.startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMenuSheet(final MenuSheetView.MenuType menuType) {
        MenuSheetView menuSheetView =
                new MenuSheetView(MainActivity.this, menuType, getString(R.string.label_menu_actions), new MenuSheetView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (vBottomSheet.isSheetShowing()) {
                            vBottomSheet.dismissSheet();
                        }
                        if (item.getItemId() == R.id.logout) {
                            // showMenuSheet(menuType == MenuSheetView.MenuType.LIST ? MenuSheetView.MenuType.GRID : MenuSheetView.MenuType.LIST);
                            finish();
                        }
                        return true;
                    }
                });
        menuSheetView.inflateMenu(R.menu.menu_actions);
        menuSheetView.setBackground(getDrawable(R.drawable.gradient_side_nav_bar));
        vBottomSheet.showWithSheetView(menuSheetView);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_1:
                initSectionOne();
                break;
            case R.id.nav_2:
                initSectionTwo();
                break;
            case R.id.nav_3:
                initSectionThree();
                break;
            case R.id.nav_4:
                initSectionFour();
                break;
            case R.id.nav_5:
                initSectionFive();
                break;
            case R.id.nav_6:
                initSectionSix();
                break;
            case R.id.nav_7:
                initSectionSeven();
                break;
            case R.id.nav_8:
                initSectionEight();
                break;
            default:
                initSectionZero();
                break;


        }


        initFragment();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment() {
        try {
            vFragment = (Fragment) vFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content, vFragment).commit();
    }

    private void initSectionZero() {
        getSupportActionBar().setTitle(R.string.app_name);
        vFragmentClass = FragmentDrawerZero.class;

        /*
        mFabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Retrieve", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                vImeiOneText.setText("IMEI 1 : " + mImeiOne);
                vImeiTwoText.setText("IMEI 2 : " + mImeiTwo);
                vImsiOneText.setText("IMSI 1 :" + mImsiOne);
                vImsiTwoText.setText("IMSI 2 : " + mImsiTwo);
                vIccidTextView.setText("ICCID : " + mIccid);
            }
        });
        */

    }

    private void initSectionOne() {
        getSupportActionBar().setTitle(R.string.title_section_1);
        vFragmentClass = FragmentDrawerOne.class;


    }

    private void initSectionTwo() {
        getSupportActionBar().setTitle(R.string.title_section_2);
        vFragmentClass = FragmentDrawerTwo.class;

    }

    private void initSectionThree() {
        getSupportActionBar().setTitle(R.string.title_section_3);
        vFragmentClass = FragmentDrawerThree.class;

    }
    private void initSectionFour() {
        getSupportActionBar().setTitle(R.string.title_section_4);
        vFragmentClass = FragmentDrawerFour.class;

    }
    private void initSectionFive() {
        getSupportActionBar().setTitle(R.string.title_section_5);
        vFragmentClass = FragmentDrawerFive.class;

    }

    private void initSectionSix() {
        getSupportActionBar().setTitle(R.string.title_section_6);
        vFragmentClass = FragmentDrawerSix.class;
    }

    private void initSectionSeven() {
        getSupportActionBar().setTitle(R.string.title_section_7);
        vFragmentClass = FragmentDrawerSeven.class;
    }

    private void initSectionEight() {
        getSupportActionBar().setTitle(R.string.title_section_7);
        vFragmentClass = FragmentDrawerEight.class;
    }



}
