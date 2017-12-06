package br.com.gunna.basemvvmrxproject.androidapp.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gunna.basemvvmrxproject.androidapp.service.BaseService;
import br.com.gunna.basemvvmrxproject.androidapp.ui.activity.BaseActivity;
import br.com.gunna.basemvvmrxproject.androidapp.viewmodel.BaseViewModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 06/12/17.
 */

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseViewModel<BaseService>> extends Fragment {

    protected B mViewBinding;
    protected V mViewModel;
    private CompositeSubscription mParentSubscriptions = new CompositeSubscription();
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateViewModel();
    }
    public void setTilte(String tilte){
        ((BaseActivity)getActivity()).setTitle(tilte);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mViewModel != null) {
            mViewBinding = inflateView(inflater,container,savedInstanceState);
            if (mViewBinding == null)
                throw new RuntimeException("Must  call DataBindingUtils.setContentView(int layout)!!");
        } else {
            throw new RuntimeException("Must  instantiate mViewModel !!");
        }
        return mViewBinding.getRoot();
    }

    protected abstract B inflateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);



    protected void setUpToolbar(Toolbar toolbar) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mParentSubscriptions.hasSubscriptions()) {
            mParentSubscriptions.add(mViewModel
                    .getOnErrorObservable()
                    .subscribe(o -> {
                        handleErrors((Throwable) o);
                    })
            );
        }
    }


    protected void handleErrors(Throwable err) {
        err.printStackTrace();
    }

    protected abstract void onCreateViewModel();


    protected void showSnackBar(int message) {
        Snackbar.make
                (mViewBinding.getRoot(), message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
