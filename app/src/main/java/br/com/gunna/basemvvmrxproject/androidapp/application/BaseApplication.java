package br.com.gunna.basemvvmrxproject.androidapp.application;

import android.app.Application;

import br.com.gunna.basemvvmrxproject.androidapp.service.AppService;


/**
 * Created by Daniel on 06/12/17.
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private static PreferencesManager mPrefs;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        PreferencesManager.initializeInstance(this,getPrefsFile());
        mPrefs = PreferencesManager.getInstance();
    }

    public static PreferencesManager getPrefs() {
        return mPrefs;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public abstract String getPrefsFile() ;
}
