package br.com.gunna.basemvvmrxproject.androidapp.service;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Daniel on 06/12/17.
 */

public abstract class BaseService {
    private final PublishSubject<Throwable> mErrorSubjet = PublishSubject.create();
    private String tag;
    private static  AppService sServiceInstance;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Observable<Throwable> getmErrorSubjet() {
        return mErrorSubjet.asObservable();
    }
}
