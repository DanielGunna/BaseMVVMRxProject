package br.com.gunna.basemvvmrxproject.androidapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.com.gunna.basemvvmrxproject.androidapp.application.BaseApplication;

/**
 * Created by Daniel on 06/12/17.
 */

public class AndroidUtils {
    public static Activity getActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return tryGetActivityFromContext(context);
    }

    private static Activity tryGetActivityFromContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) BaseApplication
                        .getInstance()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isGpsOnline(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
