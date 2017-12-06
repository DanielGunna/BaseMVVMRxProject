package br.com.gunna.basemvvmrxproject.androidapp.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.gunna.basemvvmrxproject.androidapp.R;

/**
 * Created by Daniel on 06/12/17.
 */

public class UIUtils {

    public static MaterialDialog getErrorDialog(Context context, String message) {

        return new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(R.string.msg_ok)
                .build();
    }

    public static MaterialDialog getErrorDialog(Context context, @StringRes int message) {

        return new MaterialDialog.Builder(context)
                .content(message)
                .cancelable(false)
                .positiveText(R.string.msg_ok)
                .build();
    }

    public static void showShortToast(Context context, @StringRes int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleChoice(Context context, @StringRes int title, CharSequence[] items,
                                        int selected, final MaterialDialog.ListCallbackSingleChoice listener) {
        new MaterialDialog.Builder(context)
                .items(items)
                .title(title)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .itemsCallbackSingleChoice(selected, listener)
                .positiveText(R.string.msg_ok)
                .show();
    }

    public static void showSingleChoice(Context context, CharSequence[] items, int selected,
                                        final MaterialDialog.ListCallbackSingleChoice listener) {

        new MaterialDialog.Builder(context)
                .items(items)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .itemsCallbackSingleChoice(selected, listener)
                .positiveText(R.string.msg_ok)
                .show();

    }

    public static void showInfoDialog(Context context,int content,
                                      final MaterialDialog.SingleButtonCallback listener){

        new MaterialDialog.Builder(context)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .content(content)
                .positiveText(R.string.msg_ok)
                .onPositive(listener)
                .show();
    }


    public static void showOptionDialog(Context context,int content,
                                        final MaterialDialog.SingleButtonCallback listener){

        new MaterialDialog.Builder(context)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .content(content)
                .positiveText(R.string.msg_ok)
                .negativeText(R.string.msg_no)
                .onPositive(listener)
                .show();
    }

    public static void showInfoDialog(Context context,String content,
                                      final MaterialDialog.SingleButtonCallback listener){

        new MaterialDialog.Builder(context)
                .widgetColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .content(content)
                .positiveText(R.string.msg_ok)
                .onPositive(listener)
                .show();
    }


}
