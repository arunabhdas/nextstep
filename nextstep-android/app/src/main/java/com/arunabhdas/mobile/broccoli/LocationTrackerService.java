package com.arunabhdas.mobile.broccoli;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by das on 13/05/16.
 */
public class LocationTrackerService extends Service implements LocationListener {
    private Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // TODO May need to optimize this number
    private static final long DEFAULT_MIN_TIME_BW_UPDATES = 1000; // 1 second

    public static final String LOCATION_INTENT = "com.arunabhdas.flo.intent.action.LOCATION_RECEIVED";

    protected LocationManager locationManager;

    private Timer mTimer;

    private int mTimerCounter;

    private static final int MAX_ATTEMPTS = 20;

    private static final String TAG = LocationTrackerService.class.getSimpleName();

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public LocationTrackerService getService() {
            return LocationTrackerService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        this.mContext = this;
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            getLocationChangedLocation();
            mTimer = new Timer();

            mTimer.schedule( new TimerTask() {
                public void run() {
                    if (mTimerCounter < MAX_ATTEMPTS) {
                        getLocation();
                    }
                }
            }, 0, DEFAULT_MIN_TIME_BW_UPDATES);
        }
    }

    public void getLocationChangedLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, DEFAULT_MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this);
        }
    }
    // TODO Below method is no longer used and is marked for deprecation
    public void getLocation() {
        Log.d(TAG, "getLocation");
        mTimerCounter++;
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
            } else {
                this.canGetLocation = true;
                location = retrieveBestLocation(locationManager);
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                String iso3Country = GeocodeListBuilder.getCountryId(mContext, latitude, longitude);
                String countryName = GeocodeListBuilder.getCountryName(mContext, latitude, longitude);
                Intent locationIntent = new Intent();
                locationIntent.setAction(LOCATION_INTENT);
                mContext.sendBroadcast(locationIntent);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        }

    }
    private Location retrieveBestLocation(LocationManager locationManager) {
        List<String> matchingProviders = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : matchingProviders) {
            if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location == null) {
                    continue;
                }
                if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = location;
                }
            }
        }
        return bestLocation;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(LocationTrackerService.this);
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        stopUsingGPS();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        super.onDestroy();
    }
    @Override
    public void onLocationChanged(Location newLocation) {
        Log.d(TAG, "onLocationChanged");
        location = newLocation;
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        String iso3Country = GeocodeListBuilder.getCountryId(mContext, latitude, longitude);
        String countryName = GeocodeListBuilder.getCountryName(mContext, latitude, longitude);
        Intent locationIntent = new Intent();
        locationIntent.setAction(LOCATION_INTENT);
        mContext.sendBroadcast(locationIntent);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onUnbind(Intent arg0) {
        Log.d(TAG, "onUnbind");
        stopUsingGPS();
        return true;
    }
}
