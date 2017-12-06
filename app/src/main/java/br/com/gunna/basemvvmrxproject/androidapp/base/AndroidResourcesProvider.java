package br.com.gunna.basemvvmrxproject.androidapp.base;

/**
 * Created by lipec on 9/3/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.subjects.ReplaySubject;

public class AndroidResourcesProvider implements ResourcesProvider {

    private final WeakReference<Context> mContext;



    public AndroidResourcesProvider(Context context) {
        mContext = new WeakReference<>(context);
    }

    @Override
    public String getString(@StringRes int res) {
        return mContext.get().getString(res);
    }

    @Override
    public String getPlural(@PluralsRes int res, int quantity) {
        return mContext.get().getResources().getQuantityString(res, quantity);
    }

    @Override
    public String getPlural(@PluralsRes int res, int quantity, Object... arguments) {
        return mContext.get().getResources().getQuantityString(res, quantity, arguments);
    }

    @Override
    public String[] getStringArray(@ArrayRes int res) {
        return mContext.get().getResources().getStringArray(res);
    }

    @Override
    public int getColorInt(@ColorRes int colorRes) {
        return ContextCompat.getColor(mContext.get(), colorRes);
    }

    @SuppressWarnings("MissingPermission")
    @SuppressLint("MissingPermission")
    public Observable<Location> getCurrentLocation( ) {
        final ReplaySubject<Location> mLocationSubject = ReplaySubject.create();
        LocationManager manager = (LocationManager)
                mContext.get().getSystemService(Context.LOCATION_SERVICE);
        Location location  = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            mLocationSubject.onNext(location);
        }else{
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    manager.removeUpdates(this);
                    mLocationSubject.onNext(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }


        return   mLocationSubject.asObservable();
    }


}
