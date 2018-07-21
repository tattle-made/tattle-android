package studio.laboroflove.naarad.utils;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SimpleLocationUtil {
    private final String TAG = SimpleLocationUtil.class.getSimpleName();

    private static SimpleLocationUtil _instance;
    private FusedLocationProviderClient locationProviderClient;
    private Location lastKnownLocation;

    public static SimpleLocationUtil getInstance(Context context){
        if(_instance==null){
            _instance = new SimpleLocationUtil();
            _instance.init(context);
        }
        return _instance;
    }


    private void init(Context context){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressWarnings({"ResourceType"})
    public Location getLastKnownLocation(){
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        lastKnownLocation = lastKnownLocation;
                    }
                });
        return lastKnownLocation;
    }
}
