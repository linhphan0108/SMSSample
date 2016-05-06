package com.linhphan.smssample.data.model;

import android.os.Parcel;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 30/03/2016.
 */
public class CatAndLangModel extends BaseModel<CatAndLangModel> {

    private String mName;

    public CatAndLangModel(Parcel in) {

    }

    //=========== setters and getters ==============================================================
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    //========= inherited methods ==================================================================
    //=== Parcelable callback
    public static final Creator<CatAndLangModel> CREATOR = new Creator<CatAndLangModel>() {
        @Override
        public CatAndLangModel createFromParcel(Parcel in) {
            return new CatAndLangModel(in);
        }

        @Override
        public CatAndLangModel[] newArray(int size) {
            return new CatAndLangModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    //== end Parcelable callback

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int compare(CatAndLangModel lhs, CatAndLangModel rhs) {
        return 0;
    }

    @Override
    public CatAndLangModel fromJsonString(String jsonString) {
        return null;
    }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
