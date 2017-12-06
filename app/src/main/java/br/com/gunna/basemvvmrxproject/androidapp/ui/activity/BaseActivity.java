package br.com.gunna.basemvvmrxproject.androidapp.ui.activity;



import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import br.com.gunna.basemvvmrxproject.androidapp.service.BaseService;
import br.com.gunna.basemvvmrxproject.androidapp.ui.fragment.BaseFragment;
import br.com.gunna.basemvvmrxproject.androidapp.utils.KeyboardUtils;
import br.com.gunna.basemvvmrxproject.androidapp.utils.UIUtils;
import br.com.gunna.basemvvmrxproject.androidapp.viewmodel.BaseViewModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 06/12/17.
 */

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel<BaseService>> extends AppCompatActivity {

    protected B mViewBinding;
    protected V mViewModel;
    private CompositeSubscription mParentSubscriptions = new CompositeSubscription();
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateViewModel();
        if (mViewModel != null) {
            mViewModel.setResourceProvider(this);
            onCreateBinding(savedInstanceState);
            if (mViewBinding == null)
                throw new RuntimeException("Must  call DataBindingUtils.setContentView(int layout)!!");
        } else {
            throw new RuntimeException("Must  instantiate mViewModel !!");
        }
    }
    protected abstract void onCreateBinding(Bundle instance);

    protected abstract void onCreateViewModel();

    protected abstract String getTag();

    protected void setUpToolbar(Toolbar toolbar, int navIcon) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(navIcon);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
    }


    protected void replaceFragment(BaseFragment fragment, int containerView) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerView, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mParentSubscriptions.hasSubscriptions()) {
            mParentSubscriptions.add(mViewModel
                    .getOnErrorObservable()
                    .subscribe(o -> handleErrors((Throwable) o))
            );
            mParentSubscriptions.add(mViewModel
                    .getmOnDestroySubject()
                    .subscribe(o -> finish())
            );
        }
    }

    @Override
    protected void onPause() {
        KeyboardUtils.hideSoftKeyboard(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.hideSoftKeyboard(this);
        mViewModel.destroy();
        super.onDestroy();
    }

    protected void handleErrors(Throwable err) {
        err.printStackTrace();
        UIUtils.showInfoDialog(this, err.getMessage(), (dialog, which) -> {
            dialog.dismiss();
        });
    }


    protected void showSnackBar(int message) {
        Snackbar.make
                (mViewBinding.getRoot(), message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}