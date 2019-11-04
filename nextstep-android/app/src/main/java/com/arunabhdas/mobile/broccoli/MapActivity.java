package com.arunabhdas.mobile.broccoli;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    protected LocationManager mLocationManager;
    // flag for GPS status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    private Context mContext;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    // The minimum time between updates in milliseconds
    private static final long LOCATION_REFRESH_TIME = 1; // 1 minute
    // The minimum distance to change Updates in meters
    private static final float LOCATION_REFRESH_DISTANCE = 1.0f; // 10 meters
    private static final int LOCATION_PERMISSION_REQUEST_ID = 1;
    private static final int LOCATION_SERVICES_DIALOG_DELAY = 5;
    private Intent mLocationTrackerServiceIntent;
    private BroadcastReceiver mLocationReceiver = null;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mContext = this;
        initializeLocationServices();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void displayLocationServicesEnableDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext)
                .setMessage(getString(R.string.enable_location_services_prompt))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // TODO Handle the negative case
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
        });
        alertDialog.show();
    }

    private void initializeLocationServices() {

        /*
        if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_ID);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                    displayLocationServicesEnableDialog();
                }
        }, LOCATION_SERVICES_DIALOG_DELAY);
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_ID);
        } else {
            mLocationTrackerServiceIntent = new Intent(this, LocationTrackerService.class);
        }

        */


        mLocationTrackerServiceIntent = new Intent(this, LocationTrackerService.class);
        startService(mLocationTrackerServiceIntent);
        mLocationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(LocationTrackerService.LOCATION_INTENT)) {
                    uninitializeLocationServices();
                }
            }
        };
        this.registerReceiver(mLocationReceiver, new IntentFilter(LocationTrackerService.LOCATION_INTENT));
    }
    @Override
    public void onPause() {
        super.onPause();
        uninitializeLocationServices();
    }

    public void uninitializeLocationServices() {
        if (mLocationTrackerServiceIntent != null) {
            stopService(mLocationTrackerServiceIntent);
            mLocationTrackerServiceIntent = null;
        }
        if (mLocationReceiver != null) {
            this.unregisterReceiver(mLocationReceiver);
            mLocationReceiver = null;
        }
    }

}
