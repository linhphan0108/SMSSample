package com.linhphan.androidboilerplate.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.linhphan.androidboilerplate.callback.ConfirmDialogCallback;

/**
 * Created by linhphan on 11/11/15.
 */
public class ConfirmDialog extends DialogFragment {
    private int mRequestCode;
    private String mNegativeButton;
    private String mPositiveButton;
    private String mTitle;
    private String mMessage;

    private ConfirmDialogCallback mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(mPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mCallback != null) {
                            mCallback.onPositiveButtonClicked();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(mNegativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onNegativeButtonClicked();
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

    public void setNegativeButtonName(String title) {
        this.mNegativeButton = title;
    }

    public void setPositiveButtonName(String title) {
        this.mPositiveButton = title;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * set up callback listeners to the dialog fragment
     * @param callback the callback handler
     */
    public void setCallback(ConfirmDialogCallback callback){
        this.mCallback = callback;
    }
}
