package com.mg.shopping.locationutil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.mg.shopping.interfaceutil.CustomLocationCallback;
import com.mg.shopping.utility.Utility;

public class CustomLocationEngine implements LocationListener {
    String tag = getClass().getSimpleName();
    Context context;
    String provider;
    Location location;
    int requestLocationTime = 400;
    int leastDistanceInMeter = 1;
    LocationManager locationManager;
    CustomLocationCallback customLocationCallback;


    public CustomLocationEngine(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    public CustomLocationEngine(Context context, CustomLocationCallback customLocationCallback) {
        this.context = context;
        this.customLocationCallback = customLocationCallback;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public CustomLocationEngine initCriteria(Criteria criteria) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                return this;
        }

        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);

        if (location==null)
            return this;

        onLocationChanged(location);

        return this;

    }

    @SuppressLint("MissingPermission")
    public CustomLocationEngine startLocationEngine() {

        if (Utility.isEmptyString(provider)) {
            return null;
        }

        if (locationManager==null)
            return null;

        locationManager.requestLocationUpdates(provider, requestLocationTime, leastDistanceInMeter, this);

        return this;
    }

    public CustomLocationEngine stopLocationEngine(){

        if (locationManager==null)
            return null;

        locationManager.removeUpdates(this);
        return this;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (customLocationCallback != null) {
            customLocationCallback.onLocationUpdates(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Utility.Logger(tag,"Provider Status = "+provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Utility.Logger(tag,"Provider = "+provider+" Enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Utility.Logger(tag,"Provider = "+provider+" Disable");
    }
}
