package br.com.gunna.basemvvmrxproject.androidapp.viewmodel;

/**
 * Created by Daniel on 06/12/17.
 */


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.annotation.CallSuper;

import br.com.gunna.basemvvmrxproject.androidapp.base.AndroidResourcesProvider;
import br.com.gunna.basemvvmrxproject.androidapp.base.ResourcesProvider;
import br.com.gunna.basemvvmrxproject.androidapp.base.RetrofitException;
import br.com.gunna.basemvvmrxproject.androidapp.service.BaseService;
import br.com.gunna.basemvvmrxproject.androidapp.utils.RxJavaUtils;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lipec on 9/2/2017.
 */

public  abstract class BaseViewModel<S extends BaseService> extends BaseObservable {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private final CompositeSubscription mParentSubscriptions = new CompositeSubscription();
    protected final CompositeSubscription mSubscriptions = new CompositeSubscription();
    private final PublishSubject<Throwable>  mOnErrorSubject = PublishSubject.create();
    private final PublishSubject<Void>  mOnDestroySubject = PublishSubject.create();
    protected ResourcesProvider mResourcesProvider;
    protected S mService;


    public Observable<Throwable> getOnErrorObservable(){return mOnErrorSubject.asObservable();}
    public ObservableBoolean getIsLoading(){return  mIsLoading;}


    public void setResourceProvider(Context context){
        mResourcesProvider = new AndroidResourcesProvider(context);
    }

    protected BaseViewModel(S service){
        mService =  service;
        if(mService == null)
            throw  new RuntimeException("mService cant be null !!");
        addOnServiceError();
    }

    private void addOnServiceError() {
        mParentSubscriptions.add(mService
                .getmErrorSubjet()
                .subscribe(this::onError)
        );
    }

    public Observable<Void> getmOnDestroySubject() {
        return mOnDestroySubject.asObservable();
    }

    protected  void destroyView(){
        mOnDestroySubject.onNext(null);
    }

    protected  void onError(Throwable error){
        hideLoading();
        error.printStackTrace();
        if(error instanceof RetrofitException){
            RetrofitException t = (RetrofitException) error;
            try {
                String body = t.getResponse().errorBody().string();
                mOnErrorSubject.onNext(new Throwable(body));
            }catch (Exception e) {
                e.printStackTrace();
                mOnErrorSubject.onNext(error);
            }
        }else {
            mOnErrorSubject.onNext(error);
        }
    }

    @CallSuper
    public void hideLoading(){
        mIsLoading.set(false);
    }

    @CallSuper
    public void showLoading(){
        mIsLoading.set(true);
    }
    @CallSuper
    public  void destroy(){
        mParentSubscriptions.unsubscribe();
        mSubscriptions.unsubscribe();
        RxJavaUtils.checkUnsubscribe(mParentSubscriptions);
        RxJavaUtils.checkUnsubscribe(mSubscriptions);
    }
}
