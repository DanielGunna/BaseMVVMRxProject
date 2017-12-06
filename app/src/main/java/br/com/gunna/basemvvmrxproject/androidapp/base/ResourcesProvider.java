package br.com.gunna.basemvvmrxproject.androidapp.base;

/**
 * Created by Daniel on 9/3/2017.
 */


import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

public interface ResourcesProvider {

    String getString(@StringRes int res);

    String getPlural(@PluralsRes int res, int quantity);

    String getPlural(@PluralsRes int res, int quantity, Object... arguments);

    String[] getStringArray(@ArrayRes int res);

    @ColorInt
    int getColorInt(@ColorRes int colorRes);
}
