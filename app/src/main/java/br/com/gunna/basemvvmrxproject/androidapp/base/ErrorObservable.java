package br.com.gunna.basemvvmrxproject.androidapp.base;

import android.databinding.BaseObservable;

/**
 * Created by Daniel on 9/3/2017.
 */
public class ErrorObservable extends BaseObservable {

    private String error;

    public ErrorObservable() {
    }

    public ErrorObservable(String errorMessage) {
        this.error = errorMessage;
    }

    public void set(String error) {
        this.error = error;
        notifyChange();
    }

    public String get() {
        return error;
    }

    public void clear() {
        this.error = null;
        notifyChange();
    }

    public boolean hasError() {
        return error != null;
    }
}