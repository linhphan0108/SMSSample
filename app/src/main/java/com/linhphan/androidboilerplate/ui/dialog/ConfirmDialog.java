package com.linhphan.androidboilerplate.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.linhphan.androidboilerplate.callback.ConfirmDialogCallback;
import com.linhphan.androidboilerplate.util.Constant;

/**
 * Created by linhphan on 11/11/15.
 */
public class ConfirmDialog extends DialogFragment {
    private int mRequestCode;
    private String mLeftButton;
    private String mRightBUtton;

    private ConfirmDialogCallback mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String title = null;
        String message = null;
        if (bundle != null) {
            title = bundle.getString(Constant.DIALOG_TITLE, "");
            message = bundle.getString(Constant.DIALOG_MESSAGE, "");
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mCallback != null) {
                            mCallback.onLeftButtonClicked();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onRightButtonClicked();
                        dialog.dismiss();
                    }
                })
                .create();
    }

    //============= setters and getters ============================================================
    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
    }

    public void setLeftButtonTitle(String title) {
        this.mLeftButton = title;
    }

    public void setRightButtonTitle(String title) {
        this.mRightBUtton = title;
    }

    /**
     * set up callback listeners to the dialog fragment
     * @param callback the callback handler
     */
    public void setCallback(ConfirmDialogCallback callback){
        this.mCallback = callback;
    }
}
