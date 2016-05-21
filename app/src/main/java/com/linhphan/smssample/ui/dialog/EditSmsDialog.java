package com.linhphan.smssample.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.linhphan.smssample.R;

/**
 * Created by linh on 5/19/2016.
 */
public class EditSmsDialog extends DialogFragment {

    private EditText mEdtContent;
    private Button mBtnOk;
    private Button mBtnCancel;

    private String mContent;
    private DialogCallback mCallback;

    public void setContent(String content) {
        this.mContent = content;
    }

    public void setCallback(DialogCallback callback) {
        this.mCallback = callback;
    }

    //============= inherited methods ==============================================================
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_edit_sms, container, false);
        getWidgets(root, savedInstanceState);
        registerEventHandler();

        return root;
    }

    //========== inner methods =====================================================================
    private void getWidgets(View root, Bundle savedInstanceState){
        if (root != null){
            mEdtContent = (EditText) root.findViewById(R.id.editText);
            mBtnOk = (Button) root.findViewById(R.id.btn_ok);
            mBtnCancel = (Button) root.findViewById(R.id.btn_cancel);
        }

        mEdtContent.setText(mContent);
        mEdtContent.setSelection(mEdtContent.getText().length());
    }

    private void registerEventHandler(){
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    String newContent = mEdtContent.getText().toString();
                    if (!TextUtils.isEmpty(newContent) && !newContent.equals(mContent)) {
                        mCallback.onOkClicked(newContent);
                    }
                }
                dismiss();
            }
        });
    }

    //======== inner classes =======================================================================
    public interface DialogCallback{
        /**
         * this method will return a new content
         * if the new content is as identical as the original content then return null
         */
        void onOkClicked(String newContent);
    }
}
