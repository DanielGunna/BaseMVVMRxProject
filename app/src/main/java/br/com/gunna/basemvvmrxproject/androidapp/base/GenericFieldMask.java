package br.com.gunna.basemvvmrxproject.androidapp.base;

/**
 * Created by Daniel on 9/3/2017.
 */


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public class GenericFieldMask implements TextWatcher {

    private final String mMask;
    private final WeakReference<EditText> mEditText;

    private boolean mIsUpdating;
    private String mOldText = "";

    public GenericFieldMask(String mask, EditText editText) {
        mMask = mask;
        mEditText = new WeakReference<>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int arg2, int arg3, int arg4) {
        String str = unmask(charSequence.toString());
        String maskChars = "";

        if (mIsUpdating) {
            mOldText = str;
            mIsUpdating = false;
            return;
        }
        int i = 0;
        for (char m : mMask.toCharArray()) {
            if (m != '#' && str.length() > mOldText.length()) {
                maskChars += m;
                continue;
            }
            try {
                maskChars += str.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        mIsUpdating = true;
        mEditText.get().setText(maskChars);
        mEditText.get().setSelection(maskChars.length());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private String unmask(String s) {
        return s.replaceAll("[.]", "")
                .replaceAll("[-]", "")
                .replaceAll("[/]", "")
                .replaceAll("[(]", "")
                .replaceAll("[)]", "")
                .replaceAll("[:]", "");
    }
}
