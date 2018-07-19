package studio.laboroflove;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationUtil implements LocationListener{
    private final String TAG = LocationUtil.class.getSimpleName();
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static LocationUtil mLocationUtil;
    private LocationRequest locationRequest;
    private LocationAvailabilityListener mListener;

    public interface LocationAvailabilityListener{
        void onLocationAvailable(boolean isAvailable);
        void lastKnowLocation(Location location);
    }

    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.d(TAG, "location result" + locationResult.getLastLocation().getLatitude()+", "+locationResult.getLastLocation().getLongitude());
            mListener.lastKnowLocation(locationResult.getLastLocation());
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            Log.d(TAG, "location avlb : "+locationAvailability.isLocationAvailable());
            mListener.onLocationAvailable(locationAvailability.isLocationAvailable());
        }
    };

    public static LocationUtil getInstance(Context context, LocationAvailabilityListener listener){
        if(mLocationUtil==null){
            mLocationUtil = new LocationUtil();
            mLocationUtil.mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            mLocationUtil.locationRequest = new LocationRequest();
            mLocationUtil.locationRequest.setInterval(10000);
            mLocationUtil.locationRequest.setFastestInterval(5000);
            mLocationUtil.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            mLocationUtil.mListener = listener;
        }
        return mLocationUtil;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "location changed");
    }

    public void startLocationUpdates(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mFusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
            );
        }
    }

    public void stopLocationUpdates(){
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}